package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.CollaboratorAssignmentForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.UnauthorizedException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.ProjectFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.ProjectService;

public class ProjectControllerTest {

    @Mock
    private ProjectFormMapper projectFormMapper;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectViewMapper projectViewMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testAssignCollaboratorToProject_Success() {
        Long projectId = faker.number().randomNumber();
        Long collaboratorId = faker.number().randomNumber();
        CollaboratorAssignmentForm collaboratorForm = new CollaboratorAssignmentForm(collaboratorId);

        doNothing().when(projectService).assignCollaboratorToProject(projectId, collaboratorId);

        ResponseEntity<String> response = projectController.assignCollaboratorToProject(projectId, collaboratorForm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Colaborador atribuído ao projeto com sucesso.", response.getBody());
        verify(projectService, times(1)).assignCollaboratorToProject(projectId, collaboratorId);
    }

    @Test
    public void testAssignCollaboratorToProject_ProjectNotFound() {
        Long projectId = faker.number().randomNumber();
        Long collaboratorId = faker.number().randomNumber();
        CollaboratorAssignmentForm collaboratorForm = new CollaboratorAssignmentForm(collaboratorId);

        doThrow(new EntityNotFoundException("Project not found")).when(projectService).assignCollaboratorToProject(projectId, collaboratorId);

        ResponseEntity<String> response = projectController.assignCollaboratorToProject(projectId, collaboratorForm);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Project not found", response.getBody());
        verify(projectService, times(1)).assignCollaboratorToProject(projectId, collaboratorId);
    }

    @Test
    public void testAssignCollaboratorToProject_Unauthorized() {
        Long projectId = faker.number().randomNumber();
        Long collaboratorId = faker.number().randomNumber();
        CollaboratorAssignmentForm collaboratorForm = new CollaboratorAssignmentForm(collaboratorId);

        doThrow(new UnauthorizedException("Unauthorized access attempt.")).when(projectService).assignCollaboratorToProject(projectId, collaboratorId);

        ResponseEntity<String> response = projectController.assignCollaboratorToProject(projectId, collaboratorForm);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Usuário não autorizado.", response.getBody());
        verify(projectService, times(1)).assignCollaboratorToProject(projectId, collaboratorId);
    }

    @Test
    public void testAssignCollaboratorToProject_InternalServerError() {
        Long projectId = faker.number().randomNumber();
        Long collaboratorId = faker.number().randomNumber();
        CollaboratorAssignmentForm collaboratorForm = new CollaboratorAssignmentForm(collaboratorId);

        doThrow(new RuntimeException("Unexpected error")).when(projectService).assignCollaboratorToProject(projectId, collaboratorId);

        ResponseEntity<String> response = projectController.assignCollaboratorToProject(projectId, collaboratorForm);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro interno do servidor.", response.getBody());
        verify(projectService, times(1)).assignCollaboratorToProject(projectId, collaboratorId);
    }

    @Test
    public void testGetAllProjects_Success() {
        int page = faker.number().numberBetween(0, 10);
        int size = faker.number().numberBetween(1, 50);
        String direction = faker.options().option("ASC", "DESC");
        String orderBy = faker.lorem().word();

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);

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

        ResponseEntity<Page<ProjectView>> response = projectController.projectAll(page, direction, orderBy, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPage, response.getBody());
        verify(projectService, times(1)).projectAll(pageRequest);
    }

    @Test
    public void testGetProjectsByCollaborators_Success() {
        int page = faker.number().numberBetween(0, 10);
        int size = faker.number().numberBetween(1, 50);
        String direction = faker.options().option("ASC", "DESC");
        String orderBy = faker.lorem().word();

        Integer role = RoleEnum.ROLE_COLLABORATOR.getCode();

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);

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

        ResponseEntity<Page<ProjectView>> response = projectController.projectsByRole(page, direction, orderBy, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPage, response.getBody());
        verify(projectService, times(1)).projectsCollaborators(pageRequest, role);
    }

    @Test
    public void testGetProjectById_Success() {
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

        ResponseEntity<ProjectView> response = projectController.getProjectById(projectId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projectView, response.getBody());
        verify(projectService, times(1)).getProjectById(projectId);
    }

    @Test
    public void testGetProjectById_NotFound() {
        Long projectId = faker.number().randomNumber();

        when(projectService.getProjectById(projectId)).thenThrow(new EntityNotFoundException("Project not found"));

        ResponseEntity<ProjectView> response = projectController.getProjectById(projectId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(projectService, times(1)).getProjectById(projectId);
    }

    @Test
    public void testUpdateProjectStatus_Success() {
        Long projectId = faker.number().randomNumber();
        String newStatus = "COMPLETED";

        ProjectView updatedProjectView = new ProjectView(
            projectId,
            "Updated Project Title",
            "Updated Project Description",
            75,
            newStatus,
            2L
        );

        when(projectService.updateProjectStatus(projectId, newStatus)).thenReturn(updatedProjectView);

        ResponseEntity<ProjectView> response = projectController.updateProjectStatus(projectId, newStatus);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProjectView, response.getBody());
        verify(projectService, times(1)).updateProjectStatus(projectId, newStatus);
    }


    @Test
    public void testUpdateProjectStatus_NotFound() {
        Long projectId = faker.number().randomNumber();
        String newStatus = "COMPLETED";

        when(projectService.updateProjectStatus(projectId, newStatus)).thenThrow(new EntityNotFoundException("Project not found"));

        ResponseEntity<ProjectView> response = projectController.updateProjectStatus(projectId, newStatus);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
        verify(projectService, times(1)).updateProjectStatus(projectId, newStatus);
    }

    @Test
    public void testUpdateProjectStatus_InternalServerError() {
        Long projectId = faker.number().randomNumber();
        String newStatus = "COMPLETED";

        when(projectService.updateProjectStatus(projectId, newStatus)).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<ProjectView> response = projectController.updateProjectStatus(projectId, newStatus);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(projectService, times(1)).updateProjectStatus(projectId, newStatus);
    }

}

