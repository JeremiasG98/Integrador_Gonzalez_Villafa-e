package com.tpintegrador.domain.usecase;

import com.tpintegrador.domain.exception.ResourceNotFoundException;
import com.tpintegrador.domain.inputs.IChangeProjectStatusInput;
import com.tpintegrador.domain.model.Project;
import com.tpintegrador.domain.model.ProjectStatus;
import com.tpintegrador.domain.output.IProjectRepository;

public class ChangeProjectStatusUseCase implements IChangeProjectStatusInput {
    private final IProjectRepository projectRepository;

    public ChangeProjectStatusUseCase(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void changeStatus(Long projectId, ProjectStatus newStatus) {
        // 1. Obtener el proyecto
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado con ID: " + projectId));

        // 2. Cambiar el estado (las reglas de transición se aplican dentro del modelo Project)
        project.changeStatus(newStatus);

        // 3. Persistir el cambio
        projectRepository.updateProject(project);
    }
}