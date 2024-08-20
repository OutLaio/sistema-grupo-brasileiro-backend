package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.ProjectFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.ProjectUser;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.ProjectUserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectUserRepository projectUserRepository;

    @Mock
    private ProjectFormMapper projectFormMapper;

    @Mock
    private ProjectViewMapper projectViewMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectService projectService;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    void testSaveProject() {
        String title = faker.company().name();
        String description = faker.lorem().sentence();
        Integer progress = faker.number().numberBetween(0, 100);
        String status = faker.random().nextBoolean() ? "AF" : "EA";
        Long clientId = faker.number().randomNumber();

        ProjectForm projectForm = new ProjectForm(title, description, progress, status, clientId);
        Project project = new Project();
        Project savedProject = new Project();

        when(projectFormMapper.map(any(ProjectForm.class))).thenReturn(project);
        when(projectRepository.save(any(Project.class))).thenReturn(savedProject);

        projectService.save(projectForm);

        verify(projectFormMapper).map(projectForm);
        verify(projectRepository).save(project);
        
        for (ProjectUser projectUser : savedProject.getUsers()) {
            verify(projectUserRepository).save(projectUser);
        }
    }

    @Test
    void testFindProjectById() {
        Long projectId = faker.number().randomNumber();
        Project project = new Project();
        ProjectUser projectUser = new ProjectUser();
        projectUser.setId(faker.number().randomNumber());
        project.setUsers(Set.of(projectUser));

        ProjectView projectView = new ProjectView(
            project.getId(), 
            project.getTitle(), 
            project.getDescription(), 
            project.getProgress(), 
            project.getStatus(), 
            projectUser.getId()
        );

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectViewMapper.map(project)).thenReturn(projectView);

        ProjectView result = projectService.getProjectById(projectId);

        verify(projectRepository).findById(projectId);
        verify(projectViewMapper).map(project);
        assertEquals(projectView, result);
        assertEquals(projectUser.getId(), result.projectUserId());
    }

    @Test
    void testAssignCollaboratorToProject() {
        Long projectId = faker.number().randomNumber();
        Long collaboratorId = faker.number().randomNumber();
        Project project = new Project();
        User collaborator = new User();
        ProjectUser projectUser = new ProjectUser();
        
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(userRepository.findById(collaboratorId)).thenReturn(Optional.of(collaborator));
        when(projectUserRepository.findByProjectAndClientIsNotNull(project)).thenReturn(Optional.of(projectUser));
        when(projectUserRepository.existsByProjectAndCollaborator(project, collaborator)).thenReturn(false);

        projectService.assignCollaboratorToProject(projectId, collaboratorId);

        verify(projectUserRepository).save(projectUser);
    }

    @Test
    void testUpdateProjectStatus() {
        Long projectId = faker.number().randomNumber();
        String newStatus = "AF";
        Project project = new Project();
        ProjectView updatedProjectView = new ProjectView(
            project.getId(),
            project.getTitle(),
            project.getDescription(),
            project.getProgress(),
            newStatus,
            null
        );

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepository.save(project)).thenReturn(project);
        when(projectViewMapper.map(project)).thenReturn(updatedProjectView);

        ProjectView result = projectService.updateProjectStatus(projectId, newStatus);

        verify(projectRepository).findById(projectId);
        verify(projectRepository).save(project);
        assertEquals(newStatus, result.status());
    }
    
    @Test
    void testProjectAll() {
        Long projectId1 = 1L;
        Long projectId2 = 2L;

        Project project1 = new Project();
        project1.setId(projectId1);
        project1.setTitle("Title1");
        project1.setDescription("Description1");
        project1.setProgress(50);
        project1.setStatus("AF");
        ProjectUser projectUser1 = new ProjectUser();
        User clientUser1 = new User();
        clientUser1.setId(1L);
        projectUser1.setClient(clientUser1);
        projectUser1.setId(1L);
        project1.setUsers(Set.of(projectUser1));

        Project project2 = new Project();
        project2.setId(projectId2);
        project2.setTitle("Title2");
        project2.setDescription("Description2");
        project2.setProgress(75);
        project2.setStatus("EA");
        ProjectUser projectUser2 = new ProjectUser();
        projectUser2.setClient(null);
        projectUser2.setId(2L);
        project2.setUsers(Set.of(projectUser2));

        List<Project> projects = List.of(project1, project2);
        Page<Project> projectPage = new PageImpl<>(projects, PageRequest.of(0, 10), projects.size());

        ProjectView projectView1 = new ProjectView(
            projectId1, "Title1", "Description1", 50, "AF", clientUser1.getId()
        );
        ProjectView projectView2 = new ProjectView(
            projectId2, "Title2", "Description2", 75, "EA", null
        );

        List<ProjectView> projectViews = List.of(projectView1, projectView2);
        Page<ProjectView> projectViewPage = new PageImpl<>(projectViews, PageRequest.of(0, 10), projectViews.size());

        when(projectRepository.findAll(any(PageRequest.class))).thenReturn(projectPage);
        when(projectViewMapper.map(project1)).thenReturn(projectView1);
        when(projectViewMapper.map(project2)).thenReturn(projectView2);

        Page<ProjectView> result = projectService.projectAll(PageRequest.of(0, 10));

        verify(projectRepository).findAll(any(PageRequest.class));

        assertEquals(projectViewPage.getContent(), result.getContent());
        assertEquals(projectViewPage.getTotalElements(), result.getTotalElements());
    }

    @Test
    void testIsValidStatus() {
        // Testando status válidos
        assertTrue(projectService.isValidStatus("AF"));
        assertTrue(projectService.isValidStatus("EA"));
        assertTrue(projectService.isValidStatus("AA"));
        assertTrue(projectService.isValidStatus("AP"));
        assertTrue(projectService.isValidStatus("EC"));
        assertTrue(projectService.isValidStatus("CO"));
        
        // Testando status inválidos
        assertFalse(projectService.isValidStatus("INVALID"));
        assertFalse(projectService.isValidStatus("123"));
        assertFalse(projectService.isValidStatus("ABCD"));
        assertFalse(projectService.isValidStatus(""));
        assertFalse(projectService.isValidStatus(null)); // Se o método aceitar null, teste isso também
    }
}

}
