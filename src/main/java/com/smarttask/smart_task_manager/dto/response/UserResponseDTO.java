package com.smarttask.smart_task_manager.dto.response;

import com.smarttask.smart_task_manager.entity.Role;

import java.util.Set;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String firstname,
        String lastname,
        String username,
        Set<Role> roles
) {}
