package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.form;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.EmployeeForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;

class EmployeeFormMapperTest {

    private EmployeeFormMapper employeeFormMapper;

    @BeforeEach
    void setUp() {
        employeeFormMapper = new EmployeeFormMapper();
    }

    /**
     * Testa o mapeamento de EmployeeForm para Employee.
     * Verifica se todos os campos são mapeados corretamente.
     */
    @Test
    @DisplayName("Should map EmployeeForm to Employee with null values")
    void shouldMapEmployeeFormToEmployeeWithNullValues() {
        // Arrange
        EmployeeForm employeeForm = new EmployeeForm(
                "John",
                "Doe",
                "123456789",
                "IT",
                "Developer",
                "Agency",
                1
        );

        // Act
        Employee result = employeeFormMapper.map(employeeForm);

        // Assert
        assertNotNull(result, "Mapped Employee should not be null");
        assertNull(result.getName(), "Employee name should be null due to missing mapping");
        assertNull(result.getLastName(), "Employee last name should be null due to missing mapping");
        assertNull(result.getPhoneNumber(), "Employee phone number should be null due to missing mapping");
        assertNull(result.getSector(), "Employee sector should be null due to missing mapping");
        assertNull(result.getOccupation(), "Employee occupation should be null due to missing mapping");
        assertNull(result.getAgency(), "Employee agency should be null due to missing mapping");
        assertNull(result.getAvatar(), "Employee avatar should be null due to missing mapping");
        assertNull(result.getUser(), "Employee user should be null due to missing mapping");
        assertTrue(result.getOwnedProjects().isEmpty(), "Employee should have no owned projects");
        assertTrue(result.getAssignedProjects().isEmpty(), "Employee should have no assigned projects");
        assertTrue(result.getDialogs().isEmpty(), "Employee should have no dialogs");
    }

    /**
     * Testa o mapeamento de EmployeeForm com campos nulos.
     * Verifica se o mapeamento de valores nulos é tratado corretamente.
     */
    @Test
    @DisplayName("Should handle null values in EmployeeForm")
    void shouldHandleNullValuesInEmployeeForm() {
        // Arrange
        EmployeeForm employeeForm = new EmployeeForm(
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        // Act
        Employee result = employeeFormMapper.map(employeeForm);

        // Assert
        assertNotNull(result, "Mapped Employee should not be null");
        assertNull(result.getName(), "Employee name should be null");
        assertNull(result.getLastName(), "Employee last name should be null");
        assertNull(result.getPhoneNumber(), "Employee phone number should be null");
        assertNull(result.getSector(), "Employee sector should be null");
        assertNull(result.getOccupation(), "Employee occupation should be null");
        assertNull(result.getAgency(), "Employee agency should be null");
        assertNull(result.getAvatar(), "Employee avatar should be null");
        assertNull(result.getUser(), "Employee user should be null");
        assertTrue(result.getOwnedProjects().isEmpty(), "Employee should have no owned projects");
        assertTrue(result.getAssignedProjects().isEmpty(), "Employee should have no assigned projects");
        assertTrue(result.getDialogs().isEmpty(), "Employee should have no dialogs");
    }

    /**
     * Testa o mapeamento de EmployeeForm com valores incompletos.
     * Verifica o comportamento quando alguns campos são preenchidos e outros nulos.
     */
    @Test
    @DisplayName("Should map partially filled EmployeeForm to Employee")
    void shouldMapPartiallyFilledEmployeeFormToEmployee() {
        // Arrange
        EmployeeForm employeeForm = new EmployeeForm(
                "Jane",
                null,  // Sobrenome nulo
                "987654321",
                null,  // Setor nulo
                "Designer",
                null,  // Agência nula
                null   // Avatar nulo
        );

        // Act
        Employee result = employeeFormMapper.map(employeeForm);

        // Assert
        assertNotNull(result, "Mapped Employee should not be null");
        assertNull(result.getName(), "Employee name should be null due to missing mapping");
        assertNull(result.getLastName(), "Employee last name should be null due to missing mapping");
        assertNull(result.getPhoneNumber(), "Employee phone number should be null due to missing mapping");
        assertNull(result.getSector(), "Employee sector should be null due to missing mapping");
        assertNull(result.getOccupation(), "Employee occupation should be null due to missing mapping");
        assertNull(result.getAgency(), "Employee agency should be null due to missing mapping");
        assertNull(result.getAvatar(), "Employee avatar should be null due to missing mapping");
        assertNull(result.getUser(), "Employee user should be null due to missing mapping");
        assertTrue(result.getOwnedProjects().isEmpty(), "Employee should have no owned projects");
        assertTrue(result.getAssignedProjects().isEmpty(), "Employee should have no assigned projects");
        assertTrue(result.getDialogs().isEmpty(), "Employee should have no dialogs");
    }
}
