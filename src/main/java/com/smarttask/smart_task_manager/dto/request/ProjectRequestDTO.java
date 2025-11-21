package com.smarttask.smart_task_manager.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ProjectRequestDTO(
        @NotBlank String name,
        String description
) {
}
