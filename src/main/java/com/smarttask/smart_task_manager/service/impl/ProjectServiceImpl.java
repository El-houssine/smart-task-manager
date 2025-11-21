package com.smarttask.smart_task_manager.service.impl;

import com.smarttask.smart_task_manager.dto.request.ProjectRequestDTO;
import com.smarttask.smart_task_manager.dto.response.ProjectResponseDTO;
import com.smarttask.smart_task_manager.entity.Project;
import com.smarttask.smart_task_manager.exception.ProjectNotFoundException;
import com.smarttask.smart_task_manager.mapper.ProjectMapper;
import com.smarttask.smart_task_manager.repository.ProjectRepository;
import com.smarttask.smart_task_manager.service.ProjectService;
import com.smarttask.smart_task_manager.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskService taskService;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectResponseDTO createProject(ProjectRequestDTO dto) {
        Project project = Project.builder()
                .name(dto.name())
                .description(dto.description())
                .build();
        return projectMapper.toDTO(projectRepository.save(project));
    }

    @Override
    public ProjectResponseDTO updateProject(UUID projectId, ProjectRequestDTO dto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(" Project not found "));
        projectMapper.updateProjectFromDto(dto, project);
        Project updatedProject = projectRepository.save(project);

        return projectMapper.toDTO(updatedProject);
    }

    @Override
    public void deleteProject(UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        taskService.bulkDeleteTasks(project.getTasks());
        projectRepository.delete(project);
    }

    @Override
    public ProjectResponseDTO getProject(UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        return projectMapper.toDTO(project);
    }

    @Override
    public List<ProjectResponseDTO> getAllProjects() {

        return projectRepository.findAll()
                .stream().map(projectMapper::toDTO)
                .toList();
    }
}
