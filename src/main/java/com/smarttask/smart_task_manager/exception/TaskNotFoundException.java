package com.smarttask.smart_task_manager.exception;

import java.util.UUID;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(UUID taskId) {
        super("Task not found with id: " + taskId);
    }
}
