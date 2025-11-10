package domain.inputs;

import domain.model.Task;

public interface IGetTaskByIdInput {
    Task getTaskById(Long id);
}