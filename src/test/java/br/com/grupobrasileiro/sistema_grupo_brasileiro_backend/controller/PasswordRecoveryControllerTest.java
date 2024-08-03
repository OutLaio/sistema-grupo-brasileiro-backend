package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.EmailRequestForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.SendEmailForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.EmailService;

@WebMvcTest(PasswordRecoveryController.class)
public class PasswordRecoveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private PasswordRecoveryController passwordRecoveryController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(passwordRecoveryController).build();
    }

    @Test
    public void testResetPassword_UserFound_ShouldSendEmail() throws Exception {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(tokenService.generateToken(user)).thenReturn("sample-token");
        doNothing().when(emailService).send(any(SendEmailForm.class));

        EmailRequestForm emailRequest = new EmailRequestForm(email);
        emailRequest.setEmail(email);

        mockMvc.perform(post("/solicitaReset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(emailRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Email sent successfully!"))
                .andDo(MockMvcResultHandlers.print());

        verify(userRepository).findByEmail(email);
        verify(tokenService).generateToken(user);
        verify(emailService).send(any(SendEmailForm.class));
    }

    @Test
    public void testResetPassword_UserNotFound_ShouldReturnNotFound() throws Exception {
        String email = "test@example.com";

        when(userRepository.findByEmail(email)).thenReturn(null);

        EmailRequestForm emailRequest = new EmailRequestForm(email);
        emailRequest.setEmail(email);

        mockMvc.perform(post("/solicitaReset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(emailRequest)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"))
                .andDo(MockMvcResultHandlers.print());

        verify(userRepository).findByEmail(email);
    }

    @Test
    public void testResetPassword_Exception_ShouldReturnNotFound() throws Exception {
        String email = "test@example.com";

        when(userRepository.findByEmail(email)).thenThrow(new RuntimeException("Database error"));

        EmailRequestForm emailRequest = new EmailRequestForm(email);
        emailRequest.setEmail(email);

        mockMvc.perform(post("/solicitaReset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(emailRequest)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"))
                .andDo(MockMvcResultHandlers.print());

        verify(userRepository).findByEmail(email);
    }
}
