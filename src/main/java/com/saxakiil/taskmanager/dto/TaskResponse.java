package com.saxakiil.taskmanager.dto;

import com.saxakiil.taskmanager.model.Duty;
import com.saxakiil.taskmanager.model.Phase;
import io.swagger.v3.oas.annotations.media.Schema;

public record TaskResponse(
        @Schema(description = "Task ID", example = "1")
        Long id,
        @Schema(description = "Project Title", example = "New task")
        String title,
        @Schema(description = "Duty object")
        Duty duty,
        @Schema(description = "Phase Object")
        Phase phase,
        @Schema(description = "Date of creation of the task", example = "2023-03-22 01:34:17.797")
        String createdDate,
        @Schema(description = "Date of modification of the task", example = "2023-03-22 01:34:17.797")
        String modifiedDate,
        @Schema(description = "Project Description", example = "This is project description")
        String description) {
}
