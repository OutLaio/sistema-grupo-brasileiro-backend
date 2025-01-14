package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.dialogbox.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.view.DialogBoxView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view.EmployeeSimpleViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.DialogBox;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;

@ExtendWith(MockitoExtension.class)
class DialogBoxViewMapperTest {

    @InjectMocks
    private DialogBoxViewMapper dialogBoxViewMapper;

    @Mock
    private EmployeeSimpleViewMapper employeeSimpleViewMapper;

    private DialogBox dialogBox;
    private Employee employee;

    @BeforeEach
    void setup() {
        // Configura um objeto Employee simulado
        employee = new Employee();
        employee.setId(1L);
        employee.setName("John");
        employee.setLastName("Doe");
        employee.setAvatar(101L);  // Configuração do avatar

        // Configura um objeto DialogBox com dados de teste
        dialogBox = new DialogBox();
        dialogBox.setId(10L);
        dialogBox.setEmployee(employee);
        dialogBox.setTime(LocalDateTime.of(2023, 11, 1, 10, 30));
        dialogBox.setDialog("Teste de dialog");
    }

    @Test
    void shouldMapDialogBoxToDialogBoxViewSuccessfully() {
        // Configura retorno simulado para o EmployeeSimpleViewMapper com id, fullName e avatar
        EmployeeSimpleView employeeSimpleView = new EmployeeSimpleView(1L, "John Doe", 101L);
        when(employeeSimpleViewMapper.map(employee)).thenReturn(employeeSimpleView);

        // Executa o mapeamento
        DialogBoxView result = dialogBoxViewMapper.map(dialogBox);

        // Verifica se os valores foram mapeados corretamente
        assertNotNull(result);
        assertEquals(dialogBox.getId(), result.id());
        assertEquals(employeeSimpleView, result.employee());
        assertEquals(dialogBox.getTime(), result.time());
        assertEquals(dialogBox.getDialog(), result.dialog());
    }

    @Test
    void shouldHandleNullEmployeeGracefully() {
        // Configura o DialogBox com funcionário nulo
        dialogBox.setEmployee(null);

        // Executa o mapeamento e verifica se o resultado é conforme o esperado
        Exception exception = assertThrows(NullPointerException.class, () -> dialogBoxViewMapper.map(dialogBox));
        assertEquals("DialogBox employee is null", exception.getMessage());
    }

    @Test
    void shouldMapWithNullFieldsInDialogBox() {
        // Configura DialogBox com dialog nulo
        dialogBox.setDialog(null);

        // Configura retorno simulado para EmployeeSimpleViewMapper
        EmployeeSimpleView employeeSimpleView = new EmployeeSimpleView(1L, "John Doe", 101L);
        when(employeeSimpleViewMapper.map(employee)).thenReturn(employeeSimpleView);

        // Executa o mapeamento
        DialogBoxView result = dialogBoxViewMapper.map(dialogBox);

        // Verifica se o mapeamento é feito corretamente mesmo com dialog nulo
        assertNotNull(result);
        assertEquals(dialogBox.getId(), result.id());
        assertEquals(employeeSimpleView, result.employee());
        assertEquals(dialogBox.getTime(), result.time());
        assertNull(result.dialog());
    }
}
