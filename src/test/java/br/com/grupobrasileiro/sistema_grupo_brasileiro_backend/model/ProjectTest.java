package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

public class ProjectTest {

    private final Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        // Any setup if necessary
    }

    @Test
    void testProjectCreation() {
        // Generate fake data using Faker
        Long id = faker.number().randomNumber();
        String title = faker.lorem().word();
        String description = faker.lorem().sentence();
        Integer progress = faker.number().numberBetween(0, 100);
        String status = faker.lorem().word();

        // Create a Project object with fake data
        Project project = new Project(
            id,
            title,
            description,
            progress,
            status,
            new HashSet<>()
        );

        // Verify that the fields are set correctly
        assertEquals(id, project.getId(), "ID should match.");
        assertEquals(title, project.getTitle(), "Title should match.");
        assertEquals(description, project.getDescription(), "Description should match.");
        assertEquals(progress, project.getProgress(), "Progress should match.");
        assertEquals(status, project.getStatus(), "Status should match.");
    }

    @Test
    void testEqualsAndHashCode() {
        // Generate fake data using Faker
        Long id = faker.number().randomNumber();
        String title = faker.lorem().word();
        String description = faker.lorem().sentence();
        Integer progress = faker.number().numberBetween(0, 100);
        String status = faker.lorem().word();

        // Create two Project objects with the same id
        Project project1 = new Project(id, title, description, progress, status, new HashSet<>());
        Project project2 = new Project(id, title, description, progress, status, new HashSet<>());

        // Test equals method
        assertEquals(project1, project2, "Projects with the same ID should be equal.");
        
        // Test hashCode method
        assertEquals(project1.hashCode(), project2.hashCode(), "Hash codes should match for projects with the same ID.");
    }

    @Test
    void testToString() {
        // Generate fake data using Faker
        Long id = faker.number().randomNumber();
        String title = faker.lorem().word();
        String description = faker.lorem().sentence();
        Integer progress = faker.number().numberBetween(0, 100);
        String status = faker.lorem().word();

        // Create a Project object
        Project project = new Project(id, title, description, progress, status, new HashSet<>());

        // Test toString method
        String expectedString = "Project{id=" + id + "}";
        assertEquals(expectedString, project.toString(), "toString() should return the expected string.");
    }
}
