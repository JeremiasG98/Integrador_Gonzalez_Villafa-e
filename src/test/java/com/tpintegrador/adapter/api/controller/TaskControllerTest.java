package com.tpintegrador.adapter.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpintegrador.adapter.api.dto.ChangeTaksStatusRequestDTO;
import com.tpintegrador.adapter.api.dto.CreateTaskRequestDTO;
import com.tpintegrador.domain.model.Project;
import com.tpintegrador.domain.model.Task;
import com.tpintegrador.domain.model.TaskStatus;
import com.tpintegrador.domain.output.IProjectRepository;
import com.tpintegrador.domain.output.ITaskRepository;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IProjectRepository projectRepository;

    @Autowired
    private ITaskRepository taskRepository;

    private Project testProject;

    @BeforeEach
    void setUp() {
        //creamos un proyecto de prueba antes de cada test
        testProject = projectRepository.save(Project.create(null, "Proyecto para Tareas", LocalDate.now().plusDays(1), LocalDate.now().plusDays(20)));
    }

    @Test
    void shouldCreateTaskSuccessfully() throws Exception {
        // Arrange
        CreateTaskRequestDTO request = new CreateTaskRequestDTO(
                "Nueva Tarea de Test",
                10,
                "test-user",
                TaskStatus.TODO
        );

        // Act & Assert
        mockMvc.perform(post("/projects/{projectId}/tasks", testProject.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Nueva Tarea de Test"))
                .andExpect(jsonPath("$.status").value("TODO"))
                .andExpect(jsonPath("$.projectId").value(testProject.getId()));
    }

    @Test
    void shouldGetTaskByIdSuccessfully() throws Exception {
        //Arrange
        Task task = taskRepository.save(Task.create(null, testProject, "Tarea a Buscar", 5, null, TaskStatus.IN_PROGRESS));

        //Act & Assert
        mockMvc.perform(get("/projects/{projectId}/tasks/{taskId}", testProject.getId(), task.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.title").value("Tarea a Buscar"));
    }

    @Test
    void shouldChangeTaskStatusSuccessfully() throws Exception {
        //Arrange
        Task task = taskRepository.save(Task.create(null, testProject, "Tarea a Cambiar", 8, null, TaskStatus.TODO));
        ChangeTaksStatusRequestDTO request = new ChangeTaksStatusRequestDTO(TaskStatus.DONE);

        // Act & Assert
        mockMvc.perform(post("/projects/{projectId}/tasks/{taskId}/status", testProject.getId(), task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        //verificar que el estado realmente cambió
        mockMvc.perform(get("/projects/{projectId}/tasks/{taskId}", testProject.getId(), task.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DONE"));
    }
}
