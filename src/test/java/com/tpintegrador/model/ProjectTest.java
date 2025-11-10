package com.tpintegrador.model;

import domain.exception.FieldNotNullException;
import domain.model.Project;
import domain.model.ProjectStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    @Test
    void shouldCreateProjectInstance_whenAllFieldsAreValid() {
        Project project = Project.createInstance(1L, "Test Project", LocalDate.now(), LocalDate.now().plusDays(1), ProjectStatus.PLANNED, "Test Description");
        assertNotNull(project);
    }

    @Test
    void shouldThrowException_whenNameIsNull() {
        assertThrows(FieldNotNullException.class, () -> {
            Project.createInstance(1L, null, LocalDate.now(), LocalDate.now().plusDays(1), ProjectStatus.PLANNED, "Test Description");
        });
    }

    @Test
    void shouldThrowException_whenStartDateIsNull() {
        assertThrows(FieldNotNullException.class, () -> {
            Project.createInstance(1L, "Test Project", null, LocalDate.now().plusDays(1), ProjectStatus.PLANNED, "Test Description");
        });
    }

    @Test
    void shouldThrowException_whenEndDateIsNull() {
        assertThrows(FieldNotNullException.class, () -> {
            Project.createInstance(1L, "Test Project", LocalDate.now(), null, ProjectStatus.PLANNED, "Test Description");
        });
    }

    @Test
    void shouldThrowException_whenStatusIsNull() {
        assertThrows(FieldNotNullException.class, () -> {
            Project.createInstance(1L, "Test Project", LocalDate.now(), LocalDate.now().plusDays(1), null, "Test Description");
        });
    }

    @Test
    void shouldThrowException_whenEndDateIsBeforeStartDate() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.minusDays(1);
        assertThrows(IllegalArgumentException.class, () -> {
            Project.createInstance(1L, "Test Project", startDate, endDate, ProjectStatus.PLANNED, "Test Description");
        });
    }
}
