package com.smarttask.smart_task_manager.service.impl;

import com.smarttask.smart_task_manager.dto.request.UserUpdateDTO;
import com.smarttask.smart_task_manager.dto.response.UserResponseDTO;
import com.smarttask.smart_task_manager.entity.Role;
import com.smarttask.smart_task_manager.entity.User;
import com.smarttask.smart_task_manager.enums.RoleName;
import com.smarttask.smart_task_manager.exception.UserNotFoundException;
import com.smarttask.smart_task_manager.mapper.UserMapper;
import com.smarttask.smart_task_manager.repository.RoleRepository;
import com.smarttask.smart_task_manager.repository.UserRepository;
import com.smarttask.smart_task_manager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void assignRolesToUser(String username, Set<RoleName> roleNames) {
        Set<Role> roles = roleNames.stream()
                .map(roleRepository::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));
        user.getRoles().addAll(roles);
        userRepository.save(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO).toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .build();
    }

    @Transactional(readOnly = true)
    public User getAuthenticatedUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("Authenticated user not found in database: {}", username);
                    return new UserNotFoundException("User not found with username: " + username);
                });
    }

    @Override
    @Transactional
    public UserResponseDTO updateCurrentUser(String username, UserUpdateDTO dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        userMapper.updateUserFromDTO(dto, user);
        User savedUser = userRepository.save(user);

        return userMapper.toDTO(savedUser);
    }
}
