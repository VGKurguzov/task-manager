package com.example.taskmanager.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ProjectResponse(Long id, String title, List<ProjectDto> subprojects, List<TaskDto> tasks,
                              Long parentId, String message) {
}
