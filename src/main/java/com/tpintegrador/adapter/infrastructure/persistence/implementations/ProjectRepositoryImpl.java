package com.tpintegrador.adapter.infrastructure.persistence.implementations;

import com.tpintegrador.domain.model.Project;
import com.tpintegrador.domain.output.IProjectRepository;
import com.tpintegrador.adapter.infrastructure.persistence.entity.ProjectEntity;
import com.tpintegrador.adapter.infrastructure.persistence.jpaRepository.IProjectJpaRepository;
import com.tpintegrador.adapter.infrastructure.persistence.mapper.PersistenceMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ProjectRepositoryImpl implements IProjectRepository {

    private IProjectJpaRepository jpaRepository;
    private PersistenceMapper mapper;


    @Override
    public Project create(Project project) {
        ProjectEntity projectBD = new ProjectEntity();
        projectBD.setName(project.getName());
        projectBD.setStartDate(project.getStartDate());
        projectBD.setEndDate(project.getEndDate());
        projectBD.setStatus(project.getStatus());
        jpaRepository.save(projectBD);
        return project;
    }
    public ProjectRepositoryImpl(IProjectJpaRepository jpaRepository, PersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Project save(Project project) {
        return null;
    }

    @Override
    public Optional<Project> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void updateProject(Project project) {

    }
}
