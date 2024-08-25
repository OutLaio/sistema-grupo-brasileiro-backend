package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class ProjectUserTest {

    private ProjectUser projectUser;
    private Project project;
    private User client;
    private User collaborator;

    @BeforeEach
    void setUp() {
        // Create real objects instead of mocks for testing
        project = new Project();
        client = new User();
        collaborator = new User();

        // Initialize ProjectUser instance
        projectUser = new ProjectUser(project, client);
    }

    @Test
    void testConstructorAndGetters() {
        // Set IDs to ensure they are consistent
        project.setId(1L);
        client.setId(2L);

        // Act
        ProjectUser projectUser = new ProjectUser(project, client);

        // Assert
        assertEquals(project, projectUser.getProject(), "The project should be correctly assigned");
        assertEquals(client, projectUser.getClient(), "The client should be correctly assigned");
        assertNull(projectUser.getCollaborator(), "The collaborator should be null by default");
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        ProjectUser anotherProjectUser = new ProjectUser(project, client);
        ProjectUser differentProjectUser = new ProjectUser(new Project(), new User());

        // Act & Assert
        assertEquals(projectUser, anotherProjectUser, "ProjectUser instances with the same project and client should be equal");
        assertNotEquals(projectUser, differentProjectUser, "ProjectUser instances with different projects or clients should not be equal");
        assertEquals(projectUser.hashCode(), anotherProjectUser.hashCode(), "Hash codes should be equal for instances with the same project and client");
    }

    @Test
    void testToString() {
        // Set IDs to ensure the string representation is accurate
        project.setId(1L);
        client.setId(2L);

        // Act
        String expectedString = "ProjectUser{project=1, client=2}";
        String actualString = projectUser.toString();

        // Assert
        assertEquals(expectedString, actualString, "The toString method should return the expected string representation");
    }
}
