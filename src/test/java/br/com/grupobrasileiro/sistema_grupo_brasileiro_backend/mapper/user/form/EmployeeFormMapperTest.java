package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.form;

import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.EmployeeForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeFormMapperTest {

    private final EmployeeFormMapper employeeFormMapper = new EmployeeFormMapper();

    @Test
    void shouldMapEmployeeFormToEmployee() {
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
        assertNull(result.getId(), "Employee ID should be null");
        assertEquals("John", result.getName(), "Employee name should match");
        assertEquals("Doe", result.getLastName(), "Employee lastname should match");
        assertEquals("123456789", result.getPhoneNumber(), "Employee phone number should match");
        assertEquals("IT", result.getSector(), "Employee sector should match");
        assertEquals("Developer", result.getOccupation(), "Employee occupation should match");
        assertEquals("Agency", result.getAgency(), "Employee agency should match");
        assertEquals(Integer.valueOf(1), result.getAvatar(), "Employee avatar should match");
        assertNull(result.getUser(), "Employee user should be null");
        assertTrue(result.getOwnedProjects().isEmpty(), "Employee should have no owned projects");
        assertTrue(result.getAssignedProjects().isEmpty(), "Employee should have no assigned projects");
        assertTrue(result.getDialogs().isEmpty(), "Employee should have no dialogs");
    }
}
