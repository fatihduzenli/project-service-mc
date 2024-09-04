package com.cydeo.repository;

import com.cydeo.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByProjectCode(String code);

    List<Project> findAllByAssignedManager(String assignedManager);

    @Query(value = "SELECT COUNT(*) FROM projects WHERE assigned_manager = :assignedManager " +
            "AND project_status <> 'COMPLETED' AND is_deleted = false", nativeQuery = true)
    int countNonCompletedByAssignedManager(@Param("assignedManager") String assignedManager);

}
