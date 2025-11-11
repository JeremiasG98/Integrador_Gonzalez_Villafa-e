package com.tpintegrador.model;

import domain.exception.ValidationException;
import domain.model.Project;
import domain.model.ProjectStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    @Test
    void shouldCreateProjectWithPlannedStatus() {
        // Arrange
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(10);

        // Act
        Project project = Project.create(1L, "Proyecto de Prueba", startDate, endDate);

        // Assert
        assertNotNull(project);
        assertEquals("Proyecto de Prueba", project.getName());
        assertEquals(ProjectStatus.PLANNED, project.getStatus());
        assertEquals(startDate, project.getStartDate());
        assertEquals(endDate, project.getEndDate());
    }

    @Test
    void shouldThrowNullPointerException_whenNameIsNull() {
        assertThrows(NullPointerException.class, () -> {
            Project.create(1L, null, LocalDate.now(), LocalDate.now().plusDays(1));
        });
    }

    @Test
    void shouldThrowValidationException_whenNameIsBlank() {
        assertThrows(ValidationException.class, () -> {
            Project.create(1L, "  ", LocalDate.now(), LocalDate.now().plusDays(1));
        });
    }

    @Test
    void shouldThrowNullPointerException_whenStartDateIsNull() {
        assertThrows(NullPointerException.class, () -> {
            Project.create(1L, "Test Project", null, LocalDate.now().plusDays(1));
        });
    }

    @Test
    void shouldThrowNullPointerException_whenEndDateIsNull() {
        assertThrows(NullPointerException.class, () -> {
            Project.create(1L, "Test Project", LocalDate.now(), null);
        });
    }

    @Test
    void shouldThrowValidationException_whenEndDateIsBeforeStartDate() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.minusDays(1);
        assertThrows(ValidationException.class, () -> {
            Project.create(1L, "Test Project", startDate, endDate);
        });
    }

    @Test
    void shouldThrowValidationException_whenEndDateIsBeforeToday() {
        LocalDate endDate = LocalDate.now().minusDays(1);
        assertThrows(ValidationException.class, () -> {
            Project.create(1L, "Test Project", endDate.minusDays(5), endDate);
        });
    }
}
