package com.tpintegrador.usecase;

import domain.exception.ResourceNotFoundException;
import domain.model.Project;
import domain.model.Task;
import domain.model.TaskStatus;
import domain.output.ITaskRepository;
import domain.usecase.GetTaskByIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetTaskByIdUseCaseTest {

    @Mock
    private ITaskRepository taskRepository;

    @InjectMocks
    private GetTaskByIdUseCase getTaskByIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnTask_whenFound() {
        // Arrange
        Project project = Project.create(1L, "Proyecto", LocalDate.now(), LocalDate.now().plusDays(1));
        Task task = Task.create(100L, project, "Tarea Encontrada", 8, null, TaskStatus.TODO);
        when(taskRepository.findById(100L)).thenReturn(Optional.of(task));

        // Act
        Task foundTask = getTaskByIdUseCase.getTaskById(100L);

        // Assert
        assertNotNull(foundTask);
        assertEquals(100L, foundTask.getId());
        verify(taskRepository, times(1)).findById(100L);
    }

    @Test
    void shouldThrowResourceNotFoundException_whenNotFound() {
        // Arrange
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            getTaskByIdUseCase.getTaskById(99L);
        });

        verify(taskRepository, times(1)).findById(99L);
    }
}
