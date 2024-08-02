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
        User user = new User();
        user.setEmail(email);

        // Simulates finding user and generating token
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(tokenProvider.createRecoveryToken(any(User.class))).thenReturn("recoveryToken123");

        String token = recoveryService.generateRecoveryToken(email);

        assertEquals("recoveryToken123", token);
    }

    @Test
    void testEnvioEmail() {
        String email = "john.doe@example.com";
        User user = new User();
        user.setEmail(email);

        // Simulates finding user and generating token
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(tokenProvider.createRecoveryToken(any(User.class))).thenReturn("recoveryToken123");

        recoveryService.sendRecoveryEmail(email);

        // Verify that the email sending method was called (this depends on your implementation)
        // For example, if you have a mail service, you could use verify(mailService).sendEmail(...)
    }

    @Test
    void testFluxoCompletoRecuperacao() {
        String email = "renata@example.com";
        String token = "recoveryToken123";
        String newPassword = "newPassword123";
        User user = new User();
        user.setEmail(email);

        // Simulates finding user and generating token
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(tokenProvider.createRecoveryToken(any(User.class))).thenReturn(token);

        recoveryService.resetPassword(token, newPassword);

        // Verify if the password has been updated (you might need to check if the userRepository's save method was called)
        // For example: verify(userRepository).save(user);
    }
}
