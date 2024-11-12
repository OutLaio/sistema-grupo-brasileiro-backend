package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.auth0.jwt.JWT;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.RecoveryToken;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.RecoveryTokenRepository;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private RecoveryTokenRepository revokedTokenRepository;

    private final String secret = "mySecretKey"; 

    @BeforeEach
    public void setup() {
        // Injecta o valor da propriedade `secret` no `TokenService`
        ReflectionTestUtils.setField(tokenService, "secret", secret);
    }

    @Test
    public void testGenerateToken_Success() {
        User user = new User();
        user.setEmail("test@example.com");

        String token = tokenService.generateToken(user);

        assertNotNull(token);
        assertEquals(user.getEmail(), JWT.decode(token).getSubject());
        assertEquals("login-auth-api", JWT.decode(token).getIssuer());
    }

    @Test
    public void testGenerateToken_Failure() {
        User user = new User();
        user.setEmail("test@example.com");

        
        ReflectionTestUtils.setField(tokenService, "secret", null);

        assertThrows(RuntimeException.class, () -> tokenService.generateToken(user));
    }

    @Test
    public void testValidateToken_Success() {
        User user = new User();
        user.setEmail("test@example.com");
        
        String token = tokenService.generateToken(user);

        when(revokedTokenRepository.existsByToken(token)).thenReturn(false);

        String subject = tokenService.validateToken(token);

        assertEquals(user.getEmail(), subject);
    }

    @Test
    public void testValidateToken_Failure_InvalidToken() {
        String invalidToken = "invalid.token.value";

        String subject = tokenService.validateToken(invalidToken);

        assertNull(subject);
    }

    @Test
    public void testValidateToken_Failure_RevokedToken() {
        User user = new User();
        user.setEmail("test@example.com");

        String token = tokenService.generateToken(user);

        when(revokedTokenRepository.existsByToken(token)).thenReturn(true);

        String subject = tokenService.validateToken(token);

        assertNull(subject);
    }

    @Test
    public void testInvalidateToken_TokenNotRevoked() {
        String token = "valid.token.value";

        when(revokedTokenRepository.existsByToken(token)).thenReturn(false);

        tokenService.invalidateToken(token);

        verify(revokedTokenRepository, times(1)).save(any(RecoveryToken.class));
    }

    @Test
    public void testInvalidateToken_TokenAlreadyRevoked() {
        String token = "valid.token.value";

        when(revokedTokenRepository.existsByToken(token)).thenReturn(true);

        tokenService.invalidateToken(token);

        verify(revokedTokenRepository, never()).save(any(RecoveryToken.class));
    }

    @Test
    public void testGenerateExpirationDate() {
        // Usa reflection para chamar o método privado generateExpirationDate
        Instant expirationDate = ReflectionTestUtils.invokeMethod(tokenService, "generateExpirationDate");

        assertNotNull(expirationDate);

        Instant expectedExpiration = LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
        assertTrue(expirationDate.isAfter(Instant.now()));
        assertTrue(expirationDate.isBefore(expectedExpiration.plusSeconds(10))); // margem de erro de 10s
    }

    @Test
    public void testIsTokenRevoked() {
        String token = "test.token.value";

        when(revokedTokenRepository.existsByToken(token)).thenReturn(true);

        boolean isRevoked = ReflectionTestUtils.invokeMethod(tokenService, "isTokenRevoked", token);

        assertTrue(isRevoked);
    }

    @Test
    public void testIsTokenRevoked_NotRevoked() {
        String token = "test.token.value";

        when(revokedTokenRepository.existsByToken(token)).thenReturn(false);

        boolean isRevoked = ReflectionTestUtils.invokeMethod(tokenService, "isTokenRevoked", token);

        assertFalse(isRevoked);
    }
}
