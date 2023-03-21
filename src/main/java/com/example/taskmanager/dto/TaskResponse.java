package com.example.taskmanager.dto;

import com.example.taskmanager.model.Duty;
import com.example.taskmanager.model.Phase;

public record TaskResponse(Long id, String title, Duty duty, Phase phase,
                           String createdDate, String modifiedDate, String description) {
}
