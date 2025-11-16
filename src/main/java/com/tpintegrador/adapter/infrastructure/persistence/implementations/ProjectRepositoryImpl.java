package com.tpintegrador.adapter.infrastructure.persistence.implementations;

import com.tpintegrador.domain.exception.ResourceNotFoundException;
import com.tpintegrador.domain.model.Project;
import com.tpintegrador.domain.output.IProjectRepository;
import com.tpintegrador.adapter.infrastructure.persistence.entity.ProjectEntity;
import com.tpintegrador.adapter.infrastructure.persistence.jpaRepository.IProjectJpaRepository;
import com.tpintegrador.adapter.infrastructure.persistence.mapper.PersistenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProjectRepositoryImpl implements IProjectRepository {

    @Autowired
    private IProjectJpaRepository jpaRepository;
    private PersistenceMapper mapper;

    public ProjectRepositoryImpl(IProjectJpaRepository jpaRepository, PersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Project create(Project project) {
        // Deberia devolver el objeto Project sin persistirlo
        return project;
    }

    @Override
    public Project save(Project project) {
        ProjectEntity entity = mapper.toEntity(project);
        ProjectEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Project> findById(Long id) {
        return jpaRepository.findById(id).map(projectEntity -> mapper.toDomain(projectEntity));
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public void deleteById(Long id) {
        if (jpaRepository.existsById(id)) {
            jpaRepository.deleteById(id);
        }
    }

    @Override
    public void updateProject(Project project) {
        //Obtener la entidad existente
        ProjectEntity entity = jpaRepository.findById(project.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar. Proyecto no encontrado: " + project.getId()));

        //Actualizar solo los campos que maneja el dominio
        entity.setName(project.getName());
        entity.setStartDate(project.getStartDate());
        entity.setEndDate(project.getEndDate());
        entity.setStatus(project.getStatus());

        //Guardar (JPA detecta los cambios y actualiza)
        jpaRepository.save(entity);
    }
}
