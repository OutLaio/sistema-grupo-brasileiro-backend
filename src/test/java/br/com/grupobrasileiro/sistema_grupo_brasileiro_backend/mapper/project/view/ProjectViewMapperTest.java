package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view.EmployeeSimpleViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectViewMapperTest {

    private ProjectViewMapper projectViewMapper;
    private EmployeeSimpleViewMapper employeeSimpleViewMapper;
    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
        employeeSimpleViewMapper = mock(EmployeeSimpleViewMapper.class);
        projectViewMapper = new ProjectViewMapper();
        projectViewMapper.setEmployeeMapperSimpleView(employeeSimpleViewMapper); // Assumindo que você tenha um método setter
    }

    @Test
    void shouldMapProjectSuccessfully() {
        // Arrange
        User clientUser = new User();
        clientUser.setId(1L);
        clientUser.setEmail(faker.internet().emailAddress());
        clientUser.setPassword(faker.internet().password());
        clientUser.setDisabled(false);

        User collaboratorUser = new User();
        collaboratorUser.setId(2L);
        collaboratorUser.setEmail(faker.internet().emailAddress());
        collaboratorUser.setPassword(faker.internet().password());
        collaboratorUser.setDisabled(false);

        Employee client = new Employee(1L, faker.name().firstName(), faker.name().lastName(), "123456789", "IT", "Developer", "Agency", 1L, clientUser, new HashSet<>(), new HashSet<>(), new HashSet<>());
        Employee collaborator = new Employee(2L, faker.name().firstName(), faker.name().lastName(), "987654321", "HR", "Manager", "Agency", 2L, collaboratorUser, new HashSet<>(), new HashSet<>(), new HashSet<>());
        Project project = new Project(null, collaborator, client, "Project Title", "ACTIVE", false, null);

        EmployeeSimpleView clientView = new EmployeeSimpleView(1L, "John Doe", 1L);
        EmployeeSimpleView collaboratorView = new EmployeeSimpleView(2L, "Jane Smith", 2L);

        when(employeeSimpleViewMapper.map(client)).thenReturn(clientView);
        when(employeeSimpleViewMapper.map(collaborator)).thenReturn(collaboratorView);

        // Act
        ProjectView result = projectViewMapper.map(project);

        // Assert
        assertNotNull(result, "Mapped Project should not be null");
        assertNotNull(result.getClient(), "Project client should not be null");
        assertEquals(clientView.getId(), result.getClient().getId(), "Project client ID should match");
        assertNotNull(result.getCollaborator(), "Project collaborator should not be null");
        assertEquals(collaboratorView.getId(), result.getCollaborator().getId(), "Project collaborator ID should match");
        assertEquals("Project Title", result.getTitle(), "Project title should match");
        assertEquals("ACTIVE", result.getStatus(), "Project status should match");
        assertFalse(result.isDisabled(), "Project should not be marked as disabled");
    }

    @Test
    void shouldMapProjectWithNullClient() {
        // Arrange
        User collaboratorUser = new User();
        collaboratorUser.setId(2L);
        collaboratorUser.setEmail(faker.internet().emailAddress());
        collaboratorUser.setPassword(faker.internet().password());
        collaboratorUser.setDisabled(false);

        Employee collaborator = new Employee(2L, faker.name().firstName(), faker.name().lastName(), "987654321", "HR", "Manager", "Agency", 2L, collaboratorUser, new HashSet<>(), new HashSet<>(), new HashSet<>());
        Project project = new Project(null, collaborator, null, "Project Title", "ACTIVE", false, null);

        EmployeeSimpleView collaboratorView = new EmployeeSimpleView(2L, "Jane Smith", 2L);

        when(employeeSimpleViewMapper.map(collaborator)).thenReturn(collaboratorView);

        // Act
        ProjectView result = projectViewMapper.map(project);

        // Assert
        assertNotNull(result, "Mapped Project should not be null");
        assertNull(result.getClient(), "Project client should be null");
        assertNotNull(result.getCollaborator(), "Project collaborator should not be null");
        assertEquals(collaboratorView.getId(), result.getCollaborator().getId(), "Project collaborator ID should match");
        assertEquals("Project Title", result.getTitle(), "Project title should match");
        assertEquals("ACTIVE", result.getStatus(), "Project status should match");
        assertFalse(result.isDisabled(), "Project should not be marked as disabled");
    }

    @Test
    void shouldMapProjectWithNullCollaborator() {
        // Arrange
        User clientUser = new User();
        clientUser.setId(1L);
        clientUser.setEmail(faker.internet().emailAddress());
        clientUser.setPassword(faker.internet().password());
        clientUser.setDisabled(false);

        Employee client = new Employee(1L, faker.name().firstName(), faker.name().lastName(), "123456789", "IT", "Developer", "Agency", 1L, clientUser, new HashSet<>(), new HashSet<>(), new HashSet<>());
        Project project = new Project(null, null, client, "Project Title", "ACTIVE", false, null);

        EmployeeSimpleView clientView = new EmployeeSimpleView(1L, "John Doe", 1L);

        when(employeeSimpleViewMapper.map(client)).thenReturn(clientView);

        // Act
        ProjectView result = projectViewMapper.map(project);

        // Assert
        assertNotNull(result, "Mapped Project should not be null");
        assertNotNull(result.getClient(), "Project client should not be null");
        assertEquals(clientView.getId(), result.getClient().getId(), "Project client ID should match");
        assertNull(result.getCollaborator(), "Project collaborator should be null");
        assertEquals("Project Title", result.getTitle(), "Project title should match");
        assertEquals("ACTIVE", result.getStatus(), "Project status should match");
        assertFalse(result.isDisabled(), "Project should not be marked as disabled");
    }

    @Test
    void shouldMapProjectWithNullClientAndCollaborator() {
        // Arrange
        Project project = new Project(null, null, null, "Project Title", "ACTIVE", false, null);

        // Act
        ProjectView result = projectViewMapper.map(project);

        // Assert
        assertNotNull(result, "Mapped Project should not be null");
        assertNull(result.getClient(), "Project client should be null");
        assertNull(result.getCollaborator(), "Project collaborator should be null");
        assertEquals("Project Title", result.getTitle(), "Project title should match");
        assertEquals("ACTIVE", result.getStatus(), "Project status should match");
        assertFalse(result.isDisabled(), "Project should not be marked as disabled");
    }

    @Test
    void shouldThrowExceptionWhenMappingNullProject() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> projectViewMapper.map(null), "Mapping should throw an exception for null project");
    }
}
