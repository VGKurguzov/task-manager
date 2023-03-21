package com.example.taskmanager.repository;

import com.example.taskmanager.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(value = "SELECT * FROM Project WHERE PARENT_PROJECT_ID IS NULL", nativeQuery = true)
    List<Project> findAllRootProjects();

    @Query(value = "SELECT * FROM Project WHERE PARENT_PROJECT_ID = ?1", nativeQuery = true)
    List<Project> findAllProjectsByParentProjectId(Long id);

    Optional<Project> findByTitle(String title);
}
