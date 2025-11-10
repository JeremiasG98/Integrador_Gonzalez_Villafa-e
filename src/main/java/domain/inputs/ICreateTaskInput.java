package domain.inputs;

import domain.model.Project;
import domain.model.TaskStatus;

public interface ICreateTaskInput {
    Long createTask(Long id, String title, Integer estimateHours, String assignee, TaskStatus status);
}
