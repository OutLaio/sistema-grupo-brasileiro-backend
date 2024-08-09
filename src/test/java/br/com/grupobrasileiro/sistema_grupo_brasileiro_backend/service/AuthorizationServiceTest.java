package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorizationServiceTest {

    private AuthorizationService authorizationService;
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        authorizationService = new AuthorizationService();
        authorizationService.repository = userRepository;
    }

    @Test
    public void loadUserByUsername_UserExists_ReturnsUserDetails() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        User user = new User(
            "John", "Doe", "123456789", "IT", "Developer", "1234567890", email, password, 1
        );
        user.setActive(true);

        when(userRepository.findByEmail(email)).thenReturn(user);

        // Act
        UserDetails result = authorizationService.loadUserByUsername(email);

        // Assert
        assertNotNull(result, "UserDetails should not be null.");
        assertEquals(email, result.getUsername(), "Username should match.");
        assertEquals(password, result.getPassword(), "Password should match.");
    }

    @Test
    void loadUserByUsername_UserDoesNotExist_ThrowsException() {
        // Arrange
        String emailLogin = "nonexistent@example.com";

        when(userRepository.findByEmail(emailLogin)).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> 
            authorizationService.loadUserByUsername(emailLogin),
            "Expected UsernameNotFoundException to be thrown."
        );
    }
}
