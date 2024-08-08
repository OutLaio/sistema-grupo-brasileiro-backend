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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
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

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private PasswordResetController passwordResetController;

    private Faker faker;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(passwordResetController).build();
        faker = new Faker();
    }

    @Test
    public void testResetPassword_ValidTokenAndUser_ShouldResetPassword() throws Exception {
        String email = faker.internet().emailAddress();
        String token = faker.internet().uuid();
        String newPassword = faker.internet().password();

        User user = new User();
        user.setEmail(email);
        user.setPassword(faker.internet().password());

        when(tokenService.validateToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(user);
        doNothing().when(passwordEncoder).encode(any(String.class));
        doNothing().when(userRepository).save(any(User.class));
        doNothing().when(tokenService).invalidateToken(any(String.class));

        ResetPasswordForm resetPasswordForm = new ResetPasswordForm(token, newPassword);

        mockMvc.perform(post("/api/v1/resetPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(resetPasswordForm)))
                .andExpect(status().isOk())
                .andExpect(content().string("Password successfully changed!"))
                .andDo(MockMvcResultHandlers.print());

        verify(tokenService).validateToken(token);
        verify(userRepository).findByEmail(email);
        verify(passwordEncoder).encode(newPassword);
        verify(userRepository).save(user);
        verify(tokenService).invalidateToken(token);
    }

    @Test
    public void testResetPassword_InvalidToken_ShouldReturnBadRequest() throws Exception {
        String token = faker.internet().uuid();

        when(tokenService.validateToken(token)).thenReturn(null);

        ResetPasswordForm resetPasswordForm = new ResetPasswordForm(token, faker.internet().password());

        mockMvc.perform(post("/api/v1/resetPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(resetPasswordForm)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid or expired token"))
                .andDo(MockMvcResultHandlers.print());

        verify(tokenService).validateToken(token);
    }

    @Test
    public void testResetPassword_UserNotFound_ShouldReturnNotFound() throws Exception {
        String email = faker.internet().emailAddress();
        String token = faker.internet().uuid();

        when(tokenService.validateToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(null);

        ResetPasswordForm resetPasswordForm = new ResetPasswordForm(token, faker.internet().password());

        mockMvc.perform(post("/api/v1/resetPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(resetPasswordForm)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuário não encontrado com e-mail: " + email))
                .andDo(MockMvcResultHandlers.print());

        verify(tokenService).validateToken(token);
        verify(userRepository).findByEmail(email);
    }
}
