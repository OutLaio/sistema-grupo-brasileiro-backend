package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;

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
        UserDetails userDetails = new User(
            email, 
            password, 
            List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        when(userRepository.findByEmail(email)).thenReturn(userDetails);

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
