package com.tpintegrador.domain.usecase;

import com.tpintegrador.domain.exception.BusinessRuleViolationException;
import com.tpintegrador.domain.exception.ResourceNotFoundException;
import com.tpintegrador.domain.inputs.ICreateTaskInput;
import com.tpintegrador.domain.model.Project;
import com.tpintegrador.domain.model.ProjectStatus;
import com.tpintegrador.domain.model.Task;
import com.tpintegrador.domain.model.TaskStatus;
import com.tpintegrador.domain.output.IProjectRepository;
import com.tpintegrador.domain.output.ITaskRepository;

public class CreateTaskUseCase implements ICreateTaskInput {
    private final IProjectRepository projectRepository;
    private final ITaskRepository taskRepository;

    public CreateTaskUseCase(IProjectRepository projectRepository, ITaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Long createTask(Long projectId, String title, Integer estimateHours, String assignee, TaskStatus status) {
        // 1. Verificar si el proyecto existe
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado con ID: " + projectId));

        // 2. Regla: Cannot add a Task to a Project with status CLOSED.
        if (project.getStatus() == ProjectStatus.CLOSED) {
            throw new BusinessRuleViolationException("No se puede agregar tareas a un proyecto CLOSED.");
        }

        // 3. Crear la tarea (validaciones de campos en Task.create())
        // ID nulo al crear, el repositorio lo asignará. finishedAt se fija en Task.create() si status es DONE.
        Task task = Task.create(null, project, title, estimateHours, assignee, status);

        // 4. Guardar la tarea
        return taskRepository.save(task).getId();
    }

}
