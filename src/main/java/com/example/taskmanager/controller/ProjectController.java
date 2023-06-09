package com.example.taskmanager.controller;

import com.example.taskmanager.converter.ProjectConverter;
import com.example.taskmanager.dto.ProjectDto;
import com.example.taskmanager.dto.ProjectRequest;
import com.example.taskmanager.dto.ProjectResponse;
import com.example.taskmanager.model.Project;
import com.example.taskmanager.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProjectResponse> create(@Valid @RequestBody ProjectRequest projectRequest) {
        try {
            final Project parentProject = projectRequest.parentId() != null ?
                    projectService.getById(projectRequest.parentId()) : null;
            final Project newProject = ProjectConverter.toModel(projectRequest.title(), parentProject);
            final ProjectResponse projectResponse = ProjectConverter
                    .toDto(projectService.save(newProject), Collections.emptyList());
            return new ResponseEntity<>(projectResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @GetMapping("/root")
    public ResponseEntity<List<ProjectDto>> getRoot() {
        try {
            final List<ProjectDto> projectDtos = ProjectConverter.toDto(projectService.getRoot());
            return new ResponseEntity<>(projectDtos, projectDtos.size() > 0 ?
                    HttpStatus.OK : HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getById(@PathVariable("id") Long id) {
        try {
            final Project currentProject = projectService.getById(id);
            ProjectResponse projectResponse = ProjectConverter
                    .toDto(currentProject, projectService.getAllByParentProjectId(currentProject.getId()));
            return new ResponseEntity<>(projectResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> update(@PathVariable("id") Long id,
                                                  @RequestBody ProjectRequest projectRequest) {
        try {
            final Project currentProject = projectService.getById(id);
            final Project updatedProject = projectService.update(projectRequest, currentProject);
            final ProjectResponse projectResponse = ProjectConverter
                    .toDto(updatedProject, projectService.getAllByParentProjectId(updatedProject.getId()));
            return new ResponseEntity<>(projectResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectResponse> delete(@PathVariable("id") Long id) {
        try {
            projectService.delete(id);
            final ProjectResponse projectResponse = ProjectConverter.toDto(id);
            return new ResponseEntity<>(projectResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
