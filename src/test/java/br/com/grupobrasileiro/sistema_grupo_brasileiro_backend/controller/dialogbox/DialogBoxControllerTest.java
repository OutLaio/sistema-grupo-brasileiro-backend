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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.form.DialogBoxForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.view.DialogBoxView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.dialogbox.DialogBoxService;

@SpringBootTest
@AutoConfigureMockMvc
class DialogBoxControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DialogBoxService dialogBoxService;

    @MockBean
    private TokenService tokenService;

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
        when(dialogBoxService.createMessage(any(DialogBoxForm.class))).thenReturn(dialogBoxView);

        mockMvc.perform(post("/api/v1/dialogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDialogBoxForm)))
                .andExpect(status().isCreated()) 
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
    public void testGetMessagesForBriefing_NotFound() throws Exception {
        Long briefingId = 1L;

        when(dialogBoxService.getMessagesByBriefingId(briefingId)).thenReturn(Collections.emptySet());

        mockMvc.perform(get("/api/v1/dialogs/briefing/{idBriefing}", briefingId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  
                .andExpect(jsonPath("$").isEmpty());

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
}
