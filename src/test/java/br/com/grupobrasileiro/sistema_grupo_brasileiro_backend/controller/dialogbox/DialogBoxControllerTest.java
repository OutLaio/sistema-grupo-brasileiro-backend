package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.dialogbox;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;  
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.form.DialogBoxForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.view.DialogBoxView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.dialogbox.DialogBoxService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService; // Simulando o TokenService
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;

@WebMvcTest(DialogBoxController.class)  
class DialogBoxControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
        String token = "your-jwt-token";  // Substitua pelo token de teste válido
        
        when(dialogBoxService.createMessage(any(DialogBoxForm.class))).thenReturn(dialogBoxView);

        mockMvc.perform(post("/api/v1/dialogs")
                .header("Authorization", "Bearer " + token)  // Inclui o token JWT no cabeçalho
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDialogBoxForm)))
                .andExpect(status().isCreated())  // Espera o código 201
                .andExpect(jsonPath("$.dialog").value("Sample dialog message"))
                .andExpect(jsonPath("$.employee.fullName").value("John Doe"))
                .andExpect(jsonPath("$.employee.avatar").value(101));

        verify(dialogBoxService, times(1)).createMessage(any(DialogBoxForm.class));
    }

    @Test
    public void testCreateMessage_InvalidInput_MissingFields() throws Exception {
        DialogBoxForm invalidForm = new DialogBoxForm(null, 1L, "");

        mockMvc.perform(post("/api/v1/dialogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidForm)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").exists());

        verify(dialogBoxService, never()).createMessage(any(DialogBoxForm.class));
    }

    @Test
    public void testCreateMessage_InvalidInput_NullFields() throws Exception {
        DialogBoxForm invalidForm = new DialogBoxForm(null, null, null);

        mockMvc.perform(post("/api/v1/dialogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidForm)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.errors").isArray());
        verify(dialogBoxService, never()).createMessage(any(DialogBoxForm.class));
    }

    @Test
    public void testGetMessagesForBriefing_Success() throws Exception {
        Long briefingId = 1L;
        Set<DialogBoxView> messages = Collections.singleton(dialogBoxView);

        when(dialogBoxService.getMessagesByBriefingId(briefingId)).thenReturn(messages);

        mockMvc.perform(get("/api/v1/dialogs/briefing/{idBriefing}", briefingId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dialog").value("Sample dialog message"))
                .andExpect(jsonPath("$[0].employee.fullName").value("John Doe"))
                .andExpect(jsonPath("$[0].employee.avatar").value(101));

        verify(dialogBoxService, times(1)).getMessagesByBriefingId(briefingId);
    }

    
    @Test
    public void testCreateMessage_InternalServerError() throws Exception {
        when(dialogBoxService.createMessage(any(DialogBoxForm.class))).thenThrow(new RuntimeException("Internal Server Error"));

        mockMvc.perform(post("/api/v1/dialogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDialogBoxForm)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Internal Server Error"));

        verify(dialogBoxService, times(1)).createMessage(any(DialogBoxForm.class));
    }
    
    @Test
    public void testCreateMessage_Unauthorized() throws Exception {
        DialogBoxForm validDialogBoxForm = new DialogBoxForm(1L, 1L, "Valid message");

        // Testando a falta de um token de autorização
        mockMvc.perform(post("/api/v1/dialogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDialogBoxForm)))
                .andExpect(status().isUnauthorized())  // Espera erro 401 (não autorizado)
                .andExpect(jsonPath("$.message").value("Full authentication is required to access this resource"));
    }

    @Test
    public void testCreateMessage_InvalidToken() throws Exception {
        String invalidToken = "invalid-jwt-token";  // Um token inválido

        DialogBoxForm validDialogBoxForm = new DialogBoxForm(1L, 1L, "Valid message");

        mockMvc.perform(post("/api/v1/dialogs")
                .header("Authorization", "Bearer " + invalidToken)  // Token inválido
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDialogBoxForm)))
                .andExpect(status().isUnauthorized())  // Espera erro 401 (não autorizado)
                .andExpect(jsonPath("$.message").value("Full authentication is required to access this resource"));
    }

    @Test
    public void testCreateMessage_MissingAuthorizationHeader() throws Exception {
        DialogBoxForm validDialogBoxForm = new DialogBoxForm(1L, 1L, "Valid message");

        mockMvc.perform(post("/api/v1/dialogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDialogBoxForm)))
                .andExpect(status().isUnauthorized())  // Espera erro 401
                .andExpect(jsonPath("$.message").value("Full authentication is required to access this resource"));
    }

    @Test
    public void testGetMessagesForBriefing_NotFound() throws Exception {
        Long briefingId = 999L;  // Um briefing ID que não existe

        when(dialogBoxService.getMessagesByBriefingId(briefingId)).thenReturn(Collections.emptySet());

        mockMvc.perform(get("/api/v1/dialogs/briefing/{idBriefing}", briefingId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())  // Espera erro 404 (não encontrado)
                .andExpect(jsonPath("$.message").value("Briefing not found"));
    }

    @Test
    public void testGetMessagesForBriefing_InternalServerError() throws Exception {
        Long briefingId = 1L;

        // Simulando um erro inesperado no serviço
        when(dialogBoxService.getMessagesByBriefingId(briefingId)).thenThrow(new RuntimeException("Internal Server Error"));

        mockMvc.perform(get("/api/v1/dialogs/briefing/{idBriefing}", briefingId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())  // Espera erro 500
                .andExpect(jsonPath("$.message").value("Internal Server Error"));
    }

    @Test
    public void testCreateMessage_FailedValidation_MissingFields() throws Exception {
        // Enviar um formulário de mensagem com campos inválidos (null)
        DialogBoxForm invalidDialogBoxForm = new DialogBoxForm(null, null, null);

        mockMvc.perform(post("/api/v1/dialogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDialogBoxForm)))
                .andExpect(status().isUnprocessableEntity())  // Espera erro 422
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0]").value("The briefing ID cannot be null"))
                .andExpect(jsonPath("$.errors[1]").value("The message cannot be empty"));
    }

    @Test
    public void testGetMessagesForBriefing_Success_WithEmptyMessages() throws Exception {
        Long briefingId = 1L;

        // Simulando um briefing com mensagens vazias
        when(dialogBoxService.getMessagesByBriefingId(briefingId)).thenReturn(Collections.emptySet());

        mockMvc.perform(get("/api/v1/dialogs/briefing/{idBriefing}", briefingId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Espera resposta 200 (OK)
                .andExpect(jsonPath("$").isEmpty());  // Espera que o corpo da resposta esteja vazio
    }

    @Test
    public void testCreateMessage_FailedInternalServerError() throws Exception {
        when(dialogBoxService.createMessage(any(DialogBoxForm.class)))
                .thenThrow(new RuntimeException("Internal Server Error"));

        mockMvc.perform(post("/api/v1/dialogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDialogBoxForm)))
                .andExpect(status().isInternalServerError())  // Espera erro 500
                .andExpect(jsonPath("$.message").value("Internal Server Error"));

        verify(dialogBoxService, times(1)).createMessage(any(DialogBoxForm.class));
    }

}
