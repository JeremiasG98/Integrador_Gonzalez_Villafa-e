package com.tpintegrador.adapter.api.mapper;

import com.tpintegrador.adapter.api.dto.CreateProjectRequestDTO;
import com.tpintegrador.adapter.api.dto.ProjectResponseDTO;
import com.tpintegrador.adapter.api.dto.TaskResponseDTO;
import com.tpintegrador.domain.model.Project;
import com.tpintegrador.domain.model.Task;

public class ApiMapper {



    //Convertir un Project (dominio) a ProjectResponseDTO
    public ProjectResponseDTO toProjectResponseDTO(Project project){
        return new ProjectResponseDTO(
                project.getId(),
                project.getName(),
                project.getStartDate(),
                project.getEndDate(),
                project.getStatus()
        );
    }

    //Convertir un Task (dominio) a TaskResponseDTO
    public TaskResponseDTO toTaskResponseDTO(Task task){
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getEstimateHours(),
                task.getAssignee(),
                task.getStatus(),
                task.getProjectId().getId()
        );
    }


}
