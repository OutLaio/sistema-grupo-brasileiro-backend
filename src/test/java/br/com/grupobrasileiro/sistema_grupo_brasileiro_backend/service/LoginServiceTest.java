package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.UserService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.security.JwtTokenProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSucesso() {
        String email = "renata@example.com";
        String password = "password123";

        when(userRepository.findByEmail(email)).thenReturn(new User());
        when(tokenProvider.createToken(any(User.class))).thenReturn("token123");

        String token = loginService.login(email, password);

        assertEquals("token123", token);
    }

    @Test
    void testLoginFalha() {
        String email = "renata@example.com";
        String password = "wrongpassword";

        when(userRepository.findByEmail(email)).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            loginService.login(email, password);
        });

        assertEquals("Authentication failed", exception.getMessage());
    }
}
