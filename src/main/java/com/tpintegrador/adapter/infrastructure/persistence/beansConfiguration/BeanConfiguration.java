package com.tpintegrador.adapter.infrastructure.persistence.beansConfiguration;

import com.tpintegrador.adapter.api.mapper.ApiMapper;
import com.tpintegrador.adapter.infrastructure.persistence.implementations.ProjectRepositoryImpl;
import com.tpintegrador.adapter.infrastructure.persistence.implementations.TaskRepositoryImpl;
import com.tpintegrador.adapter.infrastructure.persistence.jpaRepository.IProjectJpaRepository;
import com.tpintegrador.adapter.infrastructure.persistence.jpaRepository.ITaskJpaRepository;
import com.tpintegrador.adapter.infrastructure.persistence.mapper.PersistenceMapper;
import com.tpintegrador.domain.output.IProjectRepository;
import com.tpintegrador.domain.output.ITaskRepository;
import com.tpintegrador.domain.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PersistenceMapper persistenceMapper() {
        return new PersistenceMapper();
    }

    @Bean
    public ApiMapper apiMapper() {
        return new ApiMapper();
    }

    @Bean
    public IProjectRepository projectRepository(IProjectJpaRepository jpaRepository, PersistenceMapper mapper) {
        return new ProjectRepositoryImpl(jpaRepository, mapper);
    }

    @Bean
    public ITaskRepository taskRepository(ITaskJpaRepository taskJpaRepository, IProjectJpaRepository projectJpaRepository, PersistenceMapper mapper) {
        return new TaskRepositoryImpl(taskJpaRepository, projectJpaRepository, mapper);
    }

    @Bean
    public CreateProjectUseCase createProjectUseCase(IProjectRepository projectRepository) {
        return new CreateProjectUseCase(projectRepository);
    }

    @Bean
    public GetProjectByIdUseCase getProjectByIdUseCase(IProjectRepository projectRepository) {
        return new GetProjectByIdUseCase(projectRepository);
    }

    @Bean
    public ChangeProjectStatusUseCase changeProjectStatusUseCase(IProjectRepository projectRepository) {
        return new ChangeProjectStatusUseCase(projectRepository);
    }

    @Bean
    public CreateTaskUseCase createTaskUseCase(IProjectRepository projectRepository, ITaskRepository taskRepository) {
        return new CreateTaskUseCase(projectRepository, taskRepository);
    }

    @Bean
    public GetTaskByIdUseCase getTaskByIdUseCase(ITaskRepository taskRepository) {
        return new GetTaskByIdUseCase(taskRepository);
    }

    @Bean
    public ChangeTaskStateUseCase changeTaskStateUseCase(ITaskRepository taskRepository) {
        return new ChangeTaskStateUseCase(taskRepository);
    }
}
