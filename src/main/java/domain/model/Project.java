package domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
public class Project {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status;
    private String description;
    private final List<Long> taskIds; // almacenar ids de tareas (desacopla persistencia) | ***Esto habria que meditarlo***

    private Project(Long id, String name, LocalDate startDate, LocalDate endDate, ProjectStatus status, String description, List<Long> taskIds){
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.description = description;
        this.taskIds = new ArrayList<>(taskIds);
    }

    public static Project createInstance(Long id, String name, LocalDate startDate, LocalDate endDate, ProjectStatus status, String description){
        Objects.requireNonNull(name, "name es requerido");
        Objects.requireNonNull(startDate, "startDate es requerido");
        Objects.requireNonNull(endDate, "endDate es requerido");
        Objects.requireNonNull(status, "status es requerido");

        if (name.isBlank()) throw new IllegalArgumentException("El nombre del proyecto no puede estar vacío"); //Aca habria que hacer las exceptions correspondientes
        if (endDate.isBefore(startDate)) throw new IllegalArgumentException("endDate no puede ser anterior a startDate"); //aca igual obviamente
        return new Project(id, name, startDate, endDate, status, description,  Collections.emptyList());
    }

    // helpers para asociación local
    public Project withAddedTask(Long taskId) {
        List<Long> copy = new ArrayList<>(this.taskIds);
        copy.add(taskId);
        return new Project(this.id, this.name, this.startDate, this.endDate, this.status, this.description, copy);
    }
}
