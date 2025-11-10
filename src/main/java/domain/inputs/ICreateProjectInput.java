package domain.inputs;
import domain.model.ProjectStatus;

import java.time.LocalDate;

public interface ICreateProjectInput {
    Long createProject(Long id, String name, LocalDate startDate, LocalDate endDate, ProjectStatus status);
}