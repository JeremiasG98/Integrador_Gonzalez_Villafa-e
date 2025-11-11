package com.tpintegrador.usecase;

import domain.exception.ResourceNotFoundException;
import domain.model.Project;
import domain.output.IProjectRepository;
import domain.usecase.GetProjectByIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetProjectByIdUseCaseTest {

    @Mock
    private IProjectRepository projectRepository;

    @InjectMocks
    private GetProjectByIdUseCase getProjectByIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnProject_whenFound() {
        // Arrange
        Project project = Project.create(1L, "Proyecto Encontrado", LocalDate.now(), LocalDate.now().plusDays(1));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        // Act
        Project foundProject = getProjectByIdUseCase.getProjectById(1L);

        // Assert
        assertNotNull(foundProject);
        assertEquals(1L, foundProject.getId());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowResourceNotFoundException_whenNotFound() {
        // Arrange
        when(projectRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            getProjectByIdUseCase.getProjectById(99L);
        });

        verify(projectRepository, times(1)).findById(99L);
    }
}
