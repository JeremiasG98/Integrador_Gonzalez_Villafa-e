package com.tpintegrador.adapter.infrastructure.persistence.implementations;

import com.tpintegrador.adapter.infrastructure.persistence.entity.ProjectEntity;
import com.tpintegrador.adapter.infrastructure.persistence.entity.TaskEntity;
import com.tpintegrador.adapter.infrastructure.persistence.mapper.PersistenceMapper;
import com.tpintegrador.domain.exception.ResourceNotFoundException;
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
    private IProjectJpaRepository projectJpaRepository;
    private PersistenceMapper mapper;

    public TaskRepositoryImpl(ITaskJpaRepository taskJpaRepository, IProjectJpaRepository projectJpaRepository, PersistenceMapper mapper) {
        this.taskJpaRepository = taskJpaRepository;
        this.projectJpaRepository = projectJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Task create(Task task) {
        // Deberia devolver el objeto Task sin persistirlo
        return task;
    }

    @Override
    public Task save(Task task) {
        //Obtener la entidad Project a la que pertenece esta tarea
        ProjectEntity projectEntity = projectJpaRepository.findById(task.getProjectId().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado para la tarea: " + task.getProjectId()));

        //Mapear la Tarea de dominio a entidad, asignando el proyecto
        TaskEntity entity = mapper.toEntity(task, projectEntity);

        //Guardar
        TaskEntity savedEntity = taskJpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskJpaRepository.findById(id).map(taskEntity -> mapper.toDomain(taskEntity));
    }

    @Override
    public List<Task> findByProjectId(Long projectId) {
        return taskJpaRepository.findByProjectId(projectId).stream().map(taskEntity -> mapper.toDomain((TaskEntity) taskEntity)).toList();
    }

    @Override
    public void deleteById(Long id) {
        taskJpaRepository.deleteById(id);
    }

    @Override
    public void updateTask(Task task) {
        //Obtener la entidad Task a actualizar
        TaskEntity taskEntity = taskJpaRepository.findById(task.getId())
                .orElseThrow(()-> new ResourceNotFoundException("No se puede actualizar. Tarea no encontrada: " + task.getId()));

        //Actualizas los campos
        taskEntity.setTitle(task.getTitle());
        taskEntity.setEstimateHours(task.getEstimateHours());
        taskEntity.setAssignee(task.getAssignee());
        taskEntity.setStatus(task.getStatus());
        taskEntity.setFinishedAt(task.getFinishedAt());

        //Finalmente guardamos
        taskJpaRepository.save(taskEntity);
    }
}
