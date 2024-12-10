package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class EmployeeViewMapperTest {

    @InjectMocks
    private EmployeeViewMapper employeeViewMapper;

    @Mock
    private UserViewMapper userViewMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa o mapeamento de Employee para EmployeeView.
     * Verifica se todos os campos são mapeados corretamente.
     */
    @Test
    @DisplayName("Should map Employee to EmployeeView correctly")
    void mapEmployeeToEmployeeViewCorrectly() {
        // Arrange
        UserView userViewMock = new UserView(1L, "user@example.com", null); // Mock de UserView

        User user = new User(
            1L, // id
            null, // profile
            "user@example.com", // email
            "password", // password
            false, // isDeleted
            null // outros campos, se necessário
        );

        Employee employee = new Employee(
            1L, // id
            "John", // name
            "Doe", // lastName
            "123456789", // phoneNumber
            "IT", // sector
            "Developer", // occupation
            "Main Agency", // agency
            "123456",     // registrationNumber
            1L, // avatar
            user, // User
            null, // ownedProjects
            null, // assignedProjects
            null  // dialogs
        );

        when(userViewMapper.map(user)).thenReturn(userViewMock);

        // Act
        EmployeeView employeeView = employeeViewMapper.map(employee);

        // Assert
        assertEquals(employee.getId(), employeeView.id(), "EmployeeView ID should match");
        assertEquals(userViewMock, employeeView.user(), "EmployeeView UserView should match");
        assertEquals(employee.getName(), employeeView.name(), "EmployeeView name should match");
        assertEquals(employee.getLastName(), employeeView.lastname(), "EmployeeView lastname should match");
        assertEquals(employee.getPhoneNumber(), employeeView.phoneNumber(), "EmployeeView phoneNumber should match");
        assertEquals(employee.getSector(), employeeView.sector(), "EmployeeView sector should match");
        assertEquals(employee.getOccupation(), employeeView.occupation(), "EmployeeView occupation should match");
        assertEquals(employee.getAgency(), employeeView.agency(), "EmployeeView agency should match");
        assertEquals(employee.getAvatar(), employeeView.avatar(), "EmployeeView avatar should match");
    }

    /**
     * Testa o mapeamento quando campos opcionais são nulos.
     * Verifica se o resultado não lança exceções e mapeia corretamente os campos.
     */
    @Test
    @DisplayName("Should handle null fields in Employee correctly")
    void handleNullFieldsInEmployee() {
        // Arrange
        UserView userViewMock = new UserView(1L, "user@example.com", null);

        User user = new User(
            1L, // id
            null, // profile
            "user@example.com", // email
            "password", // password
            false, // isDeleted
            null // outros campos, se necessário
        );

        Employee employee = new Employee(
            2L, // id
            null, // name (nulo)
            null, // lastName (nulo)
            null, // phoneNumber (nulo)
            null, // sector (nulo)
            null, // occupation (nulo)
            null, // agency (nulo)
            null,
            null, // avatar (nulo)
            user, // User
            null, // ownedProjects
            null, // assignedProjects
            null  // dialogs
        );

        when(userViewMapper.map(user)).thenReturn(userViewMock);

        // Act
        EmployeeView employeeView = employeeViewMapper.map(employee);

        // Assert
        assertNotNull(employeeView, "EmployeeView should not be null");
        assertEquals(2L, employeeView.id(), "EmployeeView ID should match");
        assertEquals(userViewMock, employeeView.user(), "EmployeeView UserView should match");
        
        // Usando assertEquals para evitar ambiguidade
        assertEquals(null, employeeView.name(), "EmployeeView name should be null");
        assertEquals(null, employeeView.lastname(), "EmployeeView lastname should be null");
        assertEquals(null, employeeView.phoneNumber(), "EmployeeView phoneNumber should be null");
        assertEquals(null, employeeView.sector(), "EmployeeView sector should be null");
        assertEquals(null, employeeView.occupation(), "EmployeeView occupation should be null");
        assertEquals(null, employeeView.agency(), "EmployeeView agency should be null");
        assertEquals(null, employeeView.avatar(), "EmployeeView avatar should be null");
    }

    /**
     * Testa o mapeamento quando campos opcionais estão vazios.
     * Verifica se o resultado não lança exceções e mapeia corretamente os campos.
     */
    @Test
    @DisplayName("Should handle empty fields in Employee correctly")
    void handleEmptyFieldsInEmployee() {
        // Arrange
        UserView userViewMock = new UserView(1L, "user@example.com", null);

        User user = new User(
            1L, // id
            null, // profile
            "user@example.com", // email
            "password", // password
            false, // isDeleted
            null // outros campos, se necessário
        );

        Employee employee = new Employee(
            3L, // id
            "", // name (vazio)
            "", // lastName (vazio)
            "", // phoneNumber (vazio)
            "", // sector (vazio)
            "", // occupation (vazio)
            "", // agency (vazio)
            "", // registrationNumber
            null, // avatar (nulo)
            user, // User
            null, // ownedProjects
            null, // assignedProjects
            null  // dialogs
        );

        when(userViewMapper.map(user)).thenReturn(userViewMock);

        // Act
        EmployeeView employeeView = employeeViewMapper.map(employee);

        // Assert
        assertEquals(3L, employeeView.id(), "EmployeeView ID should match");
        assertEquals(userViewMock, employeeView.user(), "EmployeeView UserView should match");
        assertEquals("", employeeView.name(), "EmployeeView name should be empty for empty name");
        assertEquals("", employeeView.lastname(), "EmployeeView lastname should be empty for empty last name");
        assertEquals("", employeeView.phoneNumber(), "EmployeeView phoneNumber should be empty for empty phone number");
        assertEquals("", employeeView.sector(), "EmployeeView sector should be empty for empty sector");
        assertEquals("", employeeView.occupation(), "EmployeeView occupation should be empty for empty occupation");
        assertEquals("", employeeView.agency(), "EmployeeView agency should be empty for empty agency");
        assertEquals(null, employeeView.avatar(), "EmployeeView avatar should be null");
    }
}