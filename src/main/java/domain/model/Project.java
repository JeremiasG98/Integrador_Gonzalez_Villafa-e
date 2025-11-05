package domain.model;

import domain.exception.FieldNotNullException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class Project {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status;
    private String description;

    private Project(Long id, String name, LocalDate startDate, LocalDate endDate, ProjectStatus status, String description){

        if(name == null || name.isBlank()){
            throw new FieldNotNullException("Name cannot be null or empty");
        }
        if(startDate == null){
            throw new FieldNotNullException("Start date cannot be null");
        }
        if(endDate == null){
            throw new FieldNotNullException("End date cannot be null");
        }
        if(status == null){
            throw new FieldNotNullException("Status cannot be null");
        }


        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.description = description;
    }



    public static Project createInstance(Long id, String name, LocalDate startDate, LocalDate endDate, ProjectStatus status, String description){
        return new Project(id, name, startDate, endDate, status, description);
    }
}
