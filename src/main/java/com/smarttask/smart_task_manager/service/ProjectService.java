package com.smarttask.smart_task_manager.service;

import com.smarttask.smart_task_manager.dto.request.ProjectRequestDTO;
import com.smarttask.smart_task_manager.dto.response.ProjectResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    public ProjectResponseDTO createProject(ProjectRequestDTO dto);

    public ProjectResponseDTO updateProject(UUID projectId, ProjectRequestDTO dto);

    public void deleteProject(UUID projectId);

    public ProjectResponseDTO getProject(UUID projectId);

    public List<ProjectResponseDTO> getAllProjects();
}
