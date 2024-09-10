package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ResetPasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.InvalidTokenException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PasswordResetControllerTest {

    @InjectMocks
    private PasswordResetController passwordResetController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testResetPassword_Success() {
        // Arrange
        String token = faker.internet().uuid();
        String email = faker.internet().emailAddress();
        String newPassword = faker.internet().password();
        
        ResetPasswordForm form = new ResetPasswordForm(newPassword);

        User user = new User();
        user.setEmail(email);
        user.setPassword(faker.internet().password());

        when(tokenService.validateToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.encode(newPassword)).thenReturn(faker.internet().password());

        // Act
        ResponseEntity<String> response = passwordResetController.resetPassword(token, form);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Password successfully changed!", response.getBody());
        verify(userRepository).save(user);
        verify(tokenService).invalidateToken(token);
    }

    @Test
    public void testResetPassword_InvalidToken() {
        // Arrange
        String token = faker.internet().uuid();
        when(tokenService.validateToken(token)).thenReturn(null);

        ResetPasswordForm form = new ResetPasswordForm(faker.internet().password());

        // Act & Assert
        try {
            passwordResetController.resetPassword(token, form);
        } catch (InvalidTokenException e) {
            assertEquals("Token inválido ou expirado", e.getMessage());
        }
    }

    @Test
    public void testResetPassword_UserNotFound() {
        // Arrange
        String token = faker.internet().uuid();
        String email = faker.internet().emailAddress();
        when(tokenService.validateToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(null);

        ResetPasswordForm form = new ResetPasswordForm(faker.internet().password());

        // Act & Assert
        try {
            passwordResetController.resetPassword(token, form);
        } catch (EntityNotFoundException e) {
            assertEquals("Usuário não encontrado com e-mail: " + email, e.getMessage());
        }
    }
}

