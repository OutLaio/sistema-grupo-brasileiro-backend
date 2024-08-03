package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ResetPasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(PasswordResetController.class)
public class PasswordResetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PasswordResetController passwordResetController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(passwordResetController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testResetPassword_Success() throws Exception {
        // Given
        String token = "validToken";
        String email = "user@example.com";
        String newPassword = "newPassword123";

        ResetPasswordForm form = new ResetPasswordForm(token, newPassword);

        User user = new User();
        user.setEmail(email);

        when(tokenService.validateToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When & Then
        mockMvc.perform(post("/resetPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isOk())
                .andExpect(content().string("Password successfully changed!"));

        verify(tokenService, times(1)).validateToken(token);
        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, times(1)).save(any(User.class));
        verify(tokenService, times(1)).invalidateToken(token);
    }

    @Test
    public void testResetPassword_InvalidToken() throws Exception {
        // Given
        String token = "invalidToken";
        String newPassword = "newPassword123";

        ResetPasswordForm form = new ResetPasswordForm(token, newPassword);

        when(tokenService.validateToken(token)).thenReturn(null);

        // When & Then
        mockMvc.perform(post("/resetPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid or expired token"));

        verify(tokenService, times(1)).validateToken(token);
        verify(userRepository, never()).findByEmail(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(tokenService, never()).invalidateToken(anyString());
    }

    @Test
    public void testResetPassword_UserNotFound() throws Exception {
        // Given
        String token = "validToken";
        String email = "user@example.com";
        String newPassword = "newPassword123";

        ResetPasswordForm form = new ResetPasswordForm(token, newPassword);

        when(tokenService.validateToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(null);

        // When & Then
        mockMvc.perform(post("/resetPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));

        verify(tokenService, times(1)).validateToken(token);
        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, never()).save(any(User.class));
        verify(tokenService, never()).invalidateToken(anyString());
    }
}
