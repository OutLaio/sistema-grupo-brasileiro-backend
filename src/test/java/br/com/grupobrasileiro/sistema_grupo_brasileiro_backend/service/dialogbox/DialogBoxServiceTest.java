package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.dialogbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.form.DialogBoxForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.view.DialogBoxView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.dialogbox.form.DialogBoxFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.dialogbox.view.DialogBoxViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.DialogBox;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.DialogBoxRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;

public class DialogBoxServiceTest {

    @InjectMocks
    private DialogBoxService dialogBoxService;

    @Mock
    private DialogBoxRepository dialogBoxRepository;

    @Mock
    private BriefingRepository briefingRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DialogBoxFormMapper dialogBoxFormMapper;

    @Mock
    private DialogBoxViewMapper dialogBoxViewMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateMessage() {
        // Dados de teste
        Long briefingId = 1L;
        Long employeeId = 1L;
        Long avatarId = 123L; // ID fictício para o avatar
        String message = "Esta é uma mensagem de teste.";

        // Instâncias de Employee e Briefing necessárias para DialogBox
        Employee employee = new Employee();
        employee.setId(employeeId);
        
        Briefing briefing = new Briefing();
        briefing.setId(briefingId);
        
        // Criação do DialogBoxForm
        DialogBoxForm dialogBoxForm = new DialogBoxForm(employeeId, briefingId, message);
        
        // Criação do DialogBox
        DialogBox dialogBox = new DialogBox(1L, employee, briefing, LocalDateTime.now(), message);
        
        // Criação de EmployeeSimpleView para o DialogBoxView
        EmployeeSimpleView employeeSimpleView = new EmployeeSimpleView(employeeId, "Nome do Funcionário", avatarId);

        // Criação do DialogBoxView simulado
        DialogBoxView dialogBoxView = new DialogBoxView(1L, employeeSimpleView, LocalDateTime.now(), message);

        // Configuração dos mocks
        when(dialogBoxFormMapper.map(dialogBoxForm)).thenReturn(dialogBox);
        when(dialogBoxRepository.save(dialogBox)).thenReturn(dialogBox);
        when(dialogBoxViewMapper.map(dialogBox)).thenReturn(dialogBoxView);

        // Execução do método a ser testado
        DialogBoxView result = dialogBoxService.createMessage(dialogBoxForm);

        // Verificações
        assertNotNull(result, "O resultado não deve ser nulo");
        assertEquals(dialogBoxView.dialog(), result.dialog(), "A mensagem retornada deve ser igual à esperada");

        // Verificação das chamadas dos mocks
        verify(dialogBoxFormMapper).map(dialogBoxForm);
        verify(dialogBoxRepository).save(dialogBox);
        verify(dialogBoxViewMapper).map(dialogBox);
    }

    @Test
    public void testGetMessagesByBriefingId() {
        Long briefingId = 1L;

        // Criação de instâncias de Employee e Briefing
        Employee employee = new Employee();
        employee.setId(1L);

        Briefing briefing = new Briefing();
        briefing.setId(briefingId);

        // Criação do DialogBox e conjunto de DialogBoxes
        DialogBox dialogBox = new DialogBox(1L, employee, briefing, LocalDateTime.now(), "Mensagem de teste");
        Set<DialogBox> dialogBoxes = new HashSet<>();
        dialogBoxes.add(dialogBox);

        // Criação de EmployeeSimpleView para o DialogBoxView
        EmployeeSimpleView employeeSimpleView = new EmployeeSimpleView(1L, "Nome do Funcionário", 123L);

        // Criação do DialogBoxView simulado
        DialogBoxView dialogBoxView = new DialogBoxView(1L, employeeSimpleView, LocalDateTime.now(), "Mensagem de teste");

        // Configuração dos mocks
        when(dialogBoxRepository.findByBriefingId(briefingId)).thenReturn(dialogBoxes);
        when(dialogBoxViewMapper.map(any(DialogBox.class))).thenReturn(dialogBoxView);

        // Execução do método a ser testado
        Set<DialogBoxView> result = dialogBoxService.getMessagesByBriefingId(briefingId);

        // Verificações do resultado
        assertNotNull(result, "O resultado não deve ser nulo");
        assertFalse(result.isEmpty(), "O resultado deve conter mensagens");
        assertEquals(1, result.size(), "O resultado deve conter uma mensagem mapeada");

        // Verificação da consulta no repositório
        verify(dialogBoxRepository).findByBriefingId(briefingId);
    }
}
