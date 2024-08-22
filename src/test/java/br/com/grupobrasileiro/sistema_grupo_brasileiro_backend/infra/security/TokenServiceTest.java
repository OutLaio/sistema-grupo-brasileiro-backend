package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;

public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private User user;

    private String secret = "mySecret";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenService.secret = secret;
    }

    @Test
    public void testGenerateToken() {
        when(user.getEmail()).thenReturn("user@example.com");

        String token = tokenService.generateToken(user);

     // Check if the token is generated and contains the expected information
        DecodedJWT decodedJWT = JWT.decode(token);
        assertEquals("login-auth-api", decodedJWT.getIssuer());
        assertEquals("user@example.com", decodedJWT.getSubject());
    }

    @Test
    public void testValidateToken_ValidToken() {
        when(user.getEmail()).thenReturn("user@example.com");
        String token = tokenService.generateToken(user);

        String result = tokenService.validateToken(token);

        assertEquals("user@example.com", result);
    }

    @Test
    public void testValidateToken_InvalidToken() {
    	// Create an invalid token
        String invalidToken = "invalidToken";

        String result = tokenService.validateToken(invalidToken);

        assertNull(result);
    }

    @Test
    public void testInvalidateToken() {
        when(user.getEmail()).thenReturn("user@example.com");
        String token = tokenService.generateToken(user);

        tokenService.invalidateToken(token);

        String result = tokenService.validateToken(token);

        assertNull(result);
    }
}
