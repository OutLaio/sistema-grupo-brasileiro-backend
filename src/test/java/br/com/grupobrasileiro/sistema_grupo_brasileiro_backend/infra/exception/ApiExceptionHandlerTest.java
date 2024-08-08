package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiExceptionHandlerTest {

    @InjectMocks
    private ApiExceptionHandler apiExceptionHandler;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testHandleInvalidTokenException() {
        // Mock HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        String fakeUri = faker.internet().url();
        String fakeMethod = faker.options().option("GET", "POST", "PUT", "DELETE");
        when(request.getRequestURI()).thenReturn(fakeUri);
        when(request.getMethod()).thenReturn(fakeMethod);

        // Mock InvalidTokenException
        InvalidTokenException ex = new InvalidTokenException("Invalid token");

        // Call the handler method
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleInvalidTokenException(ex, request);

        // Assertions
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Token inválido", response.getBody().getMessage());
        assertEquals(fakeUri, response.getBody().getPath());
        assertEquals(fakeMethod, response.getBody().getMethod());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), response.getBody().getStatusText());
    }
    
    @Test
    public void testHandleEmailUniqueViolationException() {
        // Mock HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        String fakeUri = faker.internet().url();
        String fakeMethod = faker.options().option("GET", "POST", "PUT", "DELETE");
        when(request.getRequestURI()).thenReturn(fakeUri);
        when(request.getMethod()).thenReturn(fakeMethod);

        // Mock EmailUniqueViolationException
        EmailUniqueViolationException ex = new EmailUniqueViolationException("Email already exists");

        // Call the handler method
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.uniqueViolationException(ex, request);

        // Assertions
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Email already exists", response.getBody().getMessage());
        assertEquals(fakeUri, response.getBody().getPath());
        assertEquals(fakeMethod, response.getBody().getMethod());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.CONFLICT.getReasonPhrase(), response.getBody().getStatusText());
    }

}