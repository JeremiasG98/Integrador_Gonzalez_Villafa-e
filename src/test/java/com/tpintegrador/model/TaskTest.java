package com.tpintegrador.model;

import com.tpintegrador.domain.exception.ValidationException;
import com.tpintegrador.domain.model.Project;
import com.tpintegrador.domain.model.Task;
import com.tpintegrador.domain.model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    private Project proyectoEjemplo;

    @BeforeEach
    void setUp() {
        proyectoEjemplo = Project.create(1L, "Dummy Project", LocalDate.now(), LocalDate.now().plusDays(1));
    }

    @Test
    void shouldCreateTaskSuccessfully() {
        Task task = Task.create(1L, proyectoEjemplo, "Test Task", 8, "user@test.com", TaskStatus.TODO);
        assertNotNull(task);
        assertEquals("Test Task", task.getTitle());
        assertEquals(8, task.getEstimateHours());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertNull(task.getFinishedAt());
        assertNotNull(task.getCreatedAt());
    }

    @Test
    void shouldThrowNullPointerException_whenProjectIsNull() {
        assertThrows(NullPointerException.class, () -> {
            Task.create(1L, null, "Test Task", 8, "user@test.com", TaskStatus.TODO);
        });
    }

    @Test
    void shouldThrowNullPointerException_whenTitleIsNull() {
        assertThrows(NullPointerException.class, () -> {
            Task.create(1L, proyectoEjemplo, null, 8, "user@test.com", TaskStatus.TODO);
        });
    }

    @Test
    void shouldThrowValidationException_whenTitleIsBlank() {
        assertThrows(ValidationException.class, () -> {
            Task.create(1L, proyectoEjemplo, " ", 8, "user@test.com", TaskStatus.TODO);
        });
    }

    @Test
    void shouldThrowNullPointerException_whenEstimateIsNull() {
        assertThrows(NullPointerException.class, () -> {
            Task.create(1L, proyectoEjemplo, "Test Task", null, "user@test.com", TaskStatus.TODO);
        });
    }

    @Test
    void shouldThrowValidationException_whenEstimateIsNotPositive() {
        assertThrows(ValidationException.class, () -> {
            Task.create(1L, proyectoEjemplo, "Test Task", 0, "user@test.com", TaskStatus.TODO);
        });
        assertThrows(ValidationException.class, () -> {
            Task.create(1L, proyectoEjemplo, "Test Task", -5, "user@test.com", TaskStatus.TODO);
        });
    }

    @Test
    void shouldThrowNullPointerException_whenStatusIsNull() {
        assertThrows(NullPointerException.class, () -> {
            Task.create(1L, proyectoEjemplo, "Test Task", 8, "user@test.com", null);
        });
    }

    @Test
    void shouldSetFinishedAt_whenCreatedAsDone() {
        Task task = Task.create(1L, proyectoEjemplo, "Test Task", 8, "user@test.com", TaskStatus.DONE);
        assertNotNull(task.getFinishedAt());
        assertEquals(TaskStatus.DONE, task.getStatus());
    }

    @Test
    void shouldAllowNullAssignee() {
        Task task = Task.create(1L, proyectoEjemplo, "Test Task", 8, null, TaskStatus.TODO);
        assertNotNull(task);
        assertNull(task.getAssignee());
    }
}
