package com.tpintegrador.adapter.api.controller;


import com.tpintegrador.adapter.api.dto.ChangeProjectStatusRequestDTO;
import com.tpintegrador.adapter.api.dto.CreateProjectRequestDTO;
import com.tpintegrador.adapter.api.dto.ProjectResponseDTO;
import com.tpintegrador.adapter.api.mapper.ApiMapper;
import com.tpintegrador.domain.model.Project;
import com.tpintegrador.domain.usecase.ChangeProjectStatusUseCase;
import com.tpintegrador.domain.usecase.CreateProjectUseCase;
import com.tpintegrador.domain.usecase.GetProjectByIdUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final ApiMapper apiMapper;
    private final GetProjectByIdUseCase getProjectByIdUseCase;
    private final ChangeProjectStatusUseCase changeProjectStatusUseCase;

    public ProjectController(
            CreateProjectUseCase createProjectUseCase,
            ApiMapper apiMapper,
            GetProjectByIdUseCase getProjectByIdUseCase,
            ChangeProjectStatusUseCase changeProjectStatusUseCase
    ) {
        this.createProjectUseCase = createProjectUseCase;
        this.apiMapper = apiMapper;
        this.getProjectByIdUseCase = getProjectByIdUseCase;
        this.changeProjectStatusUseCase = changeProjectStatusUseCase;
    }

    // Endpoint para crear un nuevo proyecto
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@Valid @RequestBody CreateProjectRequestDTO request) {
        Long projectId = createProjectUseCase.createProject(request.name(), request.startDate(), request.endDate());
        Project project = getProjectByIdUseCase.getProjectById(projectId);
        ProjectResponseDTO response = apiMapper.toProjectResponseDTO(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Endpoint para buscar un proyecto por id
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long projectId){
        Project project = getProjectByIdUseCase.getProjectById(projectId);
        ProjectResponseDTO response = apiMapper.toProjectResponseDTO(project);
        return ResponseEntity.ok(response);
    }

    // Endpoint para cambiar el estado de un proyecto
    @PostMapping("/{projectId}/status")
    public ResponseEntity<Void> changeProjectStatus(@PathVariable Long projectId, @Valid @RequestBody ChangeProjectStatusRequestDTO request) {
        changeProjectStatusUseCase.changeStatus(projectId, request.newStatus());
        return ResponseEntity.ok().build();
    }
}
