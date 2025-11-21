package com.smarttask.smart_task_manager.config;

import com.smarttask.smart_task_manager.entity.Role;
import com.smarttask.smart_task_manager.enums.RoleName;
import com.smarttask.smart_task_manager.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        createRoleIfMissing(RoleName.ROLE_USER);
        createRoleIfMissing(RoleName.ROLE_ADMIN);
        createRoleIfMissing(RoleName.ROLE_MANAGER);
        // Ajoutez d'autres rôles si nécessaire
    }

    private void createRoleIfMissing(RoleName roleName) {
        if (!roleRepository.existsByName(roleName)) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
    }
}
