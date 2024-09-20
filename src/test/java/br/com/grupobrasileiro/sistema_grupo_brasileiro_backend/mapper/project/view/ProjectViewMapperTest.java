package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view;

import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view.EmployeeSimpleViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectViewMapperTest {

    private ProjectViewMapper projectViewMapper;
    private EmployeeSimpleViewMapper employeeSimpleViewMapper;
    private Faker faker;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        faker = new Faker();
        employeeSimpleViewMapper = mock(EmployeeSimpleViewMapper.class);
        projectViewMapper = new ProjectViewMapper();
        
        // Usando reflexão para injetar o mock
        Field field = ProjectViewMapper.class.getDeclaredField("employeeMapperSimpleView");
        field.setAccessible(true);
        field.set(projectViewMapper, employeeSimpleViewMapper);
    }


    /**
     * Testa o método map do ProjectViewMapper.
     * Verifica se ele mapeia um projeto com cliente e colaborador.
     */
    @Test
    @DisplayName("Should map project successfully")
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
        assertNotNull(result.client(), "Project client should not be null");
        assertEquals(clientView.id(), result.client().id(), "Project client ID should match");
        assertNotNull(result.collaborator(), "Project collaborator should not be null");
        assertEquals(collaboratorView.id(), result.collaborator().id(), "Project collaborator ID should match");
        assertEquals("Project Title", result.title(), "Project title should match");
        assertEquals("ACTIVE", result.status(), "Project status should match");
    }


    /**
     * Testa o método map do ProjectViewMapper.
     * Verifica se ele mapeia um projeto com cliente nulo.
     */
    @Test
    @DisplayName("Should map project with null client")
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
        assertNull(result.client(), "Project client should be null");
        assertNotNull(result.collaborator(), "Project collaborator should not be null");
        assertEquals(collaboratorView.id(), result.collaborator().id(), "Project collaborator ID should match");
        assertEquals("Project Title", result.title(), "Project title should match");
        assertEquals("ACTIVE", result.status(), "Project status should match");
       // assertFalse(result.disabled(), "Project should not be marked as disabled");
    }

    /**
     * Testa o método map do ProjectViewMapper.
     * Verifica se ele mapeia um projeto com colaborador nulo.
     */
    @Test
    @DisplayName("Should map project with null collaborator")
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
        assertNotNull(result.client(), "Project client should not be null");
        assertEquals(clientView.id(), result.client().id(), "Project client ID should match");
        assertNull(result.collaborator(), "Project collaborator should be null");
        assertEquals("Project Title", result.title(), "Project title should match");
        assertEquals("ACTIVE", result.status(), "Project status should match");
       // assertFalse(result.disabled(), "Project should not be marked as disabled");
    }

    /**
     * Testa o método map do ProjectViewMapper.
     * Verifica se ele mapeia um projeto com cliente e colaborador nulos.
     */
    @Test
    @DisplayName("Should map project with null client and collaborator")
    void shouldMapProjectWithNullClientAndCollaborator() {
        // Arrange
        Project project = new Project(null, null, null, "Project Title", "ACTIVE", false, null);

        // Act
        ProjectView result = projectViewMapper.map(project);

        // Assert
        assertNotNull(result, "Mapped Project should not be null");
        assertNull(result.client(), "Project client should be null");
        assertNull(result.collaborator(), "Project collaborator should be null");
        assertEquals("Project Title", result.title(), "Project title should match");
        assertEquals("ACTIVE", result.status(), "Project status should match");
      //  assertFalse(result.disabled(), "Project should not be marked as disabled");
    }

    /**
     * Testa o método map do ProjectViewMapper.
     * Verifica se ele mapeia corretamente um projeto válido.
     */
    @Test
    @DisplayName("Should map valid project correctly")
    void shouldMapValidProject() {
        // Arrange
        Project project = new Project(1L, null, null, "Valid Project", "ACTIVE", false, null);
        
        // Act
        ProjectView result = projectViewMapper.map(project);
        
        // Assert
        assertNotNull(result, "Mapped Project should not be null");
        assertEquals("Valid Project", result.title(), "Project title should match");
        assertEquals("ACTIVE", result.status(), "Project status should match");
    }
}
