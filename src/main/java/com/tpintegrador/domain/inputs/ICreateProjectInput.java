package com.tpintegrador.domain.inputs;
import com.tpintegrador.domain.model.ProjectStatus;

import java.time.LocalDate;

public interface ICreateProjectInput {
    Long createProject(String name, LocalDate startDate, LocalDate endDate);
}