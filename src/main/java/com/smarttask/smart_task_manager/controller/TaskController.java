package com.smarttask.smart_task_manager.controller;

import com.smarttask.smart_task_manager.dto.request.TaskRequestDTO;
import com.smarttask.smart_task_manager.dto.request.TaskUpdateDTO;
import com.smarttask.smart_task_manager.dto.response.TaskResponseDTO;
import com.smarttask.smart_task_manager.exception.TaskNotFoundException;
import com.smarttask.smart_task_manager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Controller", description = "Operations related to tasks")
public class TaskController {

    private final TaskService taskService;


    @GetMapping
    @Operation(summary = "Get all tasks", description = "Fetch all tasks from the system")
    public List<TaskResponseDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping
    @Operation(summary = "Create a new task", description = "Creates a new task with the provided information")
    public TaskResponseDTO createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        return taskService.createTask(taskRequestDTO);
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "Get task", description = "Get a task by the provided task Id")
    public TaskResponseDTO getTaskById(@PathVariable UUID taskId) {
        return taskService.getTaskById(taskId);
    }

    @PutMapping("/{taskId}")
    @Operation(
            summary = "Update a task",
            description = "Updates an existing task with the provided data",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task successfully updated"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "Task not found")
            })
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDTO updateTask(
            @Parameter(description = "ID of the task to update", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID taskId,

            @Parameter(description = "Task data for update", required = true)
            @Valid @RequestBody TaskUpdateDTO taskUpdateDTO) {

        log.debug("Received update request for task ID: {}", taskId);
        return taskService.updateTask(taskId, taskUpdateDTO);
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "Delete task", description = "Delete a task by the provided task Id")
    public ResponseEntity<String> deleteTask(@PathVariable UUID taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok("Task deleted successfully");
    }

    @PostMapping("/{taskId}/suggestions")
    public List<String> generateTaskSuggestions(@PathVariable UUID taskId) {
        return taskService.getSubTasksSuggestion(taskId);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFoundException(TaskNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
