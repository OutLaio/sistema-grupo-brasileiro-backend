package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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
import org.springframework.security.core.userdetails.UserDetails;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.ProjectFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.ProjectUser;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.ProjectUserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;

public class ProjectServiceTest {

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
        // Sample data for ProjectForm
        String title = faker.company().name();
        String description = faker.lorem().sentence();
        
        // Create ProjectForm with the data
        ProjectForm projectForm = new ProjectForm(title, description);

        // Create Project and define the mapping return
        Project project = new Project();
        Project savedProject = new Project();
        savedProject.setId(1L); // Set an ID for the saved project

        // Create expected ProjectView with dummy data
        ProjectView expectedProjectView = new ProjectView(
            1L, 
            title, 
            description, 
            0, 
            "New", 
            null 
        );

        // Simulate behavior of mappers and repositories
        when(projectFormMapper.map(any(ProjectForm.class))).thenReturn(project);
        when(projectRepository.save(any(Project.class))).thenReturn(savedProject);
        when(projectViewMapper.map(any(Project.class))).thenReturn(expectedProjectView);

        // Simulate UserRepository behavior
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("user@example.com");
        
        User user = new User(); 
        user.setRole(RoleEnum.ROLE_CLIENT.getCode());
        when(userRepository.findByEmail("user@example.com")).thenReturn(user);

        // Act
        ProjectView result = projectService.save(projectForm, userDetails);

        // Assert
        verify(projectFormMapper).map(projectForm);
        verify(projectRepository).save(project);
        verify(projectViewMapper).map(savedProject);
        assertEquals(expectedProjectView, result);
    }

    @Test
    void testFindProjectById() {
        Long projectId = faker.number().randomNumber();
        Project project = new Project();
        project.setId(projectId);
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

        // Act
        ProjectView result = projectService.getProjectById(projectId);

        // Assert
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

        // Act
        projectService.assignCollaboratorToProject(projectId, collaboratorId);

        // Assert
        verify(projectUserRepository).save(any(ProjectUser.class));
    }

    @Test
    void testUpdateProjectStatus() {
        Long projectId = faker.number().randomNumber();
        String newStatus = "AF";
        Project project = new Project();
        project.setStatus(newStatus);
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

        // Act
        ProjectView result = projectService.updateProjectStatus(projectId, newStatus);

        // Assert
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

        // Act
        Page<ProjectView> result = projectService.projectAll(PageRequest.of(0, 10));

        // Assert
        verify(projectRepository).findAll(any(PageRequest.class));
        verify(projectViewMapper).map(project1);
        verify(projectViewMapper).map(project2);
        assertEquals(projectViewPage.getContent(), result.getContent());
        assertEquals(projectViewPage.getTotalElements(), result.getTotalElements());
    }
}
