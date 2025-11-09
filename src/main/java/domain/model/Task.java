package domain.model;

import domain.exception.FieldNotNullException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Task {
    private Long id;
    private Project project; // referencia al proyecto
    private String title;
    private Integer estimateHours;
    private String assignee;  // nullable
    private TaskStatus status;
    private LocalDateTime finishedAt; // nullable
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

    public static Task createInstance(Long id, Project project, String title, Integer estimateHours, String assignee, TaskStatus status, LocalDateTime finishedAt, LocalDateTime createdAt){
        Objects.requireNonNull(project, "projectId es requerido");
        Objects.requireNonNull(title, "title es requerido");
        Objects.requireNonNull(estimateHours, "estimateHours es requerido");
        Objects.requireNonNull(status, "status es requerido");
        Objects.requireNonNull(createdAt, "createdAt es requerido");

        if (title.isBlank()) throw new IllegalArgumentException("title no puede estar vacío");
        if (estimateHours < 0) throw new IllegalArgumentException("estimateHours no puede ser negativo");
        return new Task(id, project, title, estimateHours, assignee, status, finishedAt, createdAt);
    }

    // Cuando se marca como DONE
    public Task markDone(LocalDateTime finishedAt) {
        if (finishedAt == null) throw new IllegalArgumentException("finishedAt es requerido");
        return new Task(this.id, this.project, this.title, this.estimateHours, this.assignee, TaskStatus.DONE, finishedAt, this.createdAt);
    }

    // getters
    public Long getId(){return id; }
    public Project getProjectId() { return project; }
    public String getTitle() { return title; }
    public Integer getEstimateHours() { return estimateHours; }
    public String getAssignee() { return assignee; }
    public TaskStatus getStatus() { return status; }
    public LocalDateTime getFinishedAt() { return finishedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }


}
