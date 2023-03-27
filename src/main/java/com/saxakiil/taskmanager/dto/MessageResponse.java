package com.saxakiil.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record MessageResponse(
        @Schema(description = "Message", example = "Test message")
        String message) {
}
