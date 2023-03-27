package com.saxakiil.taskmanager.dto;

import com.saxakiil.taskmanager.validator.UniqueTitleProjectConstraint;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProjectRequest(@NotNull(message = "Title cannot be null")
                             @UniqueTitleProjectConstraint
                             @Schema(description = "Project title", example = "New project")
                             @Size(min = 8, max = 128, message = "The length of the title must be at least" +
                                     " 8 characters and not more that 128")
                             String title,
                             @Schema(description = "ID of the parent project", example = "1")
                             Long parentId) {
}
