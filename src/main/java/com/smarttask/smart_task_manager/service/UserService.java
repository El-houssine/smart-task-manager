package com.smarttask.smart_task_manager.service;

import com.smarttask.smart_task_manager.dto.request.UserUpdateDTO;
import com.smarttask.smart_task_manager.dto.response.UserResponseDTO;
import com.smarttask.smart_task_manager.entity.User;
import com.smarttask.smart_task_manager.enums.RoleName;

import java.util.List;
import java.util.Set;

public interface UserService {
    void assignRolesToUser(String user, Set<RoleName> roleNames);

    List<UserResponseDTO> getAllUsers();

    User getAuthenticatedUser(String username);

    UserResponseDTO updateCurrentUser(String username, UserUpdateDTO dto);
}
