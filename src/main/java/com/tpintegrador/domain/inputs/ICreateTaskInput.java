package com.tpintegrador.domain.inputs;

import com.tpintegrador.domain.model.TaskStatus;

public interface ICreateTaskInput {
    Long createTask(Long id, String title, Integer estimateHours, String assignee, TaskStatus status);
}
