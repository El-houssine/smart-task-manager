package com.smarttask.smart_task_manager.dto.response;


import com.smarttask.smart_task_manager.enums.Priority;
import com.smarttask.smart_task_manager.enums.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponseDTO(
        UUID id,
        String title,
        String description,
        LocalDate dueDate,
        Priority priority,
        String category,
        UUID assignedUserId,
        int estimatedHours,
        TaskStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
