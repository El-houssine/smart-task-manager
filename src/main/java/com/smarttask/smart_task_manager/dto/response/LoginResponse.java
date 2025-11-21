package com.smarttask.smart_task_manager.dto.response;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        UserResponseDTO user
) {}
