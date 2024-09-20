package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmployeeSimpleViewMapperTest {

    private final EmployeeSimpleViewMapper employeeSimpleViewMapper = new EmployeeSimpleViewMapper();

    /**
     * Testa o mapeamento de Employee para EmployeeSimpleView.
     * Verifica se todos os campos são mapeados corretamente.
     */
    @Test
    @DisplayName("Should map Employee to EmployeeSimpleView correctly")
    void shouldMapEmployeeToEmployeeSimpleViewSuccessfully() {
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
    void shouldReturnNullWhenEmployeeIsNull() {
        // Act
        EmployeeSimpleView result = employeeSimpleViewMapper.map(null);

        // Assert
        assertNull(result, "Mapped EmployeeSimpleView should be null when Employee is null");
    }
}

