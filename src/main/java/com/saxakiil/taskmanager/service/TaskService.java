package com.saxakiil.taskmanager.service;

import com.saxakiil.taskmanager.exception.RecordNotFoundException;
import com.saxakiil.taskmanager.model.Task;
import com.saxakiil.taskmanager.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.saxakiil.taskmanager.util.Constants.TASK_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    public List<Task> getAllByProjectId(Long id) {
        return taskRepository.findAllByProject_Id(id);
    }

    @SneakyThrows
    @Transactional
    public Task getById(Long id) {
        return taskRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format(TASK_NOT_EXIST, id)));
    }

    @Transactional
    public boolean isExistTitle(String title) {
        return taskRepository.findByTitle(title).isPresent();
    }

    @Transactional
    public Task update(Task updatedTask) {
        return taskRepository.save(updatedTask);
    }

    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
