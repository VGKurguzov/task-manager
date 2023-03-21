package com.example.taskmanager.converter;

import com.example.taskmanager.dto.TaskDto;
import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.model.Duty;
import com.example.taskmanager.model.Phase;
import com.example.taskmanager.model.Project;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.util.Utils;

import java.time.Instant;
import java.util.List;

public class TaskConverter {

    public static List<TaskDto> modelReformat(List<Task> tasks) {
        return tasks.stream()
                .map(task -> new TaskDto(task.getId(), task.getTitle()))
                .toList();
    }

    public static TaskResponse toDto(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDuty(), task.getPhase(),
                Utils.millisecondToTime(task.getCreatedDate()),
                task.getModifiedDate() != null ? Utils.millisecondToTime(task.getModifiedDate()) : null,
                task.getDescription());
    }

    public static Task toModel(TaskRequest taskRequest, Project project, User owner, Phase phase, Duty duty, Long createdDate) {
        return new Task(null, taskRequest.title(), taskRequest.description(), project, owner, duty,
                phase, createdDate, null);
    }

    public static Task merge(TaskRequest taskRequest, Task currentTask, Project project, Phase phase) {
        return new Task(currentTask.getId(),
                taskRequest.title() != null ? taskRequest.title() : currentTask.getTitle(),
                taskRequest.description() != null ? taskRequest.description() : currentTask.getDescription(),
                project != null ? project : currentTask.getProject(),
                currentTask.getOwner(),
                currentTask.getDuty(),
                phase != null ? phase : currentTask.getPhase(),
                currentTask.getCreatedDate(),
                Instant.now().toEpochMilli());
    }
}
