package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;

class EmployeeSimpleViewMapperTest {

    private final EmployeeSimpleViewMapper employeeSimpleViewMapper = new EmployeeSimpleViewMapper();

    /**
     * Testa o mapeamento de Employee para EmployeeSimpleView.
     * Verifica se todos os campos são mapeados corretamente.
     */
    @Test
    @DisplayName("Should map Employee to EmployeeSimpleView correctly")
    void mapEmployeeToEmployeeSimpleViewSuccessfully() {
        // Arrange
        Employee employee = new Employee(
                1L,
                "John",
                "Doe",
                "123456789",
                "IT",
                "Developer",
                "Main Agency",
                1L,
                null,
                null,
                null,
                null
        );

        // Act
        EmployeeSimpleView result = employeeSimpleViewMapper.map(employee);

        // Assert
        assertNotNull(result, "Mapped EmployeeSimpleView should not be null");
        assertEquals(employee.getId(), result.id(), "EmployeeSimpleView ID should match");
        assertEquals("John Doe", result.fullName(), "EmployeeSimpleView fullName should match");
        assertEquals(employee.getAvatar(), result.avatar(), "EmployeeSimpleView avatar should match");
    }

    /**
     * Testa o mapeamento quando o empregado é nulo.
     * Verifica se o resultado também é nulo.
     */
    @Test
    @DisplayName("Should return null when Employee is null")
    void returnNullWhenEmployeeIsNull() {
        // Act
        EmployeeSimpleView result = employeeSimpleViewMapper.map(null);

        // Assert
        assertNull(result, "Mapped EmployeeSimpleView should be null when Employee is null");
    }

 
    /**
     * Testa o mapeamento quando o empregado tem nomes e sobrenomes em branco.
     * Verifica se o resultado é tratado corretamente.
     */
    @Test
    @DisplayName("Should handle empty first and last names")
    void handleEmptyFirstAndLastNames() {
        // Arrange
        Employee employee = new Employee(
                3L,
                "", // Nome vazio
                "", // Sobrenome vazio
                "123456789",
                "IT",
                "Developer",
                "Main Agency",
                1L,
                null,
                null,
                null,
                null
        );

        // Act
        EmployeeSimpleView result = employeeSimpleViewMapper.map(employee);

        // Assert
        assertNotNull(result, "Mapped EmployeeSimpleView should not be null");
        assertEquals(employee.getId(), result.id(), "EmployeeSimpleView ID should match");
        assertEquals(" ", result.fullName(), "EmployeeSimpleView fullName should be a space for empty names");
    }

    /**
     * Testa o mapeamento quando todos os campos obrigatórios são preenchidos.
     * Verifica se o resultado é mapeado corretamente.
     */
    @Test
    @DisplayName("Should map all mandatory fields correctly")
    void mapAllMandatoryFields() {
        // Arrange
        Employee employee = new Employee(
                4L,
                "Alice",
                "Smith",
                "987654321",
                "HR",
                "Manager",
                "Branch Office",
                2L,
                null,
                null,
                null,
                null
        );

        // Act
        EmployeeSimpleView result = employeeSimpleViewMapper.map(employee);

        // Assert
        assertNotNull(result, "Mapped EmployeeSimpleView should not be null");
        assertEquals(employee.getId(), result.id(), "EmployeeSimpleView ID should match");
        assertEquals("Alice Smith", result.fullName(), "EmployeeSimpleView fullName should match");
        assertEquals(employee.getAvatar(), result.avatar(), "EmployeeSimpleView avatar should match");
    }
}
