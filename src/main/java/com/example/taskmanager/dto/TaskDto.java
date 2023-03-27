package com.example.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record TaskDto(
        @Schema(description = "Task ID", example = "1")
        Long id,
        @Schema(description = "Project Title", example = "New task")
        String title) {
}
