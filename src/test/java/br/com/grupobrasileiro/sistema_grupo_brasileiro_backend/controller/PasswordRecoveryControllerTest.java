package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.EmailRequestForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ResetPasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.email.PasswordRequest;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.EmailService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PasswordRecoveryController.class)
public class PasswordRecoveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private EmailService emailService;

    private Faker faker;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testResetPassword_ValidToken_ShouldResetPassword() throws Exception {
        String token = "valid-token";
        String newPassword = "NewPassword123!";
        String email = faker.internet().emailAddress();
        User user = new User();
        user.setEmail(email);

        // Mock the token service to validate the token
        when(tokenService.validateToken(token)).thenReturn(email);

        // Mock the repository to find the user
        when(userRepository.findByEmail(email)).thenReturn(user);

        // Mock the repository to save the updated user
        when(userRepository.save(user)).thenReturn(user);
        
        //TODO: Implementar o reset de senha
//        ResetPasswordForm resetForm = new ResetPasswordForm(token, newPassword);
        ResetPasswordForm resetForm = null;

        // Perform the POST request to reset the password
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/resetPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(resetForm)));

        // Verify that the response status is 200 OK
        result.andExpect(status().isOk())
              // Verify the content of the response
              .andExpect(MockMvcResultMatchers.content().string("Password successfully changed!"))
              .andDo(MockMvcResultHandlers.print());

        // Verify that the token was invalidated
        verify(tokenService).invalidateToken(token);
    }

    @Test
    public void testResetPassword_InvalidToken_ShouldReturnBadRequest() throws Exception {
        String token = "invalid-token";
        String newPassword = "NewPassword123!";

        // Mock the token service to validate the token
        when(tokenService.validateToken(token)).thenReturn(null);

        //TODO: Implementar o reset de senha
//      ResetPasswordForm resetForm = new ResetPasswordForm(token, newPassword);
        ResetPasswordForm resetForm = null;

        // Perform the POST request to reset the password
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/resetPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(resetForm)));

        // Verify that the response status is 400 Bad Request
        result.andExpect(status().isBadRequest())
              // Verify the content of the response
              .andExpect(MockMvcResultMatchers.content().string("Invalid or expired token"))
              .andDo(MockMvcResultHandlers.print());

        // Verify that the token was not invalidated
        verify(tokenService, never()).invalidateToken(token);
    }

    @Test
    public void testResetPassword_UserNotFound_ShouldReturnNotFound() throws Exception {
        String token = "valid-token";
        String newPassword = "NewPassword123!";
        String email = faker.internet().emailAddress();

        // Mock the token service to validate the token
        when(tokenService.validateToken(token)).thenReturn(email);

        // Mock the repository to return null for the user
        when(userRepository.findByEmail(email)).thenReturn(null);

        //TODO: Implementar o reset de senha
//      ResetPasswordForm resetForm = new ResetPasswordForm(token, newPassword);
        ResetPasswordForm resetForm = null;

        // Perform the POST request to reset the password
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/resetPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(resetForm)));

        // Verify that the response status is 404 Not Found
        result.andExpect(status().isNotFound())
              // Verify the content of the response
              .andExpect(MockMvcResultMatchers.content().string("Usuário não encontrado com e-mail: " + email))
              .andDo(MockMvcResultHandlers.print());

        // Verify that the token was not invalidated
        verify(tokenService, never()).invalidateToken(token);
    }

    @Test
    public void testRequestReset_UserFound_ShouldSendEmail() throws Exception {
        String email = faker.internet().emailAddress();
        User user = new User();
        user.setEmail(email);

        // Mock the repository to return a User
        when(userRepository.findByEmail(email)).thenReturn(user);

        // Mock the token service to generate a token
        when(tokenService.generateToken(user)).thenReturn("sample-token");

        // Mock the email service to simulate sending the email
        doNothing().when(emailService).send(any(PasswordRequest.class));

        EmailRequestForm emailRequest = new EmailRequestForm(email);

        // Perform the POST request to the password recovery endpoint
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/requestReset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(emailRequest)));

        // Verify that the response status is 200 OK
        result.andExpect(status().isOk())
              // Verify the content of the response
              .andExpect(MockMvcResultMatchers.content().string("E-mail enviado com sucesso!"))
              .andDo(MockMvcResultHandlers.print());

        // Verify that the mocked methods were called correctly
        verify(userRepository).findByEmail(email);
        verify(tokenService).generateToken(user);
        verify(emailService).send(argThat(sendEmailForm ->
            sendEmailForm.emailTo().equals(email) &&
            sendEmailForm.subject().equals("Password Reset") &&
            sendEmailForm.text().contains("http://localhost:4200/resetPassword?token=sample-token")
        ));
    }
}
