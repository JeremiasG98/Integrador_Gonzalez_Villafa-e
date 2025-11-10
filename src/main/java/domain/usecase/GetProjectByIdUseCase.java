package domain.usecase;

import domain.exception.ResourceNotFoundException;
import domain.inputs.IGetProjectByIdInput;
import domain.model.Project;
import domain.output.IProjectRepository;

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
