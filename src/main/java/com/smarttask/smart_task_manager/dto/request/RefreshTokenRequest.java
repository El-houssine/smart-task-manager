package com.smarttask.smart_task_manager.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "The refresh token is mandatory")
        String refreshToken
) {}