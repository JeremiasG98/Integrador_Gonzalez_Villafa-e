package com.tpintegrador.domain.usecase;

import com.tpintegrador.domain.exception.ResourceNotFoundException;
import com.tpintegrador.domain.inputs.IGetProjectByIdInput;
import com.tpintegrador.domain.model.Project;
import com.tpintegrador.domain.output.IProjectRepository;

public class GetProjectByIdUseCase implements IGetProjectByIdInput {
    private final IProjectRepository projectRepository;

    public GetProjectByIdUseCase(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado con ID: " + id));
    }
}
