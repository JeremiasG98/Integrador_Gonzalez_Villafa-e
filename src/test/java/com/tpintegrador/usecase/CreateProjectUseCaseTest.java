package com.tpintegrador.usecase;

import com.tpintegrador.domain.exception.DuplicateResourceException;
import com.tpintegrador.domain.model.Project;
import com.tpintegrador.domain.output.IProjectRepository;
import com.tpintegrador.domain.usecase.CreateProjectUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateProjectUseCaseTest {

    @Mock
    private IProjectRepository projectRepository;

    @InjectMocks
    private CreateProjectUseCase createProjectUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateProjectSuccessfully() {
        // Arrange
        String projectName = "Nuevo Proyecto";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(10);

        when(projectRepository.existsByName(projectName)).thenReturn(false);

        Project projectToSave = Project.create(null, projectName, startDate, endDate);

        Project savedProject = Project.create(1L, projectName, startDate, endDate);
        when(projectRepository.save(any(Project.class))).thenReturn(savedProject);

        // Act
        Long projectId = createProjectUseCase.createProject(projectName, startDate, endDate);

        // Assert
        assertNotNull(projectId);
        assertEquals(1L, projectId);

        verify(projectRepository, times(1)).existsByName(projectName);
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void shouldThrowDuplicateResourceException_whenProjectNameExists() {
        // Arrange
        String projectName = "Proyecto Existente";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(10);


        when(projectRepository.existsByName(projectName)).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> {
            createProjectUseCase.createProject(projectName, startDate, endDate);
        });

        verify(projectRepository, times(1)).existsByName(projectName);
        verify(projectRepository, never()).save(any(Project.class));
    }
}
