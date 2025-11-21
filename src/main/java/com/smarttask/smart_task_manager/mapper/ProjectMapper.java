package com.smarttask.smart_task_manager.mapper;

import com.smarttask.smart_task_manager.dto.request.ProjectRequestDTO;
import com.smarttask.smart_task_manager.dto.response.ProjectResponseDTO;
import com.smarttask.smart_task_manager.entity.Project;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectResponseDTO toDTO(Project project);

    Project toEntity(ProjectRequestDTO dto);

    void updateProjectFromDto(ProjectRequestDTO dto, @MappingTarget Project entity);

}
