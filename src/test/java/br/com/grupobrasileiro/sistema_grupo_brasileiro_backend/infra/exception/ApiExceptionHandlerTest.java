package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.javafaker.Faker;

import jakarta.servlet.http.HttpServletRequest;

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
    	// Implementation of the test for InvalidTokenException
    }

    @Test
    public void testHandleEmailUniqueViolationException() {
    	// Test implementation for EmailUniqueViolationException
    }

    @Test
    public void testHandleIllegalArgumentException() {
        // Mock HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        String fakeUri = faker.internet().url();
        String fakeMethod = faker.options().option("GET", "POST", "PUT", "DELETE");
        when(request.getRequestURI()).thenReturn(fakeUri);
        when(request.getMethod()).thenReturn(fakeMethod);

        // Mock IllegalArgumentException
        String fakeMessage = faker.lorem().sentence();
        IllegalArgumentException ex = new IllegalArgumentException(fakeMessage);

        // Call the handler method
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.illegalArgumentException(request, ex, null);

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Internal server error", response.getBody().getMessage());
        assertEquals(fakeUri, response.getBody().getPath());
        assertEquals(fakeMethod, response.getBody().getMethod());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), response.getBody().getStatusText());
    }
}
