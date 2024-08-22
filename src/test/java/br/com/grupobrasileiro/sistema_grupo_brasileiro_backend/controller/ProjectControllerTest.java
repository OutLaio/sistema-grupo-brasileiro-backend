package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.CollaboratorAssignmentForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.UnauthorizedException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.ProjectService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.ProjectController;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testProjectAll() throws Exception {
        List<ProjectView> projectViews = List.of(
            new ProjectView(1L, "Project Title", "Project Description", 50, "IN_PROGRESS", 1L)
        );
        Page<ProjectView> page = new PageImpl<>(projectViews, PageRequest.of(0, 10), projectViews.size());

        when(projectService.projectAll(any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/projects/all")
                .param("page", "0")
                .param("direction", "ASC")
                .param("orderBy", "title")  
                .param("size", "10")
                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.content[0].id").value(1))
               .andExpect(jsonPath("$.content[0].title").value("Project Title"))
               .andExpect(jsonPath("$.content[0].description").value("Project Description"))
               .andExpect(jsonPath("$.content[0].progress").value(50))
               .andExpect(jsonPath("$.content[0].status").value("IN_PROGRESS"))
               .andExpect(jsonPath("$.content[0].projectUserId").value(1L));
    }

    @Test
    public void testGetProjectById_Success() throws Exception {
        ProjectView projectView = new ProjectView(1L, "Project Title", "Project Description", 50, "IN_PROGRESS", 1L);

        when(projectService.getProjectById(1L)).thenReturn(projectView);

        mockMvc.perform(get("/api/v1/projects/{id}", 1L))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.title").value("Project Title"))
               .andExpect(jsonPath("$.description").value("Project Description"))
               .andExpect(jsonPath("$.progress").value(50))
               .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
               .andExpect(jsonPath("$.projectUserId").value(1L));
    }

    @Test
    public void testGetProjectById_NotFound() throws Exception {
        when(projectService.getProjectById(anyLong())).thenThrow(new RuntimeException("Project not found"));

        mockMvc.perform(get("/api/v1/projects/{id}", 1L))
               .andExpect(status().isNotFound())
               .andExpect(content().string("Project not found"));
    }

    @Test
    public void testUpdateProjectStatus_Success() throws Exception {
        ProjectView projectView = new ProjectView(1L, "Project Title", "Project Description", 50, "COMPLETED", 1L);

        when(projectService.updateProjectStatus(1L, "COMPLETED")).thenReturn(projectView);

        mockMvc.perform(put("/api/v1/projects/{id}/status", 1L)
                .param("status", "COMPLETED")
                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.title").value("Project Title"))
               .andExpect(jsonPath("$.description").value("Project Description"))
               .andExpect(jsonPath("$.progress").value(50))
               .andExpect(jsonPath("$.status").value("COMPLETED"))
               .andExpect(jsonPath("$.projectUserId").value(1L));
    }

    @Test
    public void testUpdateProjectStatus_NotFound() throws Exception {
        when(projectService.updateProjectStatus(anyLong(), anyString())).thenThrow(new RuntimeException("Project not found"));

        mockMvc.perform(put("/api/v1/projects/{id}/status", 1L)
                .param("status", "COMPLETED")
                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound())
               .andExpect(content().string("Project not found"));
    }

    @Test
    public void testSave_Success() throws Exception {
        ProjectForm projectForm = new ProjectForm("Project Title", "Project Description");
        ProjectView projectView = new ProjectView(1L, "Project Title", "Project Description", 50, "IN_PROGRESS", 1L);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("user@example.com");

        when(projectService.save(any(ProjectForm.class), any(UserDetails.class))).thenReturn(projectView);

        String jsonContent = objectMapper.writeValueAsString(projectForm);

        mockMvc.perform(post("/api/v1/projects/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.title").value("Project Title"))
               .andExpect(jsonPath("$.description").value("Project Description"))
               .andExpect(jsonPath("$.progress").value(50))
               .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
               .andExpect(jsonPath("$.projectUserId").value(1L));
    }

    @Test
    public void testSave_BadRequest() throws Exception {
        ProjectForm projectForm = new ProjectForm("", "Project Description");

        String jsonContent = objectMapper.writeValueAsString(projectForm);

        mockMvc.perform(post("/api/v1/projects/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void testAssignCollaboratorToProject_Success() throws Exception {
        CollaboratorAssignmentForm collaborator = new CollaboratorAssignmentForm(1L);

        doNothing().when(projectService).assignCollaboratorToProject(anyLong(), anyLong());

        String jsonContent = objectMapper.writeValueAsString(collaborator);

        mockMvc.perform(post("/api/v1/projects/{projectId}/assign-collaborator", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().string("Colaborador atribuído ao projeto com sucesso."));
    }

    @Test
    public void testAssignCollaboratorToProject_NotFound() throws Exception {
        CollaboratorAssignmentForm collaborator = new CollaboratorAssignmentForm(1L);

        doThrow(new EntityNotFoundException("Entity not found")).when(projectService).assignCollaboratorToProject(anyLong(), anyLong());

        String jsonContent = objectMapper.writeValueAsString(collaborator);

        mockMvc.perform(post("/api/v1/projects/{projectId}/assign-collaborator", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound())
               .andExpect(content().string("Entity not found"));
    }

    @Test
    public void testAssignCollaboratorToProject_InternalError() throws Exception {
        CollaboratorAssignmentForm collaborator = new CollaboratorAssignmentForm(1L);

        doThrow(new Exception("Internal server error")).when(projectService).assignCollaboratorToProject(anyLong(), anyLong());

        String jsonContent = objectMapper.writeValueAsString(collaborator);

        mockMvc.perform(post("/api/v1/projects/{projectId}/assign-collaborator", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isInternalServerError())
               .andExpect(content().string("Erro interno do servidor."));
    }
}
