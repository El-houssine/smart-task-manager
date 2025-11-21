package com.smarttask.smart_task_manager.dto.response;

public record AuthResponse(
        String accessToken,
        String refreshToken) {
}
