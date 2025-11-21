package com.smarttask.smart_task_manager.repository;

import com.smarttask.smart_task_manager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    @Modifying
    @Query("DELETE FROM Task t WHERE t IN :tasks")
    void deleteAllInTaskList(@Param("tasks") List<Task> tasks);
}

