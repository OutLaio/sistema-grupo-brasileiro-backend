package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.UserDetailsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeView; // Importando EmployeeView
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee; // Importando Employee
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view.EmployeeViewMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserDetailsViewMapperTest {

    @InjectMocks
    private UserDetailsViewMapper userDetailsViewMapper;

    @Mock
    private EmployeeViewMapper employeeViewMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa o mapeamento de User para UserDetailsView.
     * Verifica se todos os campos são mapeados corretamente.
     */
    @Test
    @DisplayName("Should map User to UserDetailsView correctly")
    void mapUserToUserDetailsViewCorrectly() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        
        // Criando um Employee usando o construtor padrão
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setLastName("Doe");
        employee.setPhoneNumber("123456789");
        employee.setSector("IT");
        employee.setOccupation("Developer");
        employee.setAgency("Main Agency");
        employee.setAvatar(1L);
        user.setEmployee(employee);

        // Criando um EmployeeView correspondente
        EmployeeView employeeView = new EmployeeView(1L, null, "John", "Doe", "123456789", "IT", "Developer", "Main Agency", "123456", 1L);
        when(employeeViewMapper.map(employee)).thenReturn(employeeView);

        // Act
        UserDetailsView userDetailsView = userDetailsViewMapper.map(user);

        // Assert
        assertEquals(userId, userDetailsView.id(), "UserDetailsView ID should match");
        assertEquals(employeeView, userDetailsView.employee(), "UserDetailsView EmployeeView should match");
    }
}