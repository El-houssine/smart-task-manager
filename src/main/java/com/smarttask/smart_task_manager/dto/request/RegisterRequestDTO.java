package com.smarttask.smart_task_manager.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record RegisterRequestDTO(
        @NotBlank(message = "First name is mandatory")
        String firstname,

        @NotBlank(message = "Last name is mandatory")
        String lastname,

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Invalid email format")
        String username,

        @NotBlank(message = "Password is mandatory")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
        )
        String password
) {}

