package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.RoleEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.UserViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.ProjectUser;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import java.util.HashSet;
import java.util.Set;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;

public class ProjectViewMapperTest {

    private ProjectViewMapper projectViewMapper;
    private Faker faker;

    @BeforeEach
    void setUp() {
        projectViewMapper = new ProjectViewMapper();
        faker = new Faker();
    }

    @Test
    void shouldMapProjectToProjectViewWithValidData() {
        // Arrange
        User client = new User();
        client.setId(faker.number().randomNumber());
        client.setName(faker.name().firstName());
        client.setLastname(faker.name().lastName());
        client.setPhonenumber(faker.phoneNumber().phoneNumber());
        client.setSector(faker.job().field());
        client.setOccupation(faker.job().title());
        client.setNop(faker.number().digits(5));
        client.setEmail(faker.internet().emailAddress());
        client.setPassword("dummyPassword");
        client.setRole(RoleEnum.ROLE_CLIENT.getCode());

        ProjectUser projectUser = new ProjectUser();
        projectUser.setClient(client);

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
        assertNotNull(projectView.projectUserId(), "ProjectUserId should not be null");
    }

    @Test
    void shouldMapProjectWithNullUsers() {
        // Arrange
        Project project = new Project();
        project.setId(faker.number().randomNumber());
        project.setTitle(faker.lorem().word());
        project.setDescription(faker.lorem().sentence());
        project.setProgress(faker.number().numberBetween(0, 100));
        project.setStatus(faker.lorem().word());
        project.setUsers(null);

        // Act
        ProjectView projectView = projectViewMapper.map(project);

        // Assert
        assertNotNull(projectView, "ProjectView should not be null");
        assertEquals(project.getId(), projectView.id(), "Project ID should match");
        assertEquals(project.getTitle(), projectView.title(), "Project Title should match");
        assertEquals(project.getDescription(), projectView.description(), "Project Description should match");
        assertEquals(project.getProgress(), projectView.progress(), "Project Progress should match");
        assertEquals(project.getStatus(), projectView.status(), "Project Status should match");
        assertNull(projectView.projectUserId(), "ProjectUserId should be null when user set is null");
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
    void shouldMapProjectWithMultipleProjectUsersAndClientInTheMiddle() {
        // Arrange
        User client1 = new User();
        client1.setId(faker.number().randomNumber());
        client1.setName(faker.name().firstName());
        client1.setLastname(faker.name().lastName());
        client1.setPhonenumber(faker.phoneNumber().phoneNumber());
        client1.setSector(faker.job().field());
        client1.setOccupation(faker.job().title());
        client1.setNop(faker.number().digits(5));
        client1.setEmail(faker.internet().emailAddress());
        client1.setPassword("dummyPassword");
        client1.setRole(RoleEnum.ROLE_CLIENT.getCode());

        User client2 = new User();
        client2.setId(faker.number().randomNumber());
        client2.setName(faker.name().firstName());
        client2.setLastname(faker.name().lastName());
        client2.setPhonenumber(faker.phoneNumber().phoneNumber());
        client2.setSector(faker.job().field());
        client2.setOccupation(faker.job().title());
        client2.setNop(faker.number().digits(5));
        client2.setEmail(faker.internet().emailAddress());
        client2.setPassword("dummyPassword");
        client2.setRole(RoleEnum.ROLE_CLIENT.getCode());

        ProjectUser projectUser1 = new ProjectUser();
        projectUser1.setClient(client1);

        ProjectUser projectUser2 = new ProjectUser();
        projectUser2.setClient(client2);

        Set<ProjectUser> projectUsers = new HashSet<>();
        projectUsers.add(projectUser1);
        projectUsers.add(projectUser2);

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
        assertNotNull(projectView.projectUserId(), "ProjectUserId should not be null when there are multiple ProjectUsers");
    }

    @Test
    void shouldHandleLargeDataSetWithoutPerformanceIssues() {
        // Arrange
        Set<ProjectUser> projectUsers = new HashSet<>();
        User client = new User();
        client.setId(faker.number().randomNumber());
        client.setName(faker.name().firstName());
        client.setLastname(faker.name().lastName());
        client.setPhonenumber(faker.phoneNumber().phoneNumber());
        client.setSector(faker.job().field());
        client.setOccupation(faker.job().title());
        client.setNop(faker.number().digits(5));
        client.setEmail(faker.internet().emailAddress());
        client.setPassword("dummyPassword");
        client.setRole(RoleEnum.ROLE_CLIENT.getCode());

        for (int i = 0; i < 1000; i++) {
            ProjectUser projectUser = new ProjectUser();
            projectUser.setClient(client);
            projectUsers.add(projectUser);
        }

        Project project = new Project();
        project.setId(faker.number().randomNumber());
        project.setTitle(faker.lorem().word());
        project.setDescription(faker.lorem().sentence());
        project.setProgress(faker.number().numberBetween(0, 100));
        project.setStatus(faker.lorem().word());
        project.setUsers(projectUsers);

        // Act
        long startTime = System.currentTimeMillis();
        ProjectView projectView = projectViewMapper.map(project);
        long endTime = System.currentTimeMillis();

        // Assert
        assertNotNull(projectView, "ProjectView should not be null");
        assertTrue(endTime - startTime < 2000, "Mapping should be completed within acceptable time limit"); // Example time limit
    }
}
