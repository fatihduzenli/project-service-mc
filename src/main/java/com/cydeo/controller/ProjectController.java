package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.wrapper.ExceptionWrapper;
import com.cydeo.dto.wrapper.ResponseWrapper;
import com.cydeo.service.ProjectService;
import com.cydeo.util.SwaggerExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@Tag(name = "ProjectController", description = "Project controller endpoints")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RolesAllowed("Manager")
    @PostMapping("/create")
    @Operation(summary = "Create a project.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectDTO.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_CREATE_REQUEST_EXAMPLE))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project is successfully created.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_CREATE_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "409", description = "Project already exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_ALREADY_EXISTS_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid Input(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.VALIDATION_EXCEPTION_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> createProject(@Valid @RequestBody ProjectDTO projectDTO) {

        ProjectDTO createdProject = projectService.create(projectDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseWrapper.builder()
                        .success(true)
                        .statusCode(HttpStatus.CREATED)
                        .message("Project is successfully created.")
                        .data(createdProject)
                        .build());

    }

    @RolesAllowed({"Admin", "Manager"})
    @GetMapping("/read/{projectCode}")
    @Operation(summary = "Read a project by project code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project is successfully retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_GET_RESPONSE_SINGLE_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "Project does not exist.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "User does not exist.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access denied, make sure that you are working on your own project.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> getByProjectCode(@PathVariable("projectCode") String projectCode) {

        ProjectDTO foundProject = projectService.readByProjectCode(projectCode);

        return ResponseEntity
                .ok(ResponseWrapper.builder()
                        .success(true)
                        .statusCode(HttpStatus.OK)
                        .message("Project is successfully retrieved.")
                        .data(foundProject)
                        .build());

    }

    @RolesAllowed("Manager")
    @GetMapping("/read/manager/{projectCode}")
    @Operation(summary = "Read a manager by project code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Manager information is successfully retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.MANAGER_GET_BY_PROJECT_CODE_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "Project does not exist.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "User does not exist.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access denied, make sure that you are working on your own project.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> getManagerByProjectCode(@PathVariable("projectCode") String projectCode) {

        String foundManager = projectService.readManagerByProjectCode(projectCode);

        return ResponseEntity
                .ok(ResponseWrapper.builder()
                        .success(true)
                        .statusCode(HttpStatus.OK)
                        .message("Manager information is successfully retrieved.")
                        .data(foundManager)
                        .build());

    }

    @RolesAllowed("Manager")
    @GetMapping("/read/all/details")
    @Operation(summary = "Read all projects with details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects are successfully retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_DETAIL_GET_RESPONSE_LIST_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "500", description = "Project details cannot be retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_DETAILS_NOT_RETRIEVED_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> getProjectsWithDetails() {

        List<ProjectDTO> foundProjects = projectService.readAllProjectsWithDetails();

        return ResponseEntity
                .ok(ResponseWrapper.builder()
                        .success(true)
                        .statusCode(HttpStatus.OK)
                        .message("Projects are successfully retrieved.")
                        .data(foundProjects)
                        .build());

    }

    @RolesAllowed("Admin")
    @GetMapping("/read/all/admin")
    @Operation(summary = "Admin read all projects.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects are successfully retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_GET_RESPONSE_LIST_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> adminGetProjects() {

        List<ProjectDTO> foundProjects = projectService.adminReadAllProjects();

        return ResponseEntity
                .ok(ResponseWrapper.builder()
                        .success(true)
                        .statusCode(HttpStatus.OK)
                        .message("Projects are successfully retrieved.")
                        .data(foundProjects)
                        .build());

    }

    @RolesAllowed("Manager")
    @GetMapping("/read/all/manager")
    @Operation(summary = "Manager read all projects.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects are successfully retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_GET_RESPONSE_LIST_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> managerGetProjects() {

        List<ProjectDTO> foundProjects = projectService.managerReadAllProjects();

        return ResponseEntity
                .ok(ResponseWrapper.builder()
                        .success(true)
                        .statusCode(HttpStatus.OK)
                        .message("Projects are successfully retrieved.")
                        .data(foundProjects)
                        .build());

    }

    @RolesAllowed("Admin")
    @GetMapping("/count/manager/{assignedManager}")
    @Operation(summary = "Read non completed project count of an assigned manager by username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project count is retrieved.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_COUNT_BY_MANAGER_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> getNonCompletedCountByAssignedManager(@PathVariable("assignedManager") String assignedManager) {

        Integer projectCount = projectService.countNonCompletedByAssignedManager(assignedManager);

        return ResponseEntity
                .ok(ResponseWrapper.builder()
                        .success(true)
                        .statusCode(HttpStatus.OK)
                        .message("Project count is retrieved.")
                        .data(projectCount)
                        .build());

    }

    @RolesAllowed({"Admin", "Manager"})
    @GetMapping("/check/{projectCode}")
    @Operation(summary = "Check if project exists or not by project code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_CHECK_BY_PROJECT_CODE_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "409", description = "Project is already completed.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_ALREADY_COMPLETED_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "User does not exist.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access denied, make sure that you are working on your own project.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> checkByProjectCode(@PathVariable("projectCode") String projectCode) {

        boolean result = projectService.checkByProjectCode(projectCode);

        return ResponseEntity
                .ok(ResponseWrapper.builder()
                        .success(true)
                        .statusCode(HttpStatus.OK)
                        .message("Project exists.")
                        .data(result)
                        .build());

    }

    @RolesAllowed("Manager")
    @PutMapping("/update/{projectCode}")
    @Operation(summary = "Update a project.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectDTO.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_UPDATE_REQUEST_EXAMPLE))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project is successfully updated.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_UPDATE_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "Project does not exist.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "User does not exist.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "400", description = "Invalid Input(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.VALIDATION_EXCEPTION_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access denied, make sure that you are working on your own project.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> updateProject(@PathVariable("projectCode") String projectCode,
                                                     @Valid @RequestBody ProjectDTO projectDTO) {

        ProjectDTO updatedProject = projectService.update(projectCode, projectDTO);

        return ResponseEntity
                .ok(ResponseWrapper.builder()
                        .success(true)
                        .statusCode(HttpStatus.OK)
                        .message("Project is successfully updated.")
                        .data(updatedProject)
                        .build());

    }

    @RolesAllowed("Manager")
    @PutMapping("/complete/{projectCode}")
    @Operation(summary = "Complete a project.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project is successfully completed.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_COMPLETE_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "Project does not exist.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "User does not exist.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access denied, make sure that you are working on your own project.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "500", description = "Related tasks cannot be completed.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.TASKS_NOT_COMPLETED_RESPONSE_EXAMPLE)))})
    public ResponseEntity<ResponseWrapper> completeProject(@PathVariable("projectCode") String projectCode) {

        ProjectDTO completedProject = projectService.complete(projectCode);

        return ResponseEntity
                .ok(ResponseWrapper.builder()
                        .success(true)
                        .statusCode(HttpStatus.OK)
                        .message("Project is successfully completed.")
                        .data(completedProject)
                        .build());

    }

    @RolesAllowed("Manager")
    @DeleteMapping("/delete/{projectCode}")
    @Operation(summary = "Delete a project.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Project is successfully completed.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Project does not exist.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "User does not exist.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_NOT_FOUND_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access denied, make sure that you are working on your own project.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.PROJECT_ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "403", description = "Access is denied",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.ACCESS_DENIED_FORBIDDEN_RESPONSE_EXAMPLE))),
            @ApiResponse(responseCode = "500", description = "Related tasks cannot be deleted.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionWrapper.class),
                            examples = @ExampleObject(value = SwaggerExamples.TASKS_NOT_DELETED_RESPONSE_EXAMPLE)))})
    public ResponseEntity<Void> deleteProject(@PathVariable("projectCode") String projectCode) {
        projectService.delete(projectCode);
        return ResponseEntity.noContent().build();
    }

}
