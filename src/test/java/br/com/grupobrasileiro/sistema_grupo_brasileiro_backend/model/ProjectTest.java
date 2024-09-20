//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
//import com.github.javafaker.Faker;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.HashSet;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ProjectTest {
//
//    private final Faker faker = new Faker();
//
//    @BeforeEach
//    void setUp() {
//        // Any setup if necessary
//    }
//
//    @Test
//    void testProjectCreation() {
//        // Generate fake data using Faker
//        Long id = faker.number().randomNumber();
//        String title = faker.lorem().word();
//        String description = faker.lorem().sentence();
//        Integer progress = faker.number().numberBetween(0, 100);
//        String status = faker.lorem().word();
//
//        // Create a Project object with fake data
//        Project project = new Project(
//            id,
//            title,
//            description,
//            progress,
//            status,
//            new HashSet<>()
//        );
//
//        // Verify that the fields are set correctly
//        assertEquals(id, project.getId(), "ID should match.");
//        assertEquals(title, project.getTitle(), "Title should match.");
//        assertEquals(description, project.getDescription(), "Description should match.");
//        assertEquals(progress, project.getProgress(), "Progress should match.");
//        assertEquals(status, project.getStatus(), "Status should match.");
//    }
//
//    @Test
//    void testEqualsAndHashCode() {
//        // Generate fake data using Faker
//        Long id = faker.number().randomNumber();
//        String title = faker.lorem().word();
//        String description = faker.lorem().sentence();
//        Integer progress = faker.number().numberBetween(0, 100);
//        String status = faker.lorem().word();
//
//        // Create two Project objects with the same attributes
//        Project project1 = new Project(id, title, description, progress, status, new HashSet<>());
//        Project project2 = new Project(id, title, description, progress, status, new HashSet<>());
//
//        // Test equals method
//        assertEquals(project1, project2, "Projects with the same attributes should be equal.");
//
//        // Test hashCode method
//        assertEquals(project1.hashCode(), project2.hashCode(), "Hash codes should match for projects with the same attributes.");
//    }
//
//    @Test
//    void testToString() {
//        // Generate fake data using Faker
//        Long id = faker.number().randomNumber();
//        String title = faker.lorem().word();
//        String description = faker.lorem().sentence();
//        Integer progress = faker.number().numberBetween(0, 100);
//        String status = faker.lorem().word();
//
//        // Create a Project object
//        Project project = new Project(id, title, description, progress, status, new HashSet<>());
//
//        // Test toString method
//        String expectedString = "Project{id=" + id + ", title=" + title + ", description=" + description + ", progress=" + progress + ", status=" + status + "}";
//        assertEquals(expectedString, project.toString(), "toString() should return the expected string.");
//    }
//
//    @Test
//    void testProjectWithEmptyUsers() {
//        // Create a Project object with an empty set of users
//        Project project = new Project(
//            faker.number().randomNumber(),
//            faker.lorem().word(),
//            faker.lorem().sentence(),
//            faker.number().numberBetween(0, 100),
//            faker.lorem().word(),
//            new HashSet<>()
//        );
//
//        // Verify that the users set is empty
//        assertTrue(project.getUsers().isEmpty(), "Users set should be empty.");
//    }
//
//    @Test
//    void testProjectWithNullUsers() {
//        // Create a Project object with null users
//        Project project = new Project(
//            faker.number().randomNumber(),
//            faker.lorem().word(),
//            faker.lorem().sentence(),
//            faker.number().numberBetween(0, 100),
//            faker.lorem().word(),
//            null
//        );
//
//        // Verify that the users set is null
//        assertNull(project.getUsers(), "Users set should be null.");
//    }
//
//    @Test
//    void testProjectWithNullFields() {
//        // Create a Project object with null values for optional fields
//        Project project = new Project(
//            null,
//            null,
//            null,
//            null,
//            null,
//            null
//        );
//
//        // Check if fields are set to null
//        assertNull(project.getId(), "ID should be null.");
//        assertNull(project.getTitle(), "Title should be null.");
//        assertNull(project.getDescription(), "Description should be null.");
//        assertNull(project.getProgress(), "Progress should be null.");
//        assertNull(project.getStatus(), "Status should be null.");
//        assertNull(project.getUsers(), "Users set should be null.");
//    }
//}
