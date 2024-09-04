package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    ProjectDTO create(ProjectDTO projectDTO);

    ProjectDTO readByProjectCode(String projectCode);
    String readManagerByProjectCode(String projectCode);
    List<ProjectDTO> readAllProjectsWithDetails();
    List<ProjectDTO> adminReadAllProjects();
    List<ProjectDTO> managerReadAllProjects();
    Integer countNonCompletedByAssignedManager(String assignedManager);
    boolean checkByProjectCode(String projectCode);

    ProjectDTO update(String projectCode, ProjectDTO projectDTO);
    ProjectDTO complete(String projectCode);

    void delete(String projectCode);

}
