package com.saxakiil.taskmanager.dto;

import com.saxakiil.taskmanager.validator.UniqueTitleTaskConstraint;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskRequest(@NotNull(message = "Project cannot be null")
                          @Schema(description = "Project ID", example = "1")
                          Long projectId,
                          @NotNull(message = "Title cannot be null")
                          @UniqueTitleTaskConstraint
                          @Schema(description = "Project Title", example = "New task")
                          @Size(min = 8, max = 128, message = "The length of the title must be at least 8 characters" +
                                  " and not more that 128")
                          String title,
                          @NotNull(message = "Duty cannot be null")
                          @Schema(description = "Duty ID", example = "1")
                          Long dutyId,
                          @NotNull(message = "Phase cannot be null")
                          @Schema(description = "Phase ID", example = "2")
                          Long phaseId,
                          @Schema(description = "Project Description", example = "This is project description")
                          @Size(max = 1024, message = "The length of the description should not exceed 1024 characters")
                          String description) {
}
