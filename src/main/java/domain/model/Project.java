package domain.model;

import domain.exception.BusinessRuleViolationException;
import domain.exception.ValidationException;
import domain.model.ProjectStatus;

import java.time.LocalDate;
import java.util.Objects;

public class Project {

    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status;

    private Project(Long id, String name, LocalDate startDate, LocalDate endDate, ProjectStatus status) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public static Project create(Long id, String name, LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(name, "Project name es requerido");
        Objects.requireNonNull(startDate, "Project startDate es requerida");
        Objects.requireNonNull(endDate, "Project endDate es requerida");

        if (name.isBlank()) {
            throw new ValidationException("Project name no puede estar vacío");
        }
        if (endDate.isBefore(startDate)) {
            throw new ValidationException("Project endDate debe ser mayor o igual a startDate");
        }
        if (endDate.isBefore(LocalDate.now())) {
            throw new ValidationException("Project endDate debe ser mayor o igual a la fecha actual al crearse");
        }
        if (endDate.isBefore(startDate)) {
            throw new EndDateBeforeStartDateException("End date cannot be before start date");
        }

        // Un proyecto nuevo siempre inicia en PLANNED
        return new Project(id, name, startDate, endDate, ProjectStatus.PLANNED);
    }

    // Getters...
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    // Método para transicionar el estado del proyecto
    public void changeStatus(ProjectStatus newStatus) {
        if (this.status == newStatus) return; // No cambia si es el mismo

        switch (this.status) {
            case PLANNED:
                // PLANNED puede ir a ACTIVE o CLOSED
                this.status = newStatus;
                break;
            case ACTIVE:
                // ACTIVE puede ir a CLOSED
                if (newStatus == ProjectStatus.PLANNED) {
                    throw new BusinessRuleViolationException("No se puede volver de ACTIVE a PLANNED");
                }
                this.status = newStatus;
                break;
            case CLOSED:
                // CLOSED no puede cambiar
                throw new BusinessRuleViolationException("Un proyecto CLOSED no puede cambiar de estado");
        }
    }
}