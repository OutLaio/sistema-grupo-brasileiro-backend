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
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.dialogbox.form.DialogBoxFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.dialogbox.view.DialogBoxViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.DialogBox;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.DialogBoxRepository;

public class DialogBoxServiceTest {

    @InjectMocks
    private DialogBoxService dialogBoxService;

    @Mock
    private DialogBoxRepository dialogBoxRepository;

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
        // Criando um objeto DialogBoxForm para testar
        DialogBoxForm dialogBoxForm = new DialogBoxForm(1L, 1L, "Esta é uma mensagem de teste.");
        
        // Criando um objeto DialogBox simulado
        DialogBox dialogBox = new DialogBox(/* parâmetros necessários */);
        
        // Criando um objeto DialogBoxView simulado
        DialogBoxView dialogBoxView = new DialogBoxView(1L, "Nome do Funcionário", "Título do Briefing", LocalDateTime.now(), "Esta é uma mensagem de teste.");

        // Configurando o comportamento dos mocks
        when(dialogBoxFormMapper.map(dialogBoxForm)).thenReturn(dialogBox);
        when(dialogBoxRepository.save(dialogBox)).thenReturn(dialogBox);
        when(dialogBoxViewMapper.map(dialogBox)).thenReturn(dialogBoxView);

        // Chamando o método a ser testado
        DialogBoxView result = dialogBoxService.createMessage(dialogBoxForm);

        // Verificando se o resultado não é nulo
        assertNotNull(result, "O resultado não deve ser nulo");
        
        // Verificando se o método save foi chamado
        verify(dialogBoxRepository).save(dialogBox);
    }

    @Test
    public void testGetMessagesByBriefingId() {
        Long briefingId = 1L;

        // Criando um conjunto de DialogBox simulados
        Set<DialogBox> dialogBoxes = new HashSet<>();
        dialogBoxes.add(new DialogBox(/* parâmetros necessários */));

        // Criando um objeto DialogBoxView simulado
        DialogBoxView dialogBoxView = new DialogBoxView(1L, "Nome do Funcionário", "Título do Briefing", LocalDateTime.now(), "Esta é uma mensagem de teste.");

        // Configurando o comportamento dos mocks
        when(dialogBoxRepository.findByBriefingId(briefingId)).thenReturn(dialogBoxes);
        when(dialogBoxViewMapper.map(any(DialogBox.class))).thenReturn(dialogBoxView);

        // Chamando o método a ser testado
        Set<DialogBoxView> result = dialogBoxService.getMessagesByBriefingId(briefingId);

        // Verificando se o resultado não é nulo e contém os elementos esperados
        assertNotNull(result, "O resultado não deve ser nulo");
        assertFalse(result.isEmpty(), "O resultado deve conter mensagens");
        assertEquals(1, result.size(), "O resultado deve conter uma mensagem mapeada");
    }
}