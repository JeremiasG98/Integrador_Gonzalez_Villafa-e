package domain.usecase;

import domain.model.Project;
import domain.model.Task;
import domain.model.TaskStatus;
import domain.output.IProjectRepository;
import domain.output.ITaskRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class AddTaskToProjectUseCase {

    private final IProjectRepository projectRepository;
    private final ITaskRepository taskRepository;

    public AddTaskToProjectUseCase(IProjectRepository projectRepository, ITaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public Task addTask(Long projectId, String title, Integer estimateHours, String assignee) {
        Optional<Project> opt = projectRepository.findById(projectId);
        if (opt.isEmpty()) throw new IllegalArgumentException("Project no existe: " + projectId);

        Task t = Task.create(null, projectId, title, estimateHours, assignee, TaskStatus.TODO, LocalDateTime.now());
        Task saved = taskRepository.save(t);

        // actualizar proyecto con la tarea (inmutable pattern)
        Project project = opt.get().withAddedTask(saved.getId());
        projectRepository.save(project);

        return saved;
    }
}
