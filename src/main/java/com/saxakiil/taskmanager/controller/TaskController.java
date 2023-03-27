package com.saxakiil.taskmanager.controller;

import com.saxakiil.taskmanager.converter.TaskConverter;
import com.saxakiil.taskmanager.dto.TaskRequest;
import com.saxakiil.taskmanager.dto.TaskResponse;
import com.saxakiil.taskmanager.dto.error.ErrorDto;
import com.saxakiil.taskmanager.model.Duty;
import com.saxakiil.taskmanager.model.Phase;
import com.saxakiil.taskmanager.model.Project;
import com.saxakiil.taskmanager.model.RoleEnum;
import com.saxakiil.taskmanager.model.Task;
import com.saxakiil.taskmanager.model.User;
import com.saxakiil.taskmanager.service.DutyService;
import com.saxakiil.taskmanager.service.PhaseService;
import com.saxakiil.taskmanager.service.ProjectService;
import com.saxakiil.taskmanager.service.TaskService;
import com.saxakiil.taskmanager.service.UserService;
import com.saxakiil.taskmanager.util.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Task", description = "Task APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final PhaseService phaseService;
    private final DutyService dutyService;
    private final UserService userService;

    @Operation(
            summary = "Create task",
            description = "Create task")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content
                    (schema = @Schema(implementation = TaskResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema(implementation = ErrorDto.class))}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @PostMapping("/")
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest taskRequest) {
        User user = userService.getAuthUser();
        final Project project = projectService.getById(taskRequest.projectId());
        final Phase phase = phaseService.getById(taskRequest.phaseId());
        final Duty duty = dutyService.getById(taskRequest.dutyId());
        final Task newTask = TaskConverter.toModel(taskRequest, project, user, phase, duty, Instant.now().toEpochMilli());
        final TaskResponse taskResponse = TaskConverter.toDto(taskService.save(newTask));
        return new ResponseEntity<>(taskResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get task by id",
            description = "Get task by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content
                    (schema = @Schema(implementation = TaskResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema(implementation = ErrorDto.class))}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getById(@PathVariable("id") Long id) {
        final Task task = taskService.getById(id);
        final TaskResponse taskResponse = TaskConverter.toDto(task);
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Update phase for task",
            description = "Update task for phase")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content
                    (schema = @Schema(implementation = TaskResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema(implementation = ErrorDto.class))}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @PutMapping("/updatePhase/{id}")
    public ResponseEntity<TaskResponse> updatePhase(@PathVariable("id") Long id,
                                                    @RequestBody TaskRequest taskRequest) {
        final Task currentTask = taskService.getById(id);
        final Phase phase = phaseService.getById(taskRequest.phaseId());
        currentTask.setPhase(phase);
        currentTask.setModifiedDate(Instant.now().toEpochMilli());
        final TaskResponse taskResponse = TaskConverter.toDto(taskService.update(currentTask));
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Update task by id",
            description = "Update task by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content
                    (schema = @Schema(implementation = TaskResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema(implementation = ErrorDto.class))}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable("id") Long id,
                                               @RequestBody TaskRequest taskRequest) {
        final Task currentTask = taskService.getById(id);
        final Project project = taskRequest.projectId() != null ? projectService
                .getById(taskRequest.projectId()) : null;
        final Phase phase = taskRequest.phaseId() != null ? phaseService
                .getById(taskRequest.phaseId()) : null;
        final Task updatedTask = taskService.update(TaskConverter.merge(taskRequest, currentTask, project, phase));
        final TaskResponse taskResponse = TaskConverter.toDto(updatedTask);
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete task by id",
            description = "Delete task by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content
                    (schema = @Schema(implementation = TaskResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema(implementation = ErrorDto.class))}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<TaskResponse> delete(@PathVariable("id") Long id) {
        User user = userService.getAuthUser();
        final Task task = taskService.getById(id);
        if ((Utils.isAuth(user, RoleEnum.ROLE_USER) && task.getOwner().getId().equals(user.getId()))
                || (Utils.isAuth(user, RoleEnum.ROLE_ADMIN))) {
            taskService.delete(task.getId());
            final TaskResponse taskResponse = TaskConverter.toDto(task);
            return new ResponseEntity<>(taskResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
