package com.smarttask.smart_task_manager.controller;

import com.smarttask.smart_task_manager.dto.request.LoginRequest;
import com.smarttask.smart_task_manager.dto.request.RefreshTokenRequest;
import com.smarttask.smart_task_manager.dto.request.RegisterRequestDTO;
import com.smarttask.smart_task_manager.dto.response.AuthResponse;
import com.smarttask.smart_task_manager.dto.response.LoginResponse;
import com.smarttask.smart_task_manager.dto.response.UserResponseDTO;
import com.smarttask.smart_task_manager.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Authentication Management")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "User login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(
            summary = "Register a new user",
            description = "Create a user account with the provided information"
    )
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO register(
            @Valid @RequestBody RegisterRequestDTO request
    ) {
        return authService.register(request);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Refresh the token", description = "Generates a new access token from a valid refresh token")
    @ApiResponse(responseCode = "200", description = "Tokens successfully regenerated")
    @ApiResponse(responseCode = "401", description = "Invalid or expired token")
    public AuthResponse refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }
}