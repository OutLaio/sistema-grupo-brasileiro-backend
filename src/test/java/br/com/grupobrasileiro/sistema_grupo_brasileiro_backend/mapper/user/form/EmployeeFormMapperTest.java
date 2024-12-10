package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.form;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
     * Testa o mapeamento de EmployeeForm para Employee com valores válidos.
     * Verifica se todos os campos são mapeados corretamente.
     */
    @Test
    @DisplayName("Should map EmployeeForm to Employee correctly with valid values")
    void mapEmployeeFormToEmployeeCorrectly() {
        // Arrange
        EmployeeForm employeeForm = new EmployeeForm(
                "John",
                "Doe",
                "123456789",
                "IT",
                "Developer",
                "Agency",
                "123456",     // registrationNumber
                1L
        );

        // Act
        Employee result = employeeFormMapper.map(employeeForm);

        // Assert
        assertNotNull(result, "Mapped Employee should not be null");
        assertEquals("John", result.getName());
        assertEquals("Doe", result.getLastName());
        assertEquals("123456789", result.getPhoneNumber());
        assertEquals("IT", result.getSector());
        assertEquals("Developer", result.getOccupation());
        assertEquals("Agency", result.getAgency());
        assertEquals(1L, result.getAvatar());
        assertNull(result.getUser());
    }
    
    /**
     * Testa o mapeamento de EmployeeForm com valores nulos.
     * Verifica se todos os campos do Employee são nulos após o mapeamento.
     */
    @Test
    @DisplayName("Should handle null values in EmployeeForm correctly")
    void handleNullValuesInEmployeeForm() {
        // Arrange
        EmployeeForm employeeForm = new EmployeeForm(
                null,
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
        assertNull(result.getUser());
    }

    /**
     * Testa o mapeamento de EmployeeForm parcialmente preenchido para Employee.
     * Verifica se os campos preenchidos são mapeados corretamente e os campos vazios são nulos.
     */
    @Test
    @DisplayName("Should map partially filled EmployeeForm to Employee correctly")
    void mapPartiallyFilledEmployeeFormToEmployee() {
        // Arrange
        EmployeeForm employeeForm = new EmployeeForm(
                "Jane",
                null,  // Sobrenome nulo
                "987654321",
                null,  // Setor nulo
                "Designer",
                null,  // Agência nula
                "123456",     // registrationNumber
                null   // Avatar nulo
        );

        // Act
        Employee result = employeeFormMapper.map(employeeForm);

        // Assert
        assertNotNull(result, "Mapped Employee should not be null");
        assertEquals("Jane", result.getName());
        assertNull(result.getLastName(), "Employee last name should be null due to missing mapping");
        assertEquals("987654321", result.getPhoneNumber());
        assertNull(result.getSector(), "Employee sector should be null due to missing mapping");
        assertEquals("Designer", result.getOccupation());
        assertNull(result.getAgency(), "Employee agency should be null due to missing mapping");
        assertNull(result.getAvatar(), "Employee avatar should be null");
    }

    /**
     * Testa se as coleções no Employee são inicializadas ao mapear um EmployeeForm.
     */
    @Test
    @DisplayName("Should initialize collections when EmployeeForm is mapped")
    void initializeCollectionsWhenMapped() {
        // Arrange
        EmployeeForm employeeForm = new EmployeeForm(
                "Alice",
                "Johnson",
                "111222333",
                "Sales",
                "Sales Representative",
                "Branch Office",
                "123456",     // registrationNumber
                3L
        );

        // Act
        Employee result = employeeFormMapper.map(employeeForm);

    }
}
