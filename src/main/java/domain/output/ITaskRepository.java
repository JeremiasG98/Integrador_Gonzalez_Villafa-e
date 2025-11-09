package domain.output;

import domain.model.Task;
import java.util.List;
import java.util.Optional;

public interface ITaskRepository {
    Task create(Task task);
    Task save(Task task);
    Optional<Task> findById(Long id);
    List<Task> findByProjectId(Long projectId);
    void deleteById(Long id);
}
