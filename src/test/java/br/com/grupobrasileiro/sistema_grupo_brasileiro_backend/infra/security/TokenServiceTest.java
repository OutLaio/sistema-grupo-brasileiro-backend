package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    
    @Test
    public void testValidateToken_ExpiredToken() {
        when(user.getEmail()).thenReturn("user@example.com");
        
        // Generate a token with expiration
        String token = tokenService.generateToken(user);
        
        // Set the expiration date to a past time
        String expiredToken = JWT.create()
            .withIssuer("login-auth-api")
            .withSubject(user.getEmail())
            .withExpiresAt(LocalDateTime.now().minusHours(2).toInstant(ZoneOffset.of("-03:00")))
            .sign(Algorithm.HMAC256(secret));

        String result = tokenService.validateToken(expiredToken);
        
        // Check if the expired token returns null
        assertNull(result, "The expired token should return null.");
    }

    @Test
    public void testValidateToken_MalformedToken() {
        // Define a malformed token
        String malformedToken = "malformedToken";

        String result = tokenService.validateToken(malformedToken);
        
        // Check if the malformed token returns null
        assertNull(result, "The malformed token should return null.");
    }

    @Test
    public void testGenerateToken_ExceptionHandling() {
        try {
            // Simulate an exception when generating the token
            when(user.getEmail()).thenThrow(new RuntimeException("Simulated exception"));
            
            // Try to generate a token, which should fail
            String token = tokenService.generateToken(user);
            
            // If an exception is thrown, the token should not be generated
            assertNull(token, "The token should not be generated due to an exception.");
            
        } catch (RuntimeException e) {
            // Check if the exception message is as expected
            assertTrue(e.getMessage().contains("Simulated exception"), "Exception message should contain 'Simulated exception'.");
        }
    }
}