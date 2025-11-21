package com.smarttask.smart_task_manager.service;

import com.smarttask.smart_task_manager.dto.request.TaskRequestDTO;
import com.smarttask.smart_task_manager.dto.request.TaskUpdateDTO;
import com.smarttask.smart_task_manager.dto.response.TaskResponseDTO;
import com.smarttask.smart_task_manager.entity.Task;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface TaskService {
    List<TaskResponseDTO> getAllTasks();

    TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO);

    TaskResponseDTO updateTask(UUID taskId, TaskUpdateDTO taskUpdateDTO);

    void bulkDeleteTasks(Collection<Task> tasks);

    void deleteTask(UUID taskId);

    TaskResponseDTO getTaskById(UUID taskId);

    List<String> getSubTasksSuggestion(UUID taskId);
}
