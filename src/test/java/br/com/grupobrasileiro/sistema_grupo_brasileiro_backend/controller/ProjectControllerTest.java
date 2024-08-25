package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.CollaboratorAssignmentForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.CompanyForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.CompanyService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.ProjectService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private ProjectController projectController;

    private Faker faker;

    @BeforeEach
    public void setUp() {
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

         
        doThrow(new RuntimeException("Project not found"))
            .when(projectService).assignCollaboratorToProject(projectId, collaboratorId);

       
        ResponseEntity<String> response = projectController.assignCollaboratorToProject(projectId, collaboratorForm);

        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());  
        assertEquals("Project not found", response.getBody());

       
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

        when(projectService.getProjectById(projectId)).thenThrow(new RuntimeException("Project not found"));

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

        when(projectService.updateProjectStatus(projectId, newStatus)).thenThrow(new RuntimeException("Project not found"));

        ResponseEntity<ProjectView> response = projectController.updateProjectStatus(projectId, newStatus);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
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

    @Test
    public void testSaveProject_Success() {
        ProjectForm projectForm = new ProjectForm(faker.lorem().word(), faker.lorem().paragraph());
        ProjectView projectView = new ProjectView(
            faker.number().randomNumber(),
            faker.lorem().word(),
            faker.lorem().paragraph(),
            faker.number().numberBetween(0, 100),
            "ACTIVE",
            faker.number().randomNumber()
        );

        when(projectService.save(projectForm, null)).thenReturn(projectView);

        ResponseEntity<ProjectView> response = projectController.save(projectForm, null);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(projectView, response.getBody());
        verify(projectService, times(1)).save(projectForm, null);
    }

    @Test
    public void testSaveCompany_Success() {
       
        CompanyForm companyForm = new CompanyForm("Test Company");

        CompanyView companyView = new CompanyView(
            1L,  
            "Test Company"
        );

 
        when(companyService.save(companyForm)).thenReturn(companyView);

        ResponseEntity<CompanyView> response = projectController.save(companyForm);

        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(companyView, response.getBody());

       
        verify(companyService, times(1)).save(companyForm);
    }


    @Test
    public void testSaveCompany_BadRequest() {
 
        CompanyForm companyForm = new CompanyForm("");

        ResponseEntity<CompanyView> response = projectController.save(companyForm);

        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
    
    @Test
    public void testCompanyAll_Success() {
      
        Page<CompanyView> companyViews = new PageImpl<>(List.of(new CompanyView(1L, "Company1")));
        when(companyService.companyAll(any(PageRequest.class))).thenReturn(companyViews);

       
        ResponseEntity<Page<CompanyView>> response = projectController.companyAll(0, "ASC", "name", 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals("Company1", response.getBody().getContent().get(0).name());
    }
    
    @Test
    public void testCompanyAll_PaginationAndSorting() {
        
        Page<CompanyView> companyViews = new PageImpl<>(List.of(new CompanyView(1L, "Company1")));
        when(companyService.companyAll(any(PageRequest.class))).thenReturn(companyViews);

         ResponseEntity<Page<CompanyView>> response = projectController.companyAll(1, "DESC", "name", 5);

         assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals("Company1", response.getBody().getContent().get(0).name());
    }
    
    @Test
    public void testCompanyAll_NoData() {
     
        Page<CompanyView> companyViews = Page.empty();
        when(companyService.companyAll(any(PageRequest.class))).thenReturn(companyViews);

     
        ResponseEntity<Page<CompanyView>> response = projectController.companyAll(0, "ASC", "name", 10);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getContent().isEmpty());
    }



}