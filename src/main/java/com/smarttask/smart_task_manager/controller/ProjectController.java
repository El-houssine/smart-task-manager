package com.smarttask.smart_task_manager.controller;

import com.smarttask.smart_task_manager.dto.request.ProjectRequestDTO;
import com.smarttask.smart_task_manager.dto.response.ProjectResponseDTO;
import com.smarttask.smart_task_manager.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Project Controller", description = "Projects Management")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new project", description = "Create a new project with provided information")
    public ProjectResponseDTO createProject(@Valid @RequestBody ProjectRequestDTO dto) {
        return projectService.createProject(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Get all projects", description = "Fetch all projects from the system")
    public List<ProjectResponseDTO> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update entire project", description = "Replace all project data")
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @Parameter(description = "UUID of project to update", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id, @Valid @RequestBody ProjectRequestDTO projectRequestDTO) {

        ProjectResponseDTO projectResponseDTO = projectService.updateProject(id, projectRequestDTO);
        return new ResponseEntity<>(projectResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Project",description = " Delete a project with id")
    public ResponseEntity<ProjectResponseDTO> deleteProject(@PathVariable UUID id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a Project", description = "Get a project by id ")
    public ResponseEntity<ProjectResponseDTO> getProject(@PathVariable UUID id) {
        ProjectResponseDTO projectResponseDTO = projectService.getProject(id);
        return new ResponseEntity<>(projectResponseDTO, HttpStatus.OK);
    }
}
