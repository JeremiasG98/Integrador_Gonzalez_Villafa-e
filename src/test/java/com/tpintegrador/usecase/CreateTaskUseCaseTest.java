package com.tpintegrador.usecase;

import domain.exception.BusinessRuleViolationException;
import domain.exception.ResourceNotFoundException;
import domain.model.Project;
import domain.model.ProjectStatus;
import domain.model.Task;
import domain.model.TaskStatus;
import domain.output.IProjectRepository;
import domain.output.ITaskRepository;
import domain.usecase.CreateTaskUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateTaskUseCaseTest {

    @Mock
    private IProjectRepository projectRepository;

    @Mock
    private ITaskRepository taskRepository;

    @InjectMocks
    private CreateTaskUseCase createTaskUseCase;

    private Project activeProject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        activeProject = Project.create(1L, "Proyecto Activo", LocalDate.now(), LocalDate.now().plusDays(10));
    }

    @Test
    void shouldCreateTaskSuccessfully() {
        // Arrange
        when(projectRepository.findById(1L)).thenReturn(Optional.of(activeProject));

        Task savedTask = Task.create(100L, activeProject, "Nueva Tarea", 8, null, TaskStatus.TODO);
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        // Act
        Long taskId = createTaskUseCase.createTask(1L, "Nueva Tarea", 8, null, TaskStatus.TODO);

        // Assert
        assertNotNull(taskId);
        assertEquals(100L, taskId);
        verify(projectRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void shouldThrowResourceNotFoundException_whenProjectDoesNotExist() {
        // Arrange
        when(projectRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            createTaskUseCase.createTask(99L, "Tarea Fallida", 5, null, TaskStatus.TODO);
        });

        verify(projectRepository, times(1)).findById(99L);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void shouldThrowBusinessRuleViolationException_whenProjectIsClosed() {
        // Arrange
        Project closedProject = Project.create(2L, "Proyecto Cerrado", LocalDate.now(), LocalDate.now().plusDays(5));

        // Simular flujo
        closedProject.changeStatus(ProjectStatus.ACTIVE);
        closedProject.changeStatus(ProjectStatus.CLOSED);

        when(projectRepository.findById(2L)).thenReturn(Optional.of(closedProject));

        // Act & Assert
        assertThrows(BusinessRuleViolationException.class, () -> {
            createTaskUseCase.createTask(2L, "Tarea Ilegal", 10, null, TaskStatus.TODO);
        });

        verify(projectRepository, times(1)).findById(2L);
        verify(taskRepository, never()).save(any(Task.class));
    }
}
