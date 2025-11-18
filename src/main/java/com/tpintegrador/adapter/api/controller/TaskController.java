package com.tpintegrador.adapter.api.controller;

import com.tpintegrador.adapter.api.dto.ChangeTaksStatusRequestDTO;
import com.tpintegrador.adapter.api.dto.CreateTaskRequestDTO;
import com.tpintegrador.adapter.api.dto.TaskResponseDTO;
import com.tpintegrador.adapter.api.mapper.ApiMapper;
import com.tpintegrador.domain.model.Task;
import com.tpintegrador.domain.usecase.ChangeTaskStateUseCase;
import com.tpintegrador.domain.usecase.CreateTaskUseCase;
import com.tpintegrador.domain.usecase.GetTaskByIdUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final GetTaskByIdUseCase getTaskByIdUseCase;
    private final ApiMapper apiMapper;
    private final ChangeTaskStateUseCase changeTaskStateUseCase;

    public TaskController(
            CreateTaskUseCase createTaskUseCase,
            GetTaskByIdUseCase getTaskByIdUseCase,
            ApiMapper apiMapper,
            ChangeTaskStateUseCase changeTaskStateUseCase
    ) {
        this.createTaskUseCase = createTaskUseCase;
        this.getTaskByIdUseCase = getTaskByIdUseCase;
        this.apiMapper = apiMapper;
        this.changeTaskStateUseCase = changeTaskStateUseCase;
    }

    // Endpoint para crear una nueva tarea en un proyecto
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(
            @PathVariable Long projectId,
            @Valid @RequestBody CreateTaskRequestDTO request
    ) {
        Long taskId = createTaskUseCase.createTask(
                projectId,
                request.title(),
                request.estimateHours(),
                request.assignee(),
                request.status()
        );

        Task newTask = getTaskByIdUseCase.getTaskById(taskId);
        TaskResponseDTO response = apiMapper.toTaskResponseDTO(newTask);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Endpoint para buscar una tarea por id
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long taskId) {
        return ResponseEntity.ok(apiMapper.toTaskResponseDTO(getTaskByIdUseCase.getTaskById(taskId)));
    }

    // Endpoint para cambiar el estado de una task
    @PostMapping("/{taskId}/status")
    public ResponseEntity<Void> changeTaskStatus(@PathVariable Long taskId, @Valid @RequestBody ChangeTaksStatusRequestDTO request){
        this.changeTaskStateUseCase.changeState(taskId, request.newStatus());
        return ResponseEntity.ok().build();
    }

}
