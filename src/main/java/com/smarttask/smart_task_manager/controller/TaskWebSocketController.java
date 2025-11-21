package com.smarttask.smart_task_manager.controller;

import com.smarttask.smart_task_manager.dto.request.TaskUpdateDTO;
import com.smarttask.smart_task_manager.dto.response.TaskResponseDTO;
import com.smarttask.smart_task_manager.entity.Task;
import com.smarttask.smart_task_manager.mapper.TaskMapper;
import com.smarttask.smart_task_manager.service.TaskService;
import com.smarttask.smart_task_manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class TaskWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final TaskService taskService;

    @MessageMapping("/tasks/{taskId}/update")
    @SendTo("/topic/tasks/{taskId}")
    public TaskResponseDTO handleTaskUpdate(
            @DestinationVariable UUID taskId,
            @Payload TaskUpdateDTO update
    ) {
        TaskResponseDTO updatedTask = taskService.updateTask(taskId, update);
        messagingTemplate.convertAndSend("/topic/tasks/" + taskId, updatedTask);
        return updatedTask;
    }
}
