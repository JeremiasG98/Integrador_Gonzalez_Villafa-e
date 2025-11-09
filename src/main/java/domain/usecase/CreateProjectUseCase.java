package domain.usecase;

import domain.model.Project;
import domain.model.ProjectStatus;
import domain.output.IProjectRepository;
import java.time.LocalDate;

public class CreateProjectUseCase {

    private final IProjectRepository projectRepository;

    public CreateProjectUseCase(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(Long id, String name, LocalDate start, LocalDate end, String description) {
        Project p = Project.create(id, name, start, end, ProjectStatus.PLANNED, description);
        return projectRepository.save(p);
    }
}
