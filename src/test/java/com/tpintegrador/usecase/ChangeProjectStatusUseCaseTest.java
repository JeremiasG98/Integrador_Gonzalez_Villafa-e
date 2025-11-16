package com.tpintegrador.usecase;

import com.tpintegrador.domain.exception.BusinessRuleViolationException;
import com.tpintegrador.domain.exception.ResourceNotFoundException;
import com.tpintegrador.domain.model.Project;
import com.tpintegrador.domain.model.ProjectStatus;
import com.tpintegrador.domain.output.IProjectRepository;
import com.tpintegrador.domain.usecase.ChangeProjectStatusUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ChangeProjectStatusUseCaseTest {

    @Mock
    private IProjectRepository projectRepository;

    @InjectMocks
    private ChangeProjectStatusUseCase changeProjectStatusUseCase;

    private Project plannedProject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        plannedProject = Project.create(1L, "Proyecto Planeado", LocalDate.now(), LocalDate.now().plusDays(10));
    }

    @Test
    void shouldChangeStatusSuccessfully() {
        // Arrange
        when(projectRepository.findById(1L)).thenReturn(Optional.of(plannedProject));
        doNothing().when(projectRepository).updateProject(any(Project.class));

        // Act
        changeProjectStatusUseCase.changeStatus(1L, ProjectStatus.ACTIVE);

        // Assert
        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).updateProject(any(Project.class));
        assert(plannedProject.getStatus() == ProjectStatus.ACTIVE);
    }

    @Test
    void shouldThrowResourceNotFoundException_whenProjectDoesNotExist() {
        // Arrange
        when(projectRepository.findById(99L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            changeProjectStatusUseCase.changeStatus(99L, ProjectStatus.ACTIVE);
        });

        verify(projectRepository, times(1)).findById(99L);
        verify(projectRepository, never()).updateProject(any(Project.class));
    }

    @Test
    void shouldThrowBusinessRuleViolationException_whenTransitionIsInvalid() {
        // Arrange
        // Un proyecto cerrado no puede cambiar de estado
        Project closedProject = Project.create(2L, "Proyecto Cerrado", LocalDate.now(), LocalDate.now().plusDays(5));
        closedProject.changeStatus(ProjectStatus.ACTIVE);
        closedProject.changeStatus(ProjectStatus.CLOSED);

        when(projectRepository.findById(2L)).thenReturn(Optional.of(closedProject));

        // Act & Assert
        assertThrows(BusinessRuleViolationException.class, () -> {
            changeProjectStatusUseCase.changeStatus(2L, ProjectStatus.ACTIVE);
        });

        verify(projectRepository, times(1)).findById(2L);
        verify(projectRepository, never()).updateProject(any(Project.class));
    }
}
