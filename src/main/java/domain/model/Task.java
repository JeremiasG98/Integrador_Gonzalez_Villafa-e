package domain.model;

import domain.exception.FieldNotNullException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class Task {
    private Long id;
    private Project project;
    private String title;
    private Integer estimatedHours;
    private String assignee;
    private TaskStatus status;
    private LocalDateTime finishedAt;
    private LocalDateTime createdAt;


    private Task(Long id, Project project, String title, Integer estimatedHours, String assignee, TaskStatus status, LocalDateTime finishedAt, LocalDateTime createdAt) {
        if(project == null){
            throw new FieldNotNullException("Project cannot be null");
        }
        if(title == null || title.isBlank()){
            throw new FieldNotNullException("Title cannot be null or empty");
        }
        if(estimatedHours == null){
            throw new FieldNotNullException("Estimated hours cannot be null");
        }
        if(status == null){
            throw new FieldNotNullException("Status cannot be null");
        }
        if(createdAt == null) {
            throw new FieldNotNullException("Created at cannot be null");
        }

        this.id = id;
        this.project = project;
        this.title = title;
        this.estimatedHours = estimatedHours;
        this.assignee = assignee;
        this.status = status;
        this.finishedAt = finishedAt;
        this.createdAt = createdAt;
    }

    public static Task createInstance(Long id, Project project, String title, Integer estimatedHours, String assignee, TaskStatus status, LocalDateTime finishedAt, LocalDateTime createdAt){
        return new Task(id, project, title, estimatedHours, assignee, status, finishedAt, createdAt);
    }



}
