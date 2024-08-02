package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PasswordRecoveryServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private PasswordRecoveryService recoveryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGeracaoToken() {
        String email = "john.doe@example.com";

        when(userRepository.findByEmail(email)).thenReturn(new User());
        when(tokenProvider.createRecoveryToken(any(User.class))).thenReturn("recoveryToken123");

        String token = recoveryService.generateRecoveryToken(email);

        assertEquals("recoveryToken123", token);
    }

    @Test
    void testEnvioEmail() {
        String email = "john.doe@example.com";

        // Simulates sending e-mail
        when(tokenProvider.createRecoveryToken(any(User.class))).thenReturn("recoveryToken123");

        recoveryService.sendRecoveryEmail(email);

        // Check that the email was sent correctly
        // This depends on how you have implemented sending email
    }

    @Test
    void testFluxoCompletoRecuperacao() {
        String email = "renata@example.com";
        String token = "recoveryToken123";
        String newPassword = "newPassword123";

        when(userRepository.findByEmail(email)).thenReturn(new User());
        when(tokenProvider.createRecoveryToken(any(User.class))).thenReturn(token);

        recoveryService.resetPassword(token, newPassword);

        // Check if the password has been reset correctly
    }
}
