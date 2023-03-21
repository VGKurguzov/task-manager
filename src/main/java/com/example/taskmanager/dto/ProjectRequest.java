package com.example.taskmanager.dto;

import com.example.taskmanager.validator.UniqueTitleProjectConstraint;
import jakarta.validation.constraints.NotNull;

public record ProjectRequest(@NotNull(message = "Title cannot be null")
                             @UniqueTitleProjectConstraint
                             String title,
                             Long parentId) {
}
