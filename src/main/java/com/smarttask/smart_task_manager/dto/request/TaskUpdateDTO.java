package com.smarttask.smart_task_manager.dto.request;

import com.smarttask.smart_task_manager.enums.Priority;
import com.smarttask.smart_task_manager.enums.TaskStatus;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;


public record TaskUpdateDTO(
        @Nullable String title,
        @Nullable String description,
        @Nullable LocalDate dueDate,
        @Nullable Priority priority,
        @Nullable String category,
        @Nullable @PositiveOrZero Integer estimatedHours,
        @Nullable TaskStatus status
) {}
