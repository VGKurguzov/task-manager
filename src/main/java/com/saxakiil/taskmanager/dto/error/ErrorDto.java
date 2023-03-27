package com.saxakiil.taskmanager.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorDto(@Schema(description = "Error status", example = "401")
                       Integer status,
                       @Schema(description = "Error reason phrase", example = "Unauthorized")
                       String error,
                       @Schema(description = "Endpoint path", example = "/api/projects/1")
                       String path,
                       @Schema(description = "Error message",
                               example = "Full authentication is required to access this resource")
                       String message,
                       @Schema(description = "Error timestamp", example = "2023-03-27T01:14:27.007853")
                       String timestamp) {
}