package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.CollaboratorAssignmentForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = {ProjectController.class})
public class ProjectControllerTest {

    @MockBean
    private ProjectService projectService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private Faker faker;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    public void setUp() {
        // Initialize Faker and ObjectMapper
        faker = new Faker();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAssignCollaboratorToProject_Success() throws Exception {
        Long projectId = faker.number().randomNumber();
        Long collaboratorId = faker.number().randomNumber();
        CollaboratorAssignmentForm collaboratorForm = new CollaboratorAssignmentForm(collaboratorId);

        doNothing().when(projectService).assignCollaboratorToProject(projectId, collaboratorId);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/projects/{projectId}/collaborators", projectId)
                .contentType(MediaType.APPLICATION_JSON) // Make sure MediaType is correctly imported
                .content(objectMapper.writeValueAsString(collaboratorForm)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertEquals("Colaborador atribuído ao projeto com sucesso.", result.getResponse().getContentAsString());
        verify(projectService, times(1)).assignCollaboratorToProject(projectId, collaboratorId);
    }

    @Test
    public void testAssignCollaboratorToProject_ProjectNotFound() throws Exception {
        Long projectId = faker.number().randomNumber();
        Long collaboratorId = faker.number().randomNumber();
        CollaboratorAssignmentForm collaboratorForm = new CollaboratorAssignmentForm(collaboratorId);

        doThrow(new RuntimeException("Project not found")).when(projectService).assignCollaboratorToProject(projectId, collaboratorId);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/projects/{projectId}/collaborators", projectId)
                .contentType(MediaType.APPLICATION_JSON) // Make sure MediaType is correctly imported
                .content(objectMapper.writeValueAsString(collaboratorForm)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn();

        assertEquals("Project not found", result.getResponse().getContentAsString());
        verify(projectService, times(1)).assignCollaboratorToProject(projectId, collaboratorId);
    }

    @Test
    public void testGetAllProjects_Success() throws Exception {
        int page = faker.number().numberBetween(0, 10);
        int size = faker.number().numberBetween(1, 50);
        String direction = faker.options().option("ASC", "DESC");
        String orderBy = faker.lorem().word();

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), orderBy);

        // Ensure the correct Page class is used
        ProjectView projectView = new ProjectView(
                faker.number().randomNumber(),
                faker.lorem().word(),
                faker.lorem().paragraph(),
                faker.number().numberBetween(0, 100),
                faker.options().option("ACTIVE", "INACTIVE", "COMPLETED"),
                faker.number().randomNumber()
        );

        Page<ProjectView> mockPage = new PageImpl<>(List.of(projectView));
        when(projectService.projectAll(pageRequest)).thenReturn(mockPage);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/projects")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .param("direction", direction)
                .param("orderBy", orderBy))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(mockPage), responseBody);
        verify(projectService, times(1)).projectAll(pageRequest);
    }

    @Test
    public void testGetProjectsByCollaborators_Success() throws Exception {
        int page = faker.number().numberBetween(0, 10);
        int size = faker.number().numberBetween(1, 50);
        String direction = faker.options().option("ASC", "DESC");
        String orderBy = faker.lorem().word();

        Integer role = RoleEnum.ROLE_COLLABORATOR.getCode();

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), orderBy);

        // Ensure the correct Page class is used
        ProjectView projectView = new ProjectView(
                faker.number().randomNumber(),
                faker.lorem().word(),
                faker.lorem().paragraph(),
                faker.number().numberBetween(0, 100),
                faker.options().option("ACTIVE", "INACTIVE", "COMPLETED"),
                faker.number().randomNumber()
        );

        Page<ProjectView> mockPage = new PageImpl<>(List.of(projectView));
        when(projectService.projectsCollaborators(pageRequest, role)).thenReturn(mockPage);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/projects/collaborators")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .param("direction", direction)
                .param("orderBy", orderBy))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(mockPage), responseBody);
        verify(projectService, times(1)).projectsCollaborators(pageRequest, role);
    }

    @Test
    public void testGetProjectById_Success() throws Exception {
        Long projectId = faker.number().randomNumber();
        ProjectView projectView = new ProjectView(
                projectId,
                faker.lorem().word(),
                faker.lorem().paragraph(),
                faker.number().numberBetween(0, 100),
                faker.options().option("ACTIVE", "INACTIVE", "COMPLETED"),
                faker.number().randomNumber()
        );

        when(projectService.getProjectById(projectId)).thenReturn(projectView);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}", projectId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(projectView), responseBody);
        verify(projectService, times(1)).getProjectById(projectId);
    }

    @Test
    public void testGetProjectById_NotFound() throws Exception {
        Long projectId = faker.number().randomNumber();

        when(projectService.getProjectById(projectId)).thenThrow(new RuntimeException("Project not found"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/projects/{projectId}", projectId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        assertEquals("Project not found", result.getResponse().getContentAsString());
        verify(projectService, times(1)).getProjectById(projectId);
    }

    @Test
    public void testUpdateProjectStatus_Success() throws Exception {
        Long projectId = faker.number().randomNumber();
        String newStatus = "COMPLETED";

        ProjectView updatedProjectView = new ProjectView(
                projectId,
                faker.lorem().word(),
                faker.lorem().paragraph(),
                faker.number().numberBetween(0, 100),
                newStatus,
                faker.number().randomNumber()
        );

        when(projectService.updateProjectStatus(projectId, newStatus)).thenReturn(updatedProjectView);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/projects/{projectId}/status", projectId)
                .param("status", newStatus))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(updatedProjectView), responseBody);
        verify(projectService, times(1)).updateProjectStatus(projectId, newStatus);
    }

    @Test
    public void testUpdateProjectStatus_BadRequest() throws Exception {
        Long projectId = faker.number().randomNumber();
        String newStatus = "INVALID_STATUS";

        when(projectService.updateProjectStatus(projectId, newStatus)).thenThrow(new RuntimeException("Invalid status"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/projects/{projectId}/status", projectId)
                .param("status", newStatus))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        assertEquals("Invalid status", result.getResponse().getContentAsString());
        verify(projectService, times(1)).updateProjectStatus(projectId, newStatus);
    }
}
