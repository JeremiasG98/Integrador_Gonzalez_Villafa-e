package com.tpintegrador.adapter.infrastructure.persistence.entity;

import com.tpintegrador.domain.model.ProjectStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter
@Entity
@Table(name="projects")
@SequenceGenerator(name="project_id_seq",sequenceName = "project_id_seq",initialValue =  1,allocationSize=1)
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false,unique = true)
    private String name;

    @Column(name = "startdate",nullable = false)
    private LocalDate startDate;

    @Column(name = "enddate",nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private ProjectStatus status;

    // Relación: Un proyecto tiene muchas tareas
    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) //Aprender parametros.
    private List<TaskEntity> tasks = new ArrayList<>();

    public ProjectEntity(){};

}
