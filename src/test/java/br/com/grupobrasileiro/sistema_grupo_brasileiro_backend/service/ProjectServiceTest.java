package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.ProjectFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.ProjectUser;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.ProjectRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectFormMapper projectFormMapper;

    @Mock
    private ProjectViewMapper projectViewMapper;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testSaveProject() {
        // Arrange
        ProjectForm form = new ProjectForm(
            faker.lorem().word(),
            faker.lorem().sentence(),
            faker.number().numberBetween(0, 100),
            faker.lorem().word(),
            new HashSet<>()
        );

        User client = new User();
        client.setName(faker.name().firstName());
        client.setLastname(faker.name().lastName());
        client.setEmail(faker.internet().emailAddress());
        client.setRole(RoleEnum.ROLE_CLIENT.getCode());  // Usando o código do RoleEnum

        ProjectUser projectUser = new ProjectUser();
        projectUser.setClient(client);

        Set<ProjectUser> projectUsers = new HashSet<>();
        projectUsers.add(projectUser);

        Project project = new Project(
            null,
            form.title(),
            form.description(),
            form.progress(),
            form.status(),
            projectUsers
        );

        ProjectView projectView = new ProjectView(
            faker.number().randomNumber(),
            form.title(),
            form.description(),
            form.progress(),
            form.status(),
            new HashSet<>()
        );

        // Mock do mapeamento e salvamento
        when(projectFormMapper.map(form)).thenReturn(project);
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(projectViewMapper.map(project)).thenReturn(projectView);

        // Act
        ProjectView result = projectService.save(form);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(projectView.id());
        assertThat(result.title()).isEqualTo(form.title());
        assertThat(result.description()).isEqualTo(form.description());
        assertThat(result.progress()).isEqualTo(form.progress());
        assertThat(result.status()).isEqualTo(form.status());
    }

    @Test
    public void testGetProjectById() {
        // Arrange
        Long projectId = faker.number().randomNumber();
        Project project = new Project();
        project.setId(projectId);
        project.setTitle(faker.lorem().word());
        project.setDescription(faker.lorem().sentence());
        project.setProgress(faker.number().numberBetween(0, 100));
        project.setStatus(faker.lorem().word());

        User client = new User();
        client.setId(faker.number().randomNumber());
        client.setName(faker.name().firstName());
        client.setLastname(faker.name().lastName());
        client.setPhonenumber(faker.phoneNumber().phoneNumber());
        client.setSector(faker.lorem().word());
        client.setOccupation(faker.lorem().word());
        client.setNop(faker.lorem().word()); // Ajuste para String
        client.setEmail(faker.internet().emailAddress());
        client.setRole(RoleEnum.ROLE_CLIENT.getCode());
        client.setActive(true);

        User collaborator = new User();
        collaborator.setId(faker.number().randomNumber());
        collaborator.setName(faker.name().firstName());
        collaborator.setLastname(faker.name().lastName());
        collaborator.setPhonenumber(faker.phoneNumber().phoneNumber());
        collaborator.setSector(faker.lorem().word());
        collaborator.setOccupation(faker.lorem().word());
        collaborator.setNop(faker.lorem().word()); // Ajuste para String
        collaborator.setEmail(faker.internet().emailAddress());
        collaborator.setRole(RoleEnum.ROLE_COLLABORATOR.getCode());
        collaborator.setActive(true);

        ProjectUser projectUser = new ProjectUser();
        projectUser.setClient(client);
        projectUser.setCollaborator(collaborator);

        Set<ProjectUser> projectUsers = Set.of(projectUser);

        ProjectView projectView = new ProjectView(
            projectId,
            project.getTitle(),
            project.getDescription(),
            project.getProgress(),
            project.getStatus(),
            Set.of(
                new UserView(client.getId(), client.getName(), client.getLastname(), client.getPhonenumber(), client.getSector(), client.getOccupation(), client.getNop(), client.getEmail(), client.getRole(), client.getActive()),
                new UserView(collaborator.getId(), collaborator.getName(), collaborator.getLastname(), collaborator.getPhonenumber(), collaborator.getSector(), collaborator.getOccupation(), collaborator.getNop(), collaborator.getEmail(), collaborator.getRole(), collaborator.getActive())
            )
        );

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectViewMapper.map(project)).thenReturn(projectView);

        // Act
        ProjectView result = projectService.getProjectById(projectId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(projectId);
        assertThat(result.title()).isEqualTo(project.getTitle());
        assertThat(result.description()).isEqualTo(project.getDescription());
        assertThat(result.progress()).isEqualTo(project.getProgress());
        assertThat(result.status()).isEqualTo(project.getStatus());
        assertThat(result.users()).isEqualTo(projectView.users());
    }

    @Test
    public void testProjectAll() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Project> projectPage = new PageImpl<>(List.of(new Project(), new Project()));
        when(projectRepository.findAll(pageRequest)).thenReturn(projectPage);

        ProjectView projectView = new ProjectView(
            faker.number().randomNumber(),
            faker.lorem().word(),
            faker.lorem().sentence(),
            faker.number().numberBetween(0, 100),
            faker.lorem().word(),
            new HashSet<>()
        );

        when(projectViewMapper.map(any(Project.class))).thenReturn(projectView);

        // Act
        Page<ProjectView> result = projectService.projectAll(pageRequest);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent().size()).isEqualTo(2); // Based on PageImpl size
    }

    @Test
    public void testSaveProject_InvalidData() {
        // Given
        ProjectForm invalidForm = new ProjectForm("", "", -1, "", new HashSet<>());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            projectService.save(invalidForm);
        });
    }

    @Test
    public void testGetProjectById_NotFound() {
        // Given
        Long invalidId = 999L;
        when(projectRepository.findById(invalidId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            projectService.getProjectById(invalidId);
        });
    }

    @Test
    public void testUpdateProjectStatus_InvalidStatus() {
        // Given
        Long projectId = 1L;
        String invalidStatus = "InvalidStatus";
        Project project = new Project();
        project.setId(projectId);
        project.setStatus("In Progress");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            projectService.updateProjectStatus(projectId, invalidStatus);
        });
    }

    @Test
    public void testUpdateProjectStatus() {
        // Arrange
        Long projectId = faker.number().randomNumber();
        String newStatus = "Completed";
        Project project = new Project();
        project.setId(projectId);
        project.setStatus("In Progress");

        Project updatedProject = new Project();
        updatedProject.setId(projectId);
        updatedProject.setStatus(newStatus);

        ProjectView projectView = new ProjectView(
            projectId,
            faker.lorem().word(),
            faker.lorem().sentence(),
            faker.number().numberBetween(0, 100),
            newStatus,
            new HashSet<>()
        );

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(updatedProject);
        when(projectViewMapper.map(updatedProject)).thenReturn(projectView);

        // Act
        ProjectView result = projectService.updateProjectStatus(projectId, newStatus);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(projectId);
        assertThat(result.status()).isEqualTo(newStatus);
    }
}

