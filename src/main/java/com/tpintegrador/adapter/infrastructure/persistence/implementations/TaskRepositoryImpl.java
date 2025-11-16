package com.tpintegrador.adapter.infrastructure.persistence.implementations;

import com.tpintegrador.domain.model.Task;
import com.tpintegrador.domain.output.ITaskRepository;
import com.tpintegrador.adapter.infrastructure.persistence.jpaRepository.IProjectJpaRepository;
import com.tpintegrador.adapter.infrastructure.persistence.jpaRepository.ITaskJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskRepositoryImpl implements ITaskRepository {

    @Autowired
    private ITaskJpaRepository taskJpaRepository;
    @Autowired
    private IProjectJpaRepository projectJpaRepository;

    public TaskRepositoryImpl(ITaskJpaRepository taskJpaRepository, IProjectJpaRepository projectJpaRepository) {
        this.projectJpaRepository = projectJpaRepository;
        this.taskJpaRepository = taskJpaRepository;
    }

    @Override
    public Task create(Task task) {
        return null;
    }

    @Override
    public Task save(Task task) {
        return null;
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Task> findByProjectId(Long projectId) {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void updateTask(Task task) {

    }
}
