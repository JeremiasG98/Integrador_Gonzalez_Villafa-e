package com.tpintegrador.domain.inputs;

import com.tpintegrador.domain.model.TaskStatus;

public interface IChangeTaskStateInput {
    void changeState(Long taskId, TaskStatus newStatus);
}

