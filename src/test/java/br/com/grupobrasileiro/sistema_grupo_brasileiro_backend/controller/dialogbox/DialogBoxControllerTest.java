package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.dialogbox;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.Response;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.form.DialogBoxForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.view.DialogBoxView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.dialogbox.DialogBoxService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DialogBoxController.class)
class DialogBoxControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DialogBoxController dialogBoxController;

    @MockBean
    private DialogBoxService dialogBoxService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private DialogBoxForm validDialogBoxForm;
    private DialogBoxView dialogBoxView;

    @BeforeEach
    public void setup() {
        validDialogBoxForm = new DialogBoxForm(1L, 1L, "Sample message");

        EmployeeSimpleView employeeSimpleView = new EmployeeSimpleView(1L, "John Doe", 101L);
        dialogBoxView = new DialogBoxView(1L, employeeSimpleView, LocalDateTime.now(), "Sample dialog message");
    }

    @Test
    public void testCreateMessage_Success() throws Exception {
        // Arrange
        DialogBoxForm form = new DialogBoxForm(1L, 1L, "Test message");
        DialogBoxView dialogBoxView = new DialogBoxView(1L, null, LocalDateTime.now(), "Test message");

        when(dialogBoxService.createMessage(form)).thenReturn(dialogBoxView);

        // Act
        ResponseEntity<?> response = dialogBoxController.createMessage(form);

        // Assert
        verify(dialogBoxService).createMessage(form);

        assertNotNull(response, "ResponseEntity should not be null");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(new Response<>("Nova mensagem registrada!", dialogBoxView), response.getBody());

        @SuppressWarnings("unchecked")
        Response<DialogBoxView> responseBody = (Response<DialogBoxView>) response.getBody();
        assertNotNull(responseBody, "Response body should not be null");
        assertEquals("Nova mensagem registrada!", responseBody.getMessage(), "Response message should match");
        assertEquals(dialogBoxView, responseBody.getData(), "Response data should match the created dialog box view");
    }

    @Test
    public void testGetMessagesForBriefing_Success() throws Exception {
        Long briefingId = 1L;
        Set<DialogBoxView> messages = Set.of(
                new DialogBoxView(1L, new EmployeeSimpleView(1L, null, null), LocalDateTime.now(), "First message"),
                new DialogBoxView(2L, new EmployeeSimpleView(1L, null, null), LocalDateTime.now(), "Second message")
        );

        when(dialogBoxService.getMessagesByBriefingId(briefingId)).thenReturn(messages);

        // Act
        ResponseEntity<Response<?>> response = dialogBoxController.getMessagesForBriefing(briefingId);

        // Assert
        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be 200 OK");

        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals(new Response<>("Mensagens recuperadas com sucesso!", messages), response.getBody(), "Response message should match");

        @SuppressWarnings("unchecked")
        Set<DialogBoxView> data = (Set<DialogBoxView>) response.getBody().getData();
        assertNotNull(data, "Data should not be null");
        assertEquals(2, data.size(), "Data should contain 2 messages");

        DialogBoxView firstMessage = data.stream().toList().get(0);
        assertEquals(messages.stream().toList().get(0).id(), firstMessage.id(), "First message ID should match");
        assertEquals(messages.stream().toList().get(0).employee(), firstMessage.employee(), "First message employee should match");
        assertEquals(messages.stream().toList().get(0).dialog(), firstMessage.dialog(), "First message content should match");

        DialogBoxView secondMessage = data.stream().toList().get(1);
        assertEquals(messages.stream().toList().get(1).id(), secondMessage.id(), "Second message ID should match");
        assertEquals(messages.stream().toList().get(1).employee(), secondMessage.employee(), "Second message employee should match");
        assertEquals(messages.stream().toList().get(1).dialog(), secondMessage.dialog(), "Second message content should match");
    }

    @Test
    public void testGetMessagesForBriefing_NotFound() throws Exception {
        Long briefingId = 999L;
        when(dialogBoxService.getMessagesByBriefingId(briefingId))
                .thenThrow(new EntityNotFoundException("Briefing não encontrado"));

        // Act
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> dialogBoxController.getMessagesForBriefing(briefingId)
        );

        // Assert
        assertNotNull(exception, "Exception should not be null");
        assertEquals("Briefing não encontrado", exception.getMessage(), "Exception message should match");
        verify(dialogBoxService, times(1)).getMessagesByBriefingId(briefingId);
    }

}
