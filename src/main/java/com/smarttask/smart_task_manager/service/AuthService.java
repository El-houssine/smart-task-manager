package com.smarttask.smart_task_manager.service;

import com.smarttask.smart_task_manager.dto.request.LoginRequest;
import com.smarttask.smart_task_manager.dto.request.RefreshTokenRequest;
import com.smarttask.smart_task_manager.dto.request.RegisterRequestDTO;
import com.smarttask.smart_task_manager.dto.response.AuthResponse;
import com.smarttask.smart_task_manager.dto.response.LoginResponse;
import com.smarttask.smart_task_manager.dto.response.UserResponseDTO;


public interface AuthService {
    UserResponseDTO register(RegisterRequestDTO request);

    LoginResponse login(LoginRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);
}
