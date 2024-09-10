package com.cydeo.service.impl;

import com.cydeo.client.TaskClient;
import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.wrapper.TaskResponse;
import com.cydeo.entity.Project;
import com.cydeo.enums.Status;
import com.cydeo.exception.*;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.KeycloakService;
import com.cydeo.service.ProjectService;
import com.cydeo.util.MapperUtil;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final MapperUtil mapperUtil;
    private final KeycloakService keycloakService;
    private final TaskClient taskClient;

    public ProjectServiceImpl(ProjectRepository projectRepository, MapperUtil mapperUtil, KeycloakService keycloakService, TaskClient taskClient) {
        this.projectRepository = projectRepository;
        this.mapperUtil = mapperUtil;
        this.keycloakService = keycloakService;
        this.taskClient = taskClient;
    }

    @Override
    public ProjectDTO create(ProjectDTO projectDTO) {

        Optional<Project> foundProject = projectRepository.findByProjectCode(projectDTO.getProjectCode());

        if (foundProject.isPresent()) {
            throw new ProjectAlreadyExistsException("Project already exists.");
        }

        Project projectToSave = mapperUtil.convert(projectDTO, new Project());

        String loggedInUserUsername = keycloakService.getUsername();

        projectToSave.setAssignedManager(loggedInUserUsername);
        projectToSave.setProjectStatus(Status.OPEN);

        Project savedProject = projectRepository.save(projectToSave);

        return mapperUtil.convert(savedProject, new ProjectDTO());

    }

    @Override
    public ProjectDTO readByProjectCode(String projectCode) {

        Project foundProject = projectRepository.findByProjectCode(projectCode)
                .orElseThrow(() -> new ProjectNotFoundException("Project does not exist."));

        checkAccess(foundProject);

        return mapperUtil.convert(foundProject, new ProjectDTO());

    }

    @Override
    public String readManagerByProjectCode(String projectCode) {

        Project foundProject = projectRepository.findByProjectCode(projectCode)
                .orElseThrow(() -> new ProjectNotFoundException("Project does not exist."));

        checkAccess(foundProject);

        return foundProject.getAssignedManager();

    }

    @Override
    @CircuitBreaker(name = "task-service",fallbackMethod = "readAllProjectsWithDetails")
    public List<ProjectDTO> readAllProjectsWithDetails() {

        String loggedInUserUsername = keycloakService.getUsername();

        List<Project> foundProjects = projectRepository.findAllByAssignedManager(loggedInUserUsername);
        return foundProjects.stream()
                .map(this::retrieveProjectDetails).collect(Collectors.toList());

    }


    List<ProjectDTO> readAllProjectsWithDetails (Throwable throwable){
        return Arrays.asList(
                ProjectDTO.builder()
                        .nonCompletedTaskCount(0)
                        .completedTaskCount(0)
                        .projectDetail("")
                        .projectName("")
                        .assignedManager("")
                        .endDate(null)
                        .startDate(null)
                        .projectStatus(null)
                        .id(null)
                        .build()
        );
    }



    @Override
    public List<ProjectDTO> adminReadAllProjects() {
        List<Project> foundProjects = projectRepository.findAll();
        return foundProjects.stream()
                .map(project -> mapperUtil.convert(project, new ProjectDTO())).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> managerReadAllProjects() {

        String loggedInUserUsername = keycloakService.getUsername();

        List<Project> foundProjects = projectRepository.findAllByAssignedManager(loggedInUserUsername);

        return foundProjects.stream()
                .map(project -> mapperUtil.convert(project, new ProjectDTO())).collect(Collectors.toList());

    }

    @Override
    public Integer countNonCompletedByAssignedManager(String assignedManager) {
        return projectRepository.countNonCompletedByAssignedManager(assignedManager);
    }

    @Override
    public boolean checkByProjectCode(String projectCode) {

        Optional<Project> foundProject = projectRepository.findByProjectCode(projectCode);

        if (foundProject.isEmpty()) {
            return false;
        }

        if (foundProject.get().getProjectStatus().getValue().equals("Completed")) {
            throw new ProjectIsCompletedException("Project is already completed.");
        }

        checkAccess(foundProject.get());

        return true;

    }

    @Override
    public ProjectDTO update(String projectCode, ProjectDTO projectDTO) {

        Project foundProject = projectRepository.findByProjectCode(projectCode)
                .orElseThrow(() -> new ProjectNotFoundException("Project does not exist."));

        checkAccess(foundProject);

        Project projectToUpdate = mapperUtil.convert(projectDTO, new Project());
        projectToUpdate.setId(foundProject.getId());
        projectToUpdate.setProjectCode(projectCode);
        projectToUpdate.setProjectStatus(foundProject.getProjectStatus());
        projectToUpdate.setAssignedManager(foundProject.getAssignedManager());

        Project updatedProject = projectRepository.save(projectToUpdate);

        return mapperUtil.convert(updatedProject, new ProjectDTO());

    }

    @Override
    public ProjectDTO complete(String projectCode) {

        Project projectToComplete = projectRepository.findByProjectCode(projectCode)
                .orElseThrow(() -> new ProjectNotFoundException("Project does not exist."));

        checkAccess(projectToComplete);

        projectToComplete.setProjectStatus(Status.COMPLETED);

        Project completedProject = projectRepository.save(projectToComplete);

        completeRelatedTasks(projectCode);

        return mapperUtil.convert(completedProject, new ProjectDTO());

    }

    @Override
    public void delete(String projectCode) {

        Project projectToDelete = projectRepository.findByProjectCode(projectCode)
                .orElseThrow(() -> new ProjectNotFoundException("Project does not exist."));

        checkAccess(projectToDelete);

        projectToDelete.setIsDeleted(true);
        projectToDelete.setProjectCode(projectCode + "-" + projectToDelete.getId());

        deleteRelatedTasks(projectCode);

        projectRepository.save(projectToDelete);

    }

    private void checkAccess(Project project) {

        String loggedInUserUsername = keycloakService.getUsername();

        if ((keycloakService.hasClientRole(loggedInUserUsername, "Manager") && !loggedInUserUsername.equals(project.getAssignedManager()))
                || keycloakService.hasClientRole(loggedInUserUsername, "Employee")) {
            throw new ProjectAccessDeniedException("Access denied, make sure that you are working on your own project.");
        }

    }

    private ProjectDTO retrieveProjectDetails(Project project) {

        ProjectDTO projectDTO=mapperUtil.convert(project,new ProjectDTO());

        ResponseEntity<TaskResponse> taskResponse= taskClient.getCountByProject(project.getProjectCode());

        if (Objects.requireNonNull(taskResponse.getBody()).isSuccess()){

            Map<String,Integer>taskCount= (HashMap<String, Integer>) taskResponse.getBody().getData();

            Integer completedTaskCount= taskCount.get("completedTaskCount");
            Integer nonCompletedTaskCount= taskCount.get("nonCompletedTaskCount");


            projectDTO.setCompletedTaskCount(completedTaskCount);
            projectDTO.setNonCompletedTaskCount(nonCompletedTaskCount);
        }else {
            throw new ProjectDetailsNotRetrievedException("Project details can not be retrieved");
        }


        return projectDTO;

    }

    private void completeRelatedTasks(String projectCode) {

       ResponseEntity<TaskResponse> response=taskClient.completeByProject(projectCode);

       if(!Objects.requireNonNull(response.getBody()).isSuccess()){
             throw new TasksCanNotBeCompletedException("Tasks of a project"+ projectCode +" can not be completed");
       }

    }

    private void deleteRelatedTasks(String projectCode) {

        ResponseEntity<TaskResponse> response=taskClient.deleteByProject(projectCode);

        if(!Objects.requireNonNull(response.getBody()).isSuccess()){
            throw new TasksCanNotBeDeletedException("Tasks of a project"+ projectCode +" can not be deleted");
        }

    }

}
