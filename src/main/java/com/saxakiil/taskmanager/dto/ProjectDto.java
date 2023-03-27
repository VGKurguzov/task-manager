package com.saxakiil.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ProjectDto(@Schema(description = "Project ID", example = "1")
                         Long id,
                         @Schema(description = "Project title", example = "New title")
                         String title,
                         @Schema(description = "List of tasks for the project")
                         List<TaskDto> tasks,
                         @Schema(description = "ID of the parent project", example = "1")
                         Long parentId) {
}
