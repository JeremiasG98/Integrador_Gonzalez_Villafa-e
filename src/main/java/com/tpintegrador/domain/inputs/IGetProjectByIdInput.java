package com.tpintegrador.domain.inputs;

import com.tpintegrador.domain.model.Project;

public interface IGetProjectByIdInput {
    Project getProjectById(Long id);
}