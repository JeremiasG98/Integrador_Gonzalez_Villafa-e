package com.tpintegrador.usecase;

import com.tpintegrador.domain.exception.ResourceNotFoundException;
import com.tpintegrador.domain.model.Project;
import com.tpintegrador.domain.model.Task;
import com.tpintegrador.domain.model.TaskStatus;
import com.tpintegrador.domain.output.ITaskRepository;
import com.tpintegrador.domain.usecase.ChangeTaskStateUseCase;
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

public class ChangeTaskStateUseCaseTest {

    @Mock
    private ITaskRepository taskRepository;

    @InjectMocks
    private ChangeTaskStateUseCase changeTaskStateUseCase;

    private Task todoTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Project project = Project.create(1L, "Proyecto", LocalDate.now(), LocalDate.now().plusDays(1));
        todoTask = Task.create(100L, project, "Tarea Pendiente", 8, null, TaskStatus.TODO);
    }

    @Test
    void shouldChangeStateSuccessfully() {
        // Arrange
        when(taskRepository.findById(100L)).thenReturn(Optional.of(todoTask));
        doNothing().when(taskRepository).updateTask(any(Task.class));

        // Act
        changeTaskStateUseCase.changeState(100L, TaskStatus.IN_PROGRESS);

        // Assert
        verify(taskRepository, times(1)).findById(100L);
        verify(taskRepository, times(1)).updateTask(any(Task.class));
        assertEquals(TaskStatus.IN_PROGRESS, todoTask.getStatus());
        assertNull(todoTask.getFinishedAt());
    }

    @Test
    void shouldSetFinishedAt_whenStateChangesToDone() {
        // Arrange
        when(taskRepository.findById(100L)).thenReturn(Optional.of(todoTask));
        doNothing().when(taskRepository).updateTask(any(Task.class));

        // Act
        changeTaskStateUseCase.changeState(100L, TaskStatus.DONE);

        // Assert
        verify(taskRepository, times(1)).findById(100L);
        verify(taskRepository, times(1)).updateTask(any(Task.class));
        assertEquals(TaskStatus.DONE, todoTask.getStatus());
        assertNotNull(todoTask.getFinishedAt());
    }

    @Test
    void shouldThrowResourceNotFoundException_whenTaskDoesNotExist() {
        // Arrange
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            changeTaskStateUseCase.changeState(99L, TaskStatus.DONE);
        });

        verify(taskRepository, times(1)).findById(99L);
        verify(taskRepository, never()).updateTask(any(Task.class));
    }
}
