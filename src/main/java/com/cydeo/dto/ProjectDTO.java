package com.cydeo.dto;

import com.cydeo.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"}, ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectDTO {

    @JsonIgnore
    private Long id;

    @NotBlank(message = "Project name is required.")
    @Size(min = 3, max = 16, message = "Project name length should be min 3, max 16.")
    private String projectName;

    @NotBlank(message = "Project code is required.")
    @Size(min = 5, max = 5, message = "Project code should include 5 characters.")
    private String projectCode;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String assignedManager;

    @NotNull(message = "Start date is required.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date is required.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String projectDetail;
    private Status projectStatus;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer completedTaskCount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer nonCompletedTaskCount;

}
