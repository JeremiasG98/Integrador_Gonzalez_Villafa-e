package infrastructure.persistence.mapper;

import domain.model.Project;
import domain.model.Task;
import infrastructure.persistence.entity.ProjectEntity;
import infrastructure.persistence.entity.TaskEntity;

public class PersistenceMapper {

    public ProjectEntity toEntity(Project project){
        if (project == null){
            return null;
        }
        ProjectEntity entity = new ProjectEntity();
        entity.setId(project.getId());
        entity.setName(project.getName());
        entity.setStartDate(project.getStartDate());
        entity.setEndDate(project.getEndDate());
        entity.setStatus(project.getStatus());

        return entity;
    }

    public Project toDomain(ProjectEntity entity){
        //Aca volvemos a crear "Project" desde "Entity"
        Project project = Project.create(
                entity.getId(),
                entity.getName(),
                entity.getStartDate(),
                entity.getEndDate()
        );
        //Y fuerzo el estado guardado
        project.changeStatus(entity.getStatus());

        return project;
    }

    //Ahora para las tareas
    public TaskEntity toEntity(Task task, ProjectEntity projectEntity){
        if(task == null){
            return null;
        }
        TaskEntity entity = new TaskEntity();
        entity.setId(task.getId());
        entity.setTitle(task.getTitle());
        entity.setEstimateHours(task.getEstimateHours());
        entity.setAssignee(task.getAssignee());
        entity.setStatus(task.getStatus());
        entity.setFinishedAt(task.getFinishedAt());
        entity.setCreatedAt(task.getCreatedAt());
        entity.setProject(projectEntity); //aca se asigna el proyecto

        return entity;
    }

    public Task toDomain(TaskEntity entity){
        if(entity == null){
            return null;
        }
        Project project = toDomain(entity.getProject());
        Task task = Task.create( //aca hay algo raro mono...
                entity.getId(),
                project,
                entity.getTitle(),
                entity.getEstimateHours(),
                entity.getAssignee(),
                entity.getStatus()
        );
        return task;
    }

}
