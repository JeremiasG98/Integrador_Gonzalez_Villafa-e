package com.tpintegrador.domain.usecase;

import com.tpintegrador.domain.exception.ResourceNotFoundException;
import com.tpintegrador.domain.inputs.IGetTaskByIdInput;
import com.tpintegrador.domain.model.Task;
import com.tpintegrador.domain.output.ITaskRepository;

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
