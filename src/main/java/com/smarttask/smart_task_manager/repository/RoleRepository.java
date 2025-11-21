package com.smarttask.smart_task_manager.repository;

import com.smarttask.smart_task_manager.entity.Role;
import com.smarttask.smart_task_manager.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);

    boolean existsByName(RoleName roleName);
}
