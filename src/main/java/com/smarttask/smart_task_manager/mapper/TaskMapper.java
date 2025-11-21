package com.smarttask.smart_task_manager.mapper;


import com.smarttask.smart_task_manager.dto.request.TaskRequestDTO;
import com.smarttask.smart_task_manager.dto.request.TaskUpdateDTO;
import com.smarttask.smart_task_manager.dto.response.TaskResponseDTO;
import com.smarttask.smart_task_manager.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "assignedUser.id", target = "assignedUserId")
    TaskResponseDTO toDTO(Task task);

    @Mapping(source = "assignedUserId", target = "assignedUser.id")
    Task toEntity(TaskRequestDTO dto);

    void updateEntityFromDTO(TaskUpdateDTO taskUpdateDTO, @MappingTarget Task task);


}
