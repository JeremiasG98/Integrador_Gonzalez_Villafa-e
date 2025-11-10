package domain.usecase;

import domain.exception.DuplicateResourceException;
import domain.inputs.ICreateProjectInput;
import domain.model.Project;
import domain.model.ProjectStatus;
import domain.output.IProjectRepository;
import java.time.LocalDate;

public class CreateProjectUseCase implements ICreateProjectInput {

    private final IProjectRepository projectRepository;

    public CreateProjectUseCase(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(Long id, String name, LocalDate start, LocalDate end) {
        Project p = Project.create(id, name, start, end);
        return projectRepository.save(p);
    }

    @Override
    public Long createProject(Long id, String name, LocalDate startDate, LocalDate endDate, ProjectStatus status) {
        // Validación de campos requeridos y reglas de fecha se manejan en Project.create()

        // Regla: Project.name must be unique.
        if (projectRepository.existsByName(name)) {
            throw new DuplicateResourceException("Ya existe un proyecto con el nombre: " + name);
        }

        // ID nulo al crear, el repositorio lo asignará.
        Project project = Project.create(null, name, startDate, endDate);

        return projectRepository.save(project).getId();
    }
}
