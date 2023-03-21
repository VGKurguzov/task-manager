package com.example.taskmanager.controller;

import com.example.taskmanager.converter.TaskConverter;
import com.example.taskmanager.dto.TaskRequest;
import com.example.taskmanager.dto.TaskResponse;
import com.example.taskmanager.model.Duty;
import com.example.taskmanager.model.Phase;
import com.example.taskmanager.model.Project;
import com.example.taskmanager.model.RoleEnum;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.service.DutyService;
import com.example.taskmanager.service.PhaseService;
import com.example.taskmanager.service.ProjectService;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.service.UserService;
import com.example.taskmanager.util.Utils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final PhaseService phaseService;
    private final DutyService dutyService;
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @PostMapping("/")
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest taskRequest) {
        try {
            User user = userService.getAuthUser();
            final Project project = projectService.getById(taskRequest.projectId());
            final Phase phase = phaseService.getById(taskRequest.phaseId());
            final Duty duty = dutyService.getById(taskRequest.dutyId());
            final Task newTask = TaskConverter.toModel(taskRequest, project, user, phase, duty, Instant.now().toEpochMilli());
            final TaskResponse taskResponse = TaskConverter.toDto(taskService.save(newTask));
            return new ResponseEntity<>(taskResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getById(@PathVariable("id") Long id) {
        try {
            final Task task = taskService.getById(id);
            final TaskResponse taskResponse = TaskConverter.toDto(task);
            return new ResponseEntity<>(taskResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @PutMapping("/updatePhase/{id}")
    public ResponseEntity<TaskResponse> updatePhase(@PathVariable("id") Long id,
                                                    @RequestBody TaskRequest taskRequest) {
        try {
            final Task currentTask = taskService.getById(id);
            final Phase phase = phaseService.getById(taskRequest.phaseId());
            currentTask.setPhase(phase);
            currentTask.setModifiedDate(Instant.now().toEpochMilli());
            final TaskResponse taskResponse = TaskConverter.toDto(taskService.update(currentTask));
            return new ResponseEntity<>(taskResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable("id") Long id,
                                               @RequestBody TaskRequest taskRequest) {
        try {
            final Task currentTask = taskService.getById(id);
            final Project project = taskRequest.projectId() != null ? projectService
                    .getById(taskRequest.projectId()) : null;
            final Phase phase = taskRequest.phaseId() != null ? phaseService
                    .getById(taskRequest.phaseId()) : null;
            final Task updatedTask = taskService.update(TaskConverter.merge(taskRequest, currentTask, project, phase));
            final TaskResponse taskResponse = TaskConverter.toDto(updatedTask);
            return new ResponseEntity<>(taskResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<TaskResponse> delete(@PathVariable("id") Long id) {
        try {
            User user = userService.getAuthUser();
            final Task task = taskService.getById(id);
            if ((Utils.isAuth(user, RoleEnum.ROLE_USER) && task.getOwner().getId().equals(user.getId()))
                    || (Utils.isAuth(user, RoleEnum.ROLE_ADMIN))) {
                taskService.delete(task.getId());
                final TaskResponse taskResponse = TaskConverter.toDto(task);
                return new ResponseEntity<>(taskResponse, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
