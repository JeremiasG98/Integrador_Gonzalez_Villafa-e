package domain.usecase;

import domain.exception.ResourceNotFoundException;
import domain.inputs.IGetTaskByIdInput;
import domain.model.Task;
import domain.output.ITaskRepository;

public class GetTaskByIdUseCase implements IGetTaskByIdInput {
    private final ITaskRepository taskRepository;

    public GetTaskByIdUseCase(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con ID: " + id));
    }
}
