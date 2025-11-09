package domain.output;

import domain.model.Project;

import java.time.LocalDate;
import java.util.Optional;

public interface IProjectRepository {
    Project create(Project project);
    Project save(Project project);               // create or update
    Optional<Project> findById(Long id);
    void deleteById(Long id);
}
