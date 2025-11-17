package com.tpintegrador.adapter.infrastructure.persistence.beansConfiguration;

import com.tpintegrador.adapter.infrastructure.persistence.implementations.ProjectRepositoryImpl;
import com.tpintegrador.domain.inputs.*;
import com.tpintegrador.domain.output.IProjectRepository;
import com.tpintegrador.domain.output.ITaskRepository;
import com.tpintegrador.domain.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    //Casos de uso de Project
    @Bean
    public ICreateProjectInput CreateProjectUseCase(IProjectRepository projectRepository){
        return new CreateProjectUseCase(projectRepository);
    }
    public IGetProjectByIdInput GetProjectByIdUseCase(IProjectRepository projectRepository){
        return new GetProjectByIdUseCase(projectRepository);
    }
    public IChangeProjectStatusInput ChangeProjectStatusUseCase(IProjectRepository projectRepository){
        return new ChangeProjectStatusUseCase(projectRepository);
    }
    //Casos de uso de Task
    @Bean
    public ICreateTaskInput CreateTaskUseCase(IProjectRepository projectRepository,ITaskRepository taskRepository){
        return new CreateTaskUseCase(projectRepository,taskRepository);
    }
    @Bean
    public IGetTaskByIdInput GetTaskByIdUseCase(ITaskRepository taskRepository){
        return new GetTaskByIdUseCase(taskRepository);
    }
    @Bean
    public IChangeTaskStateInput ChangeTaskStatusUseCase(ITaskRepository taskRepository) {
        return new ChangeTaskStateUseCase(taskRepository);
    }
}
