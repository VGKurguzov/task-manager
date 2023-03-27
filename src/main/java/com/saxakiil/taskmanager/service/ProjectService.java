package com.saxakiil.taskmanager.service;

import com.saxakiil.taskmanager.dto.ProjectRequest;
import com.saxakiil.taskmanager.exception.RecordNotFoundException;
import com.saxakiil.taskmanager.model.Project;
import com.saxakiil.taskmanager.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.saxakiil.taskmanager.util.Constants.INCORRECT_PROJECT_UPDATE;
import static com.saxakiil.taskmanager.util.Constants.PROJECT_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskService taskService;

    private boolean isParentRelation(Long newParentId, Long currentProjectId) {
        if (currentProjectId == null || newParentId == null) {
            return false;
        }
        final Project newParent = projectRepository
                .findById(newParentId).get();
        if (newParent.getId().equals(currentProjectId)) {
            return true;
        }
        return isParentRelation(newParent.getParentProject() != null ? newParent.getParentProject().getId() : null,
                currentProjectId);
    }

    @SneakyThrows
    private Project mergeParentProject(Long newParentId, Project currentProject) {
        if (currentProject.getParentProject() == null || isParentRelation(newParentId,
                currentProject.getId()) || (newParentId != null && newParentId.equals(currentProject.getId()))) {
            throw new Exception(String.format(INCORRECT_PROJECT_UPDATE, currentProject.getId()));
        }
        return newParentId != null ? projectRepository
                .findById(newParentId)
                .orElseThrow(() -> new RecordNotFoundException(String.format(PROJECT_NOT_EXIST,
                        newParentId))) : null;
    }

    private void deleteRecurse(Long id) {
        projectRepository.findAllProjectsByParentProjectId(id)
                .forEach(project -> {
                    taskService.getAllByProjectId(project.getId())
                            .forEach(task -> taskService.delete(task.getId()));
                    deleteRecurse(project.getId());
                });
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        }
    }

    @Transactional
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Transactional
    public boolean isExistTitle(String title) {
        return projectRepository.findByTitle(title).isPresent();
    }

    @Transactional
    public List<Project> getRoot() {
        return projectRepository.findAllRootProjects();
    }

    @SneakyThrows
    @Transactional
    public Project getById(Long id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format(PROJECT_NOT_EXIST, id)));
    }

    @Transactional
    public List<Project> getAllByParentProjectId(Long id) {
        return projectRepository.findAllProjectsByParentProjectId(id);
    }

    @Transactional
    public Project update(ProjectRequest projectRequest, Project project) {
        final Project updatedProject = new Project(project.getId(),
                projectRequest.title() != null ? projectRequest.title() : project.getTitle(),
                Collections.emptyList(),
                mergeParentProject(projectRequest.parentId(), project));
        return projectRepository.save(updatedProject);
    }

    @SneakyThrows
    @Transactional
    public void delete(Long id) {
        deleteRecurse(id);
    }
}
