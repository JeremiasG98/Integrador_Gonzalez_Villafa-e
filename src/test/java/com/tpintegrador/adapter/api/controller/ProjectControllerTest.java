package com.tpintegrador.adapter.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpintegrador.adapter.api.dto.ChangeProjectStatusRequestDTO;
import com.tpintegrador.adapter.api.dto.CreateProjectRequestDTO;
import com.tpintegrador.domain.model.Project;
import com.tpintegrador.domain.model.ProjectStatus;
import com.tpintegrador.domain.output.IProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional //asegura que cada test se ejecute en una transacción que se revierte al final.
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IProjectRepository projectRepository;

    @Test
    void shouldCreateProjectSuccessfully() throws Exception {
        // arrange
        CreateProjectRequestDTO request = new CreateProjectRequestDTO(
                "Proyecto de Integración",
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(30)
        );

        // Act & Assert
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Proyecto de Integración"))
                .andExpect(jsonPath("$.status").value("PLANNED"));
    }

    @Test
    void shouldGetProjectByIdSuccessfully() throws Exception {
        // arrange: Creamos un proyecto directamente en la BD para el test
        Project project = projectRepository.save(Project.create(null, "Proyecto a Buscar", LocalDate.now().plusDays(1), LocalDate.now().plusDays(10)));

        // Act & Assert
        mockMvc.perform(get("/projects/{id}", project.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(project.getId()))
                .andExpect(jsonPath("$.name").value("Proyecto a Buscar"));
    }

    @Test
    void shouldReturnNotFound_whenGettingNonExistentProject() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/projects/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Proyecto no encontrado con ID: 999"));
    }

    @Test
    void shouldChangeProjectStatusSuccessfully() throws Exception {
        // Arrange
        Project project = projectRepository.save(Project.create(null, "Proyecto a Cambiar", LocalDate.now().plusDays(1), LocalDate.now().plusDays(10)));
        ChangeProjectStatusRequestDTO request = new ChangeProjectStatusRequestDTO(ProjectStatus.ACTIVE);

        // Act & Assert
        mockMvc.perform(post("/projects/{id}/status", project.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        //verificar que el estado realmente cambió
        mockMvc.perform(get("/projects/{id}", project.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }
}
