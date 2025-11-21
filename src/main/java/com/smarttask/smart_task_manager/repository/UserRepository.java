package com.smarttask.smart_task_manager.repository;

import com.smarttask.smart_task_manager.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);


    boolean existsByUsername(@NotBlank(message = "Email is mandatory") @Email(message = "Invalid email format") String username);

    Optional<User> findUserById(UUID userId);
}

