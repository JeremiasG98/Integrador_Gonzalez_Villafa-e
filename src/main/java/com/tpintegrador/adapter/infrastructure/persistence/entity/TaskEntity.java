package com.tpintegrador.adapter.infrastructure.persistence.entity;

import com.tpintegrador.domain.model.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
@Entity
@Table(name="tasks")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "estimatehours",nullable = false)
    private int estimateHours;

    @Column(name = "assignee")
    private String assignee;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private TaskStatus status;

    @Column(name = "finishedat")
    private LocalDateTime finishedAt;

    @Column(name = "createdat",nullable = false)
    private LocalDateTime createdAt;

    //Mchas tareas tienen un solo proyecto
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    public TaskEntity(){};
}
