package com.smarttask.smart_task_manager.controller;


import com.smarttask.smart_task_manager.dto.request.UserUpdateDTO;
import com.smarttask.smart_task_manager.dto.response.UserResponseDTO;
import com.smarttask.smart_task_manager.entity.User;
import com.smarttask.smart_task_manager.enums.RoleName;
import com.smarttask.smart_task_manager.mapper.UserMapper;
import com.smarttask.smart_task_manager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Users Management")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @Operation(summary = "Get all Users")
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get current user profile", description = "Returns authenticated user's details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved profile"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDTO> getCurrentUserProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Fetching profile for user: {}", username);

        User user = userService.getAuthenticatedUser(username);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                .body(userMapper.toDTO(user));
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update current user profile")
    public ResponseEntity<UserResponseDTO> updateCurrentUser(
            @Valid @RequestBody UserUpdateDTO dto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        UserResponseDTO updatedUser = userService.updateCurrentUser(userDetails.getUsername(), dto);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{userId}/roles")
    @Operation(summary = "Assign Roles to User")
    public ResponseEntity<HttpMessage> assignRoles(
            @PathVariable String userId,
            @RequestBody Set<RoleName> roles
    ) {
        userService.assignRolesToUser(userId, roles);
        return ResponseEntity.ok().build();
    }
}
