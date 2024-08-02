package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.PasswordRecoveryForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.PasswordResetForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PasswordRecoveryController.class)
public class PasswordRecoveryTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Set up code if needed
    }

    @Test
    void testGenerateToken() throws Exception {
        // Test the generation of recovery token
        PasswordRecoveryForm form = new PasswordRecoveryForm("john.doe@example.com");

        mockMvc.perform(post("/password-recovery/generate-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Token generated successfully"));
    }

    @Test
    void testSendEmail() throws Exception {
        // Test sending recovery email
        PasswordRecoveryForm form = new PasswordRecoveryForm("john.doe@example.com");

        mockMvc.perform(post("/password-recovery/send-email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Email sent successfully"));
    }

    @Test
    void testCompleteRecoveryFlow() throws Exception {
        // Test the complete password recovery flow
        PasswordResetForm resetForm = new PasswordResetForm("recoveryToken123", "newPassword123");

        mockMvc.perform(post("/password-recovery/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetForm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Password reset successfully"));
    }
}
