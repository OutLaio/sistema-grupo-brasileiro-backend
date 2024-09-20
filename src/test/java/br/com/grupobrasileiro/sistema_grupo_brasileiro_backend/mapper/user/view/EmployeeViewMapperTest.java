package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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

public class EmployeeViewMapperTest {

    @InjectMocks
    private EmployeeViewMapper employeeViewMapper;

    @Mock
    private UserViewMapper userViewMapper;

    /**
     * Testa o mapeamento de Employee para EmployeeView.
     * Verifica se todos os campos são mapeados corretamente.
     */
    @Test
    @DisplayName("Should map Employee to EmployeeView correctly")
    void shouldMapEmployeeToEmployeeViewCorrectly() {
        // Arrange
        UserView userViewMock = new UserView(1L, "user@example.com", null); // Mock de UserView

        // Ajustar o construtor de User conforme necessário
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
        assertEquals(userViewMock, employeeView.userView(), "EmployeeView UserView should match");
        assertEquals(employee.getName(), employeeView.name(), "EmployeeView name should match");
        assertEquals(employee.getLastName(), employeeView.lastname(), "EmployeeView lastname should match");
        assertEquals(employee.getPhoneNumber(), employeeView.phonenumber(), "EmployeeView phoneNumber should match");
        assertEquals(employee.getSector(), employeeView.sector(), "EmployeeView sector should match");
        assertEquals(employee.getOccupation(), employeeView.occupation(), "EmployeeView occupation should match");
        assertEquals(employee.getAgency(), employeeView.agency(), "EmployeeView agency should match");
        assertEquals(employee.getAvatar(), employeeView.avatar(), "EmployeeView avatar should match");
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}
