package com.example.taskmanager.dto;

import com.example.taskmanager.validator.UniqueTitleTaskConstraint;
import jakarta.validation.constraints.NotNull;

public record TaskRequest(@NotNull(message = "Project cannot be null")
                          Long projectId,
                          @NotNull(message = "Title cannot be null")
                          @UniqueTitleTaskConstraint
                          String title,
                          @NotNull(message = "Duty cannot be null")
                          Long dutyId,
                          @NotNull(message = "Phase cannot be null")
                          Long phaseId,
                          String description) {
}
