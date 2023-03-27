package com.example.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotNull(message = "Username cannot be null")
        @Schema(description = "Username", example = "user")
        @Size(min = 8, max = 32, message = "The length of the username must be at least 8 characters" +
                " and not more that 32")
        String username,
        @NotNull(message = "Password cannot be null")
        @Schema(description = "User password", example = "password")
        @Size(min = 8, max = 32, message = "The length of the password must be at least 8 characters" +
                " and not more that 32")
        String password) {
}
