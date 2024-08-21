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

public class ProjectViewMapperTest {

    private ProjectViewMapper projectViewMapper;
    private Faker faker;

    @BeforeEach
    void setUp() {
        projectViewMapper = new ProjectViewMapper();
        faker = new Faker();
    }

    @Test
    void testMap() {
        // Simulate the creation of Users for Client and Collaborator
        User client = new User();
        client.setName(faker.name().firstName());
        client.setLastname(faker.name().lastName());
        client.setPhonenumber(faker.phoneNumber().phoneNumber());
        client.setSector(faker.job().field());
        client.setOccupation(faker.job().title());
        client.setNop(faker.number().digits(5));
        client.setEmail(faker.internet().emailAddress());
        client.setPassword("dummyPassword"); // Use a placeholder for password
        client.setRole(RoleEnum.ROLE_CLIENT.getCode()); // Ensure the role is set

        User collaborator = new User();
        collaborator.setName(faker.name().firstName());
        collaborator.setLastname(faker.name().lastName());
        collaborator.setPhonenumber(faker.phoneNumber().phoneNumber());
        collaborator.setSector(faker.job().field());
        collaborator.setOccupation(faker.job().title());
        collaborator.setNop(faker.number().digits(5));
        collaborator.setEmail(faker.internet().emailAddress());
        collaborator.setPassword("dummyPassword"); // Use a placeholder for password
        collaborator.setRole(RoleEnum.ROLE_COLLABORATOR.getCode()); // Ensure the role is set

        // Simulate a ProjectUser
        ProjectUser projectUser = new ProjectUser();
        projectUser.setClient(client);
        projectUser.setCollaborator(collaborator);

        // Add ProjectUser to the project's set of users
        Set<ProjectUser> projectUsers = new HashSet<>();
        projectUsers.add(projectUser);

        // Simulate a Project for the test
        Project project = new Project();
        project.setId(faker.number().randomNumber());
        project.setTitle(faker.lorem().word());
        project.setDescription(faker.lorem().sentence());
        project.setProgress(faker.number().numberBetween(0, 100));
        project.setStatus(faker.lorem().word());
        project.setUsers(projectUsers);

        // Map the Project to ProjectView
        ProjectView projectView = projectViewMapper.map(project);

        // Validations
        assertNotNull(projectView, "ProjectView should not be null");
        assertEquals(project.getId(), projectView.id(), "IDs should match");
        assertEquals(project.getTitle(), projectView.title(), "Titles should match");
        assertEquals(project.getDescription(), projectView.description(), "Descriptions should match");
        assertEquals(project.getProgress(), projectView.progress(), "Progress should match");
        assertEquals(project.getStatus(), projectView.status(), "Status should match");
        assertEquals(1, projectView.users().size(), "UserViews size should match the number of users");
    }

    @Test
    void testMapWithNullUsers() {
        // Simulate a Project with null Users
        Project project = new Project();
        project.setId(faker.number().randomNumber());
        project.setTitle(faker.lorem().word());
        project.setDescription(faker.lorem().sentence());
        project.setProgress(faker.number().numberBetween(0, 100));
        project.setStatus(faker.lorem().word());
        project.setUsers(null); // Explicitly setting users to null

        // Map the Project to ProjectView
        ProjectView projectView = projectViewMapper.map(project);

        // Validations
        assertNotNull(projectView, "ProjectView should not be null");
        assertEquals(project.getId(), projectView.id(), "IDs should match");
        assertEquals(project.getTitle(), projectView.title(), "Titles should match");
        assertEquals(project.getDescription(), projectView.description(), "Descriptions should match");
        assertEquals(project.getProgress(), projectView.progress(), "Progress should match");
        assertEquals(project.getStatus(), projectView.status(), "Status should match");

        // If the method map() should handle null users, assert that the users view is empty
        assertNotNull(projectView.users(), "UserViews should not be null");
        assertTrue(projectView.users().isEmpty(), "UserViews should be empty when no users are present");
    }

    @Test
    void testMapWithLargeDataSet() {
        // Create a large set of ProjectUsers
        Set<ProjectUser> projectUsers = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            User client = new User();
            client.setName(faker.name().firstName());
            client.setLastname(faker.name().lastName());
            client.setPhonenumber(faker.phoneNumber().phoneNumber());
            client.setSector(faker.job().field());
            client.setOccupation(faker.job().title());
            client.setNop(faker.number().digits(5));
            client.setEmail(faker.internet().emailAddress());
            client.setPassword("dummyPassword"); // Use a placeholder for password
            client.setRole(RoleEnum.ROLE_CLIENT.getCode()); // Ensure the role is set

            User collaborator = new User();
            collaborator.setName(faker.name().firstName());
            collaborator.setLastname(faker.name().lastName());
            collaborator.setPhonenumber(faker.phoneNumber().phoneNumber());
            collaborator.setSector(faker.job().field());
            collaborator.setOccupation(faker.job().title());
            collaborator.setNop(faker.number().digits(5));
            collaborator.setEmail(faker.internet().emailAddress());
            collaborator.setPassword("dummyPassword"); // Use a placeholder for password
            collaborator.setRole(RoleEnum.ROLE_COLLABORATOR.getCode()); // Ensure the role is set

            ProjectUser projectUser = new ProjectUser();
            projectUser.setClient(client);
            projectUser.setCollaborator(collaborator);

            projectUsers.add(projectUser);
        }

        // Create a Project with the large set of users
        Project project = new Project();
        project.setId(faker.number().randomNumber());
        project.setTitle(faker.lorem().word());
        project.setDescription(faker.lorem().sentence());
        project.setProgress(faker.number().numberBetween(0, 100));
        project.setStatus(faker.lorem().word());
        project.setUsers(projectUsers);

        long startTime = System.currentTimeMillis();
        // Map the Project to ProjectView
        ProjectView projectView = projectViewMapper.map(project);
        long endTime = System.currentTimeMillis();

        // Validate execution time
        long duration = endTime - startTime;
        assertTrue(duration < 2000, "Mapping should complete in less than 2 seconds");

        // Validations (simplified for this example)
        assertNotNull(projectView, "ProjectView should not be null");
        assertEquals(1000, projectView.users().size(), "UserViews size should match the number of users");
    }
}

