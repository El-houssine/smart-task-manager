package com.smarttask.smart_task_manager.service.impl;

import com.smarttask.smart_task_manager.dto.request.TaskRequestDTO;
import com.smarttask.smart_task_manager.dto.request.TaskUpdateDTO;
import com.smarttask.smart_task_manager.dto.response.TaskResponseDTO;
import com.smarttask.smart_task_manager.entity.Task;
import com.smarttask.smart_task_manager.exception.TaskNotFoundException;
import com.smarttask.smart_task_manager.mapper.TaskMapper;
import com.smarttask.smart_task_manager.repository.TaskRepository;
import com.smarttask.smart_task_manager.service.TaskService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final AIService aiService;

    @Override
    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    @Override
    public TaskResponseDTO createTask(@Valid TaskRequestDTO taskRequestDTO) {
        Task task = taskMapper.toEntity(taskRequestDTO);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDTO(savedTask);
    }

    @Override
    public TaskResponseDTO updateTask(UUID taskId, TaskUpdateDTO taskUpdateDTO) {
        Task existingTask = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        taskMapper.updateEntityFromDTO(taskUpdateDTO, existingTask);
        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.toDTO(updatedTask);
    }

    @Override
    @Transactional
    public void deleteTask(UUID taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        taskRepository.delete(task);
    }

    @Override
    public void bulkDeleteTasks(Collection<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) return;

        taskRepository.deleteAllInBatch(tasks);
    }

    @Override
    public TaskResponseDTO getTaskById(UUID taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        return taskMapper.toDTO(task);
    }

    public List<String> getSubTasksSuggestion(UUID taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        return aiService.generateSubtasks(task.getDescription());
    }

}
