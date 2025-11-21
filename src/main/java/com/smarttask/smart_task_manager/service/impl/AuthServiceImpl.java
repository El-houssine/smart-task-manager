package com.smarttask.smart_task_manager.service.impl;

import com.smarttask.smart_task_manager.config.security.jwt.JwtService;
import com.smarttask.smart_task_manager.dto.request.LoginRequest;
import com.smarttask.smart_task_manager.dto.request.RefreshTokenRequest;
import com.smarttask.smart_task_manager.dto.request.RegisterRequestDTO;
import com.smarttask.smart_task_manager.dto.response.AuthResponse;
import com.smarttask.smart_task_manager.dto.response.LoginResponse;
import com.smarttask.smart_task_manager.dto.response.UserResponseDTO;
import com.smarttask.smart_task_manager.entity.Role;
import com.smarttask.smart_task_manager.entity.User;
import com.smarttask.smart_task_manager.enums.RoleName;
import com.smarttask.smart_task_manager.exception.EmailAlreadyExistsException;
import com.smarttask.smart_task_manager.exception.RoleNotFoundException;
import com.smarttask.smart_task_manager.mapper.UserMapper;
import com.smarttask.smart_task_manager.repository.RoleRepository;
import com.smarttask.smart_task_manager.repository.UserRepository;
import com.smarttask.smart_task_manager.service.AuthService;
import io.jsonwebtoken.JwtException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Builder
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        org.springframework.security.core.userdetails.User securityUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        User user = userRepository.findByUsername(securityUser.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new LoginResponse(
                accessToken,
                refreshToken,
                userMapper.toDTO(user)
        );
    }

    public UserResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new EmailAlreadyExistsException("Email already exist");
        }

        User user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .roles(getDefaultRoles())
                .build();
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();

        if (!jwtService.isTokenValid(refreshToken)) {
            throw new JwtException("Refresh token invalid");
        }

        String username = jwtService.extractUsername(refreshToken);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.getRefreshToken().equals(refreshToken)) {
            throw new SecurityException("Refresh token compromise");
        }

        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        return new AuthResponse(newAccessToken, newRefreshToken);
    }

    private Set<Role> getDefaultRoles() {
        return roleRepository.findByName(RoleName.ROLE_USER)
                .map(Set::of)
                .orElseThrow(() -> new RoleNotFoundException("Role USER not found"));
    }
}
