package com.tpintegrador.domain.output;

import com.tpintegrador.domain.model.Project;


import java.util.Optional;

public interface IProjectRepository {
    Project create(Project project);
    Project save(Project project);               // create or update
    Optional<Project> findById(Long id);
    boolean existsByName(String name);
    void deleteById(Long id);

    void updateProject(Project project);
}
