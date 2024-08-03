package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
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
        User user = new User(); // Configure o usuário conforme necessário
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(user);

        // Act
        UserDetails result = authorizationService.loadUserByUsername(email);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getUsername());
    }

    @Test
    void loadUserByUsername_UserDoesNotExist_ReturnsNull() {
        String emailLogin = "nonexistent@example.com";

        // Configurar o mock para retornar null quando o usuário não é encontrado
        when(userRepository.findByEmail(emailLogin)).thenReturn(null);

        // Verificar que o retorno é null ou algum comportamento adequado para quando
        // não há usuário
        assertNull(authorizationService.loadUserByUsername(emailLogin));
    }
}