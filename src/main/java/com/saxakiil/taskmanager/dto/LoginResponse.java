package com.saxakiil.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(description = "JWT token", example = "JWTToken")
        String token,
        @Schema(description = "Username", example = "12345678")
        String username,
        @Schema(description = "User role", example = "ROLE_USER")
        String role) {
}
