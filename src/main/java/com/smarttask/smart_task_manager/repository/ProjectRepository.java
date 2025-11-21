package com.smarttask.smart_task_manager.repository;

import com.smarttask.smart_task_manager.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Project findByName(String name);
    Project findProjectById(UUID id);
}
