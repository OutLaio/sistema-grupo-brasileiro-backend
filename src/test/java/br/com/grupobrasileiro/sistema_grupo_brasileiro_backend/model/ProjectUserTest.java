package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        // Mock objects for Project and User
        project = mock(Project.class);
        client = mock(User.class);
        collaborator = mock(User.class);
        
        // Create a new ProjectUser instance
        projectUser = new ProjectUser(project, client);
    }

    @Test
    void testConstructorAndGetters() {
        // Arrange
        when(project.getId()).thenReturn(1L);

        // Act
        ProjectUser projectUser = new ProjectUser(project, client);

        // Assert
        assertEquals(project, projectUser.getProject(), "The project should be correctly assigned");
        assertEquals(client, projectUser.getClient(), "The client should be correctly assigned");
        assertEquals(null, projectUser.getCollaborator(), "The collaborator should be null by default");
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        ProjectUser anotherProjectUser = new ProjectUser(project, client);
        ProjectUser differentProjectUser = new ProjectUser(mock(Project.class), mock(User.class));

        // Act & Assert
        assertEquals(projectUser, anotherProjectUser, "ProjectUser instances with the same ID should be equal");
        assertNotEquals(projectUser, differentProjectUser, "ProjectUser instances with different IDs should not be equal");
        assertEquals(projectUser.hashCode(), anotherProjectUser.hashCode(), "Hash codes should be equal for instances with the same ID");
    }

    @Test
    void testToString() {
        // Arrange
        when(project.getId()).thenReturn(1L);

        // Act
        String expectedString = "ProjectUser{id=null, project=1}";
        String actualString = projectUser.toString();

        // Assert
        assertEquals(expectedString, actualString, "The toString method should return the expected string representation");
    }
}
