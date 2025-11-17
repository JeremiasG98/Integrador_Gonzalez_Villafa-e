package com.tpintegrador.domain.usecase;

import com.tpintegrador.domain.exception.DuplicateResourceException;
import com.tpintegrador.domain.inputs.ICreateProjectInput;
import com.tpintegrador.domain.model.Project;
import com.tpintegrador.domain.model.ProjectStatus;
import com.tpintegrador.domain.output.IProjectRepository;
import java.time.LocalDate;

public class CreateProjectUseCase implements ICreateProjectInput {

    private final IProjectRepository projectRepository;

    public CreateProjectUseCase(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Long createProject(String name, LocalDate startDate, LocalDate endDate) {
        // Validación de campos requeridos y reglas de fecha se manejan en Project.create()

        // Regla: Project.name must be unique.
        if (projectRepository.existsByName(name)) {
            throw new DuplicateResourceException("Ya existe un proyecto con el nombre: " + name);
        }

        // ID nulo al crear, el repositorio lo asignara
        Project project = Project.create(null, name, startDate, endDate);

        return projectRepository.save(project).getId();
    }
}
