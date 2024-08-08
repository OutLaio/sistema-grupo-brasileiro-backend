package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

public class ErrorMessageTest {

    @Test
    public void testConstructorWithRequestAndStatus() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRequestURI()).thenReturn("/test-uri");
        Mockito.when(request.getMethod()).thenReturn("GET");

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Test error message";

        ErrorMessage errorMessage = new ErrorMessage(request, status, message);

        assertEquals("/test-uri", errorMessage.getPath());
        assertEquals("GET", errorMessage.getMethod());
        assertEquals(status.value(), errorMessage.getStatus());
        assertEquals(status.getReasonPhrase(), errorMessage.getStatusText());
        assertEquals(message, errorMessage.getMessage());
    }
}
