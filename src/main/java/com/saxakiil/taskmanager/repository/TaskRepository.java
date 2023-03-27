package com.saxakiil.taskmanager.repository;

import com.saxakiil.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByProject_Id(Long id);

    Optional<Task> findByTitle(String title);
}
