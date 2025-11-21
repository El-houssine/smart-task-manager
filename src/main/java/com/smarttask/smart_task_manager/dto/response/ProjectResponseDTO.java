package com.smarttask.smart_task_manager.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProjectResponseDTO(
        UUID id,
        String name,
        String description,
        LocalDateTime createdAt
) {
}
