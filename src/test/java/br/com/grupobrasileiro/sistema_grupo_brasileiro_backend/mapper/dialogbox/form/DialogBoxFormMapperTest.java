package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.dialogbox.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.form.DialogBoxForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.dialogbox.form.DialogBoxFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.DialogBox;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class DialogBoxFormMapperTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private BriefingRepository briefingRepository;

    @InjectMocks
    private DialogBoxFormMapper dialogBoxFormMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMapSuccessfully() {
        // Arrange
        Long employeeId = 1L;
        Long briefingId = 1L;
        String message = "Test message";
        DialogBoxForm form = new DialogBoxForm(employeeId, briefingId, message);
        Employee employee = new Employee(); // Configure Employee with necessary data if needed
        Briefing briefing = new Briefing(); // Configure Briefing with necessary data if needed

        // Simular o comportamento dos repositórios
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(briefingRepository.findById(briefingId)).thenReturn(Optional.of(briefing));

        // Act
        DialogBox result = dialogBoxFormMapper.map(form);

        // Assert
        assertEquals(employee, result.getEmployee());
        assertEquals(briefing, result.getBriefing());
        assertEquals(message, result.getDialog());
        assertNotNull(result.getTime());
        assertEquals(LocalDateTime.now().getMinute(), result.getTime().getMinute()); // Verifica se o tempo é o atual
    }

    @Test
    public void testMapWithNullInput() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            dialogBoxFormMapper.map(null);
        });
    }

    @Test
    public void testMapWhenEmployeeNotFound() {
        // Arrange
        DialogBoxForm form = new DialogBoxForm(1L, 1L, "Test message");
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            dialogBoxFormMapper.map(form);
        });
        assertEquals("Employee not found", thrown.getMessage());
    }

    @Test
    public void testMapWhenBriefingNotFound() {
        // Arrange
        DialogBoxForm form = new DialogBoxForm(1L, 1L, "Test message");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(new Employee()));
        when(briefingRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            dialogBoxFormMapper.map(form);
        });
        assertEquals("Briefing not found", thrown.getMessage());
    }
}
