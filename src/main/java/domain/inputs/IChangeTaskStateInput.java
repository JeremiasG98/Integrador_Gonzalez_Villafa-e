package domain.inputs;

import domain.model.TaskStatus;

public interface IChangeTaskStateInput {
    void changeState(Long taskId, TaskStatus newStatus);
}

