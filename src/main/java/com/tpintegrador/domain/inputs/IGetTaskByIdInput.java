package com.tpintegrador.domain.inputs;

import com.tpintegrador.domain.model.Task;

public interface IGetTaskByIdInput {
    Task getTaskById(Long id);
}