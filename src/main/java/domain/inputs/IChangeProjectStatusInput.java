package domain.inputs;

import domain.model.ProjectStatus;

public interface IChangeProjectStatusInput {
    void changeStatus(Long projectId, ProjectStatus newStatus);
}