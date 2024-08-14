package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ResetPasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.InvalidTokenException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

@WebMvcTest(PasswordResetController.class)
public class PasswordResetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private PasswordEncoder passwordEncoder;

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

        when(tokenService.validateToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.encode(newPassword)).thenReturn("encoded-password");
        when(userRepository.save(user)).thenReturn(user);

        //TODO: Implementar o reset de senha
//      ResetPasswordForm resetForm = new ResetPasswordForm(token, newPassword);
        ResetPasswordForm resetForm = null;

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/resetPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(resetForm)));

        result.andExpect(status().isOk())
              .andExpect(MockMvcResultMatchers.content().string("Password successfully changed!"))
              .andDo(MockMvcResultHandlers.print());

        verify(tokenService).invalidateToken(token);
    }

    @Test
    public void testResetPassword_InvalidToken_ShouldReturnBadRequest() throws Exception {
        String token = "invalid-token";
        String newPassword = "NewPassword123!";

        when(tokenService.validateToken(token)).thenReturn(null);

        //TODO: Implementar o reset de senha
//      ResetPasswordForm resetForm = new ResetPasswordForm(token, newPassword);
        ResetPasswordForm resetForm = null;

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/resetPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(resetForm)));

        result.andExpect(status().isBadRequest())
              .andExpect(MockMvcResultMatchers.content().string("Invalid or expired token"))
              .andDo(MockMvcResultHandlers.print());

        verify(tokenService, never()).invalidateToken(token);
    }

    @Test
    public void testResetPassword_UserNotFound_ShouldReturnNotFound() throws Exception {
        String token = "valid-token";
        String newPassword = "NewPassword123!";
        String email = faker.internet().emailAddress();

        when(tokenService.validateToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(null);

        //TODO: Implementar o reset de senha
//      ResetPasswordForm resetForm = new ResetPasswordForm(token, newPassword);
        ResetPasswordForm resetForm = null;

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/resetPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(resetForm)));

        result.andExpect(status().isNotFound())
              .andExpect(MockMvcResultMatchers.content().string("Usuário não encontrado com e-mail: " + email))
              .andDo(MockMvcResultHandlers.print());

        verify(tokenService, never()).invalidateToken(token);
    }
}
