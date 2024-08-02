package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.LoginForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSucesso() {
        LoginForm loginForm = new LoginForm("joao.silva@example.com", "password123");

        // Simulate an existing user with the correct password
        User user = new User();
        user.setEmail("joao.silva@example.com");
        user.setPassword("password123"); // The password must be the password encoded in practice

        when(userRepository.findByEmail(loginForm.getEmail())).thenReturn(user);
        // Simulate correct authentication
        when(loginService.authenticate(loginForm.getEmail(), loginForm.getPassword())).thenReturn("token123");

        String token = loginService.authenticate(loginForm.getEmail(), loginForm.getPassword());

        assertNotNull(token);
        assertEquals("token123", token);
    }

    @Test
    void testLoginFalha() {
        LoginForm loginForm = new LoginForm("joao.silva@example.com", "wrongpassword");

        // Simulate an existing user with incorrect password
        User user = new User();
        user.setEmail("joao.silva@example.com");
        user.setPassword("correctpassword"); // The password must be the password encoded in practice

        when(userRepository.findByEmail(loginForm.getEmail())).thenReturn(user);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            loginService.authenticate(loginForm.getEmail(), loginForm.getPassword());
        });

        assertEquals("Autenticação falhou", thrown.getMessage());
    }
}
