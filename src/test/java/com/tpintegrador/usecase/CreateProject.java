package com.tpintegrador.usecase;


import domain.output.IProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateProject {

    @Mock
    private IProjectRepository projectRepository;

    @Test
    void createProject_projectNonExistent_success(){
        //Arrange
        //Instanciar Project
        //Crear Proyecto Use Case

    }


}
