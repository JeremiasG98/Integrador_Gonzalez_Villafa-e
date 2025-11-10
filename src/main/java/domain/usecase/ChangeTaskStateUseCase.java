package domain.usecase;

import domain.exception.ResourceNotFoundException;
import domain.inputs.IChangeTaskStateInput;
import domain.model.Task;
import domain.model.TaskStatus;
import domain.output.ITaskRepository;

public class ChangeTaskStateUseCase implements IChangeTaskStateInput {
    private final ITaskRepository taskRepository;

    public ChangeTaskStateUseCase(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void changeState(Long taskId, TaskStatus newStatus) {
        // 1. Obtener la tarea
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con ID: " + taskId));

        // 2. Cambiar el estado (finishedAt se actualiza dentro del modelo Task si es DONE)
        task.changeStatus(newStatus);

        // 3. Persistir el cambio
        taskRepository.updateTask(task);
    }
}