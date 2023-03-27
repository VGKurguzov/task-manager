package com.saxakiil.taskmanager.converter;

import com.saxakiil.taskmanager.dto.ProjectDto;
import com.saxakiil.taskmanager.dto.ProjectResponse;
import com.saxakiil.taskmanager.model.Project;

import java.util.Collections;
import java.util.List;

public class ProjectConverter {

    private static List<ProjectDto> modelReformat(List<Project> subprojects) {
        return subprojects.stream()
                .map(subproject -> new ProjectDto(subproject.getId(),
                        subproject.getTitle(),
                        TaskConverter.modelReformat(subproject.getTasks()),
                        subproject.getParentProject() != null ? subproject.getParentProject().getId() : null))
                .toList();
    }
    public static List<ProjectDto> toDto(List<Project> projects) {
        return modelReformat(projects);
    }

    public static ProjectResponse toDto(Project project, List<Project> subprojects) {
        return new ProjectResponse(project.getId(), project.getTitle(), modelReformat(subprojects),
                TaskConverter.modelReformat(project.getTasks()),
                project.getParentProject() != null ? project.getParentProject().getId() : null);
    }

    public static ProjectResponse toDto(Long id) {
        return new ProjectResponse(id, null, null, null, null);
    }

    public static Project toModel(String title, Project parentProject) {
        return new Project(null, title, Collections.emptyList(), parentProject);
    }
}
