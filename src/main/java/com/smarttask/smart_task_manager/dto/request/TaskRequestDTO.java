package com.smarttask.smart_task_manager.dto.request;

import com.smarttask.smart_task_manager.enums.Priority;
import com.smarttask.smart_task_manager.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record TaskRequestDTO(
        @NotBlank(message = "Title is mandatory")
        String title,

        @NotBlank(message = "Description is mandatory")
        String description,

        @NotNull(message = "Due date is mandatory")
        LocalDate dueDate,

        @NotNull(message = "Priority is mandatory")
        Priority priority,

        @NotBlank(message = "Category is mandatory")
        String category,

        @NotNull(message = "Assigned user is mandatory")
        UUID assignedUserId,

        @PositiveOrZero(message = "Estimated hours must be positive or zero")
        int estimatedHours,

        @NotNull(message = "Status is mandatory")
        TaskStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public TaskRequestDTO {
        if (status == null) {
            status = TaskStatus.TODO;
        }
    }
}
