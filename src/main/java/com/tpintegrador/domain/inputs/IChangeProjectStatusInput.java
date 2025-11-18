package com.tpintegrador.domain.inputs;

import com.tpintegrador.domain.model.ProjectStatus;

public interface IChangeProjectStatusInput {
    void changeStatus(Long projectId, ProjectStatus newStatus);
}