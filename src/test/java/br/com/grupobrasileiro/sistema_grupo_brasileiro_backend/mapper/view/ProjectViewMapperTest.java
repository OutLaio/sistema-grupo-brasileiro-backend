package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.ProjectUser;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;

public class ProjectViewMapperTest {

    private ProjectViewMapper projectViewMapper;
    private Faker faker;

    @BeforeEach
    void setUp() {
        projectViewMapper = new ProjectViewMapper();
        faker = new Faker();
    }

    @Test
    void shouldMapProjectWithEmptyUserSet() {
        // Arrange
        Project project = new Project();
        project.setId(faker.number().randomNumber());
        project.setTitle(faker.lorem().word());
        project.setDescription(faker.lorem().sentence());
        project.setProgress(faker.number().numberBetween(0, 100));
        project.setStatus(faker.lorem().word());
        project.setUsers(new HashSet<>()); // Empty set

        // Act
        ProjectView projectView = projectViewMapper.map(project);

        // Assert
        assertNotNull(projectView, "ProjectView should not be null");
        assertEquals(project.getId(), projectView.id(), "Project ID should match");
        assertEquals(project.getTitle(), projectView.title(), "Project Title should match");
        assertEquals(project.getDescription(), projectView.description(), "Project Description should match");
        assertEquals(project.getProgress(), projectView.progress(), "Project Progress should match");
        assertEquals(project.getStatus(), projectView.status(), "Project Status should match");
        assertNull(projectView.projectUserId(), "ProjectUserId should be null when user set is empty");
    }

    @Test
    void shouldMapProjectWithProjectUserWithoutClient() {
        // Arrange
        ProjectUser projectUser = new ProjectUser();
        projectUser.setClient(null); // No client

        Set<ProjectUser> projectUsers = new HashSet<>();
        projectUsers.add(projectUser);

        Project project = new Project();
        project.setId(faker.number().randomNumber());
        project.setTitle(faker.lorem().word());
        project.setDescription(faker.lorem().sentence());
        project.setProgress(faker.number().numberBetween(0, 100));
        project.setStatus(faker.lorem().word());
        project.setUsers(projectUsers);

        // Act
        ProjectView projectView = projectViewMapper.map(project);

        // Assert
        assertNotNull(projectView, "ProjectView should not be null");
        assertEquals(project.getId(), projectView.id(), "Project ID should match");
        assertEquals(project.getTitle(), projectView.title(), "Project Title should match");
        assertEquals(project.getDescription(), projectView.description(), "Project Description should match");
        assertEquals(project.getProgress(), projectView.progress(), "Project Progress should match");
        assertEquals(project.getStatus(), projectView.status(), "Project Status should match");
        assertNull(projectView.projectUserId(), "ProjectUserId should be null when client is not present");
    }

    @Test
    void shouldMapProjectWithProjectUserWithClient() {
        // Arrange
        User clientUser = new User();
        clientUser.setId(faker.number().randomNumber());
        clientUser.setName(faker.name().fullName());
        clientUser.setEmail(faker.internet().emailAddress());

        ProjectUser projectUser = new ProjectUser();
        projectUser.setClient(clientUser); // Set client as User

        Set<ProjectUser> projectUsers = new HashSet<>();
        projectUsers.add(projectUser);

        Project project = new Project();
        project.setId(faker.number().randomNumber());
        project.setTitle(faker.lorem().word());
        project.setDescription(faker.lorem().sentence());
        project.setProgress(faker.number().numberBetween(0, 100));
        project.setStatus(faker.lorem().word());
        project.setUsers(projectUsers);

        // Act
        ProjectView projectView = projectViewMapper.map(project);

        // Assert
        assertNotNull(projectView, "ProjectView should not be null");
        assertEquals(project.getId(), projectView.id(), "Project ID should match");
        assertEquals(project.getTitle(), projectView.title(), "Project Title should match");
        assertEquals(project.getDescription(), projectView.description(), "Project Description should match");
        assertEquals(project.getProgress(), projectView.progress(), "Project Progress should match");
        assertEquals(project.getStatus(), projectView.status(), "Project Status should match");
        assertNotNull(projectView.projectUserId(), "ProjectUserId should not be null when client is present");
        assertEquals(clientUser.getId(), projectView.projectUserId(), "ProjectUserId should match client ID");
    }
}
