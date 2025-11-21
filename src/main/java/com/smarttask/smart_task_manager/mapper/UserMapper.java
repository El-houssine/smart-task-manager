package com.smarttask.smart_task_manager.mapper;

import com.smarttask.smart_task_manager.dto.request.RegisterRequestDTO;
import com.smarttask.smart_task_manager.dto.request.UserUpdateDTO;
import com.smarttask.smart_task_manager.dto.response.UserResponseDTO;
import com.smarttask.smart_task_manager.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(RegisterRequestDTO user);

    UserResponseDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    void updateUserFromDTO(UserUpdateDTO dto, @MappingTarget User user);
}
