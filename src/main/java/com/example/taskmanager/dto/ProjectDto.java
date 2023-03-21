package com.example.taskmanager.dto;

import java.util.List;

public record ProjectDto(Long id, String title, List<TaskDto> tasks, Long parentId) {
}
