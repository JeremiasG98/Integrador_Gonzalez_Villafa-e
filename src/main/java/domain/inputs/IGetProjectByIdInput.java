package domain.inputs;

import domain.model.Project;

public interface IGetProjectByIdInput {
    Project getProjectById(Long id);
}