package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.containsString;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.EmailRequestForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.email.PasswordRequest;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.SecurityConfig;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.EmailService;

@WebMvcTest(PasswordRecoveryController.class)
@Import(SecurityConfig.class)  
public class PasswordRecoveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private EmailService emailService;

    private ObjectMapper objectMapper;
    private Faker faker;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        faker = new Faker(); 
    }

    @Test
    void testRequestReset_Success() throws Exception {
        // Arrange
        String email = faker.internet().emailAddress();
        String token = faker.internet().uuid(); // Simula a geração de token
        EmailRequestForm emailRequestForm = new EmailRequestForm(email);
        User user = new User(); // Cria um usuário fictício

        // Configura o comportamento dos mocks
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(tokenService.generateToken(user)).thenReturn(token);

        PasswordRequest expectedPasswordRequest = new PasswordRequest(
            "no-reply@everdev.com", 
            email, 
            "Password Reset", 
            String.format(
                "Hello!<br><br>" +
                "You requested a password reset. To reset your password, please click the link below:<br><br>" +
                "<a href=\"http://localhost:4200/resetPassword?token=%s\">Reset Password</a><br><br>" +
                "If you did not request this, please ignore this email. Your password will remain unchanged and no further action will be needed.<br><br>" +
                "Best regards,<br>" +
                "EverDev Team", token
            )
        );

        doNothing().when(emailService).send(expectedPasswordRequest);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/requestReset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailRequestForm)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("E-mail enviado com sucesso!"));

        verify(userRepository).findByEmail(email);
        verify(tokenService).generateToken(user);
        verify(emailService).send(expectedPasswordRequest);
    }

    @Test
    void testRequestReset_UserNotFound() throws Exception {
        // Arrange
        String email = faker.internet().emailAddress();
        EmailRequestForm emailRequestForm = new EmailRequestForm(email);

        when(userRepository.findByEmail(email)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/requestReset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailRequestForm)))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError())  
            .andExpect(MockMvcResultMatchers.content().string("Usuário não encontrado com e-mail: " + email));  

        verify(userRepository).findByEmail(email);
        verify(tokenService, never()).generateToken(any());
        verify(emailService, never()).send(any());
    }

    @Test
    void testRequestReset_ExceptionHandling() throws Exception {
        // Arrange
        String email = faker.internet().emailAddress();
        EmailRequestForm emailRequestForm = new EmailRequestForm(email);

        when(userRepository.findByEmail(email)).thenThrow(new RuntimeException("Unexpected error"));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/requestReset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emailRequestForm)))
            .andExpect(MockMvcResultMatchers.status().isInternalServerError())  
            .andExpect(MockMvcResultMatchers.content().string("Unexpected error"));  

        verify(userRepository).findByEmail(email);
        verify(tokenService, never()).generateToken(any());
        verify(emailService, never()).send(any());
    }
}
