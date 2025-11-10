package domain.model;


import domain.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {

    private Long id;
    private Project project;
    private String title;
    private Integer estimateHours;
    private String assignee; //Nullable
    private TaskStatus status;
    private LocalDateTime finishedAt; //Nullable
    private LocalDateTime createdAt;

    private Task(Long id, Project project, String title, Integer estimateHours, String assignee, TaskStatus status, LocalDateTime finishedAt, LocalDateTime createdAt) {
        this.id = id;
        this.project = project;
        this.title = title;
        this.estimateHours = estimateHours;
        this.assignee = assignee;
        this.status = status;
        this.finishedAt = finishedAt;
        this.createdAt = createdAt;
    }

    public static Task create(Long id, Project project, String title, Integer estimateHours, String assignee, TaskStatus status) {
        Objects.requireNonNull(project, "projectId es requerido");
        Objects.requireNonNull(title, "Task title es requerido");
        Objects.requireNonNull(estimateHours, "Task estimateHours es requerido");
        Objects.requireNonNull(status, "Task status es requerido");

        if (title.isBlank()) {
            throw new ValidationException("Task title no puede estar vacío");
        }
        if (estimateHours <= 0) {
            throw new ValidationException("Task estimateHours debe ser mayor a cero");
        }

        LocalDateTime finishedAt = null;
        if (status == TaskStatus.DONE) {
            finishedAt = LocalDateTime.now(); // Regla: si se crea como DONE, se fija finishedAt
        }

        return new Task(id, project, title, estimateHours, assignee, status, finishedAt, LocalDateTime.now());
    }

    // Getters...
    public Long getId() { return id; }
    public Project getProjectId() { return project; }
    public String getTitle() { return title; }
    public int getEstimateHours() { return estimateHours; }
    public TaskStatus getStatus() { return status; }
    public LocalDateTime getFinishedAt() { return finishedAt; }
    public String getAssignee() { return assignee; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Método para transicionar el estado de la tarea
    public void changeStatus(TaskStatus newStatus) {
        if (this.status == newStatus) return;

        // Lógica de transición más simple: cualquier estado a cualquier otro
        this.status = newStatus;
        if (newStatus == TaskStatus.DONE) {
            this.finishedAt = LocalDateTime.now(); // Regla: al cambiar a DONE, se fija finishedAt
        } else {
            this.finishedAt = null;
        }
    }
}