package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public class ErrorMessageTest {

    private HttpServletRequest request;
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        bindingResult = mock(BindingResult.class);
    }

    @Test
    void testErrorMessageConstructorWithoutBindingResult() {
        // Arrange
        String path = "/some/path";
        String method = "GET";
        String message = "Some error occurred";
        when(request.getRequestURI()).thenReturn(path);
        when(request.getMethod()).thenReturn(method);

        // Act
        ErrorMessage errorMessage = new ErrorMessage(request, HttpStatus.BAD_REQUEST, message);

        // Assert
        assertNotNull(errorMessage);
        assertEquals(path, errorMessage.getPath());
        assertEquals(method, errorMessage.getMethod());
        assertEquals(HttpStatus.BAD_REQUEST.value(), errorMessage.getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), errorMessage.getStatusText());
        assertEquals(message, errorMessage.getMessage());
        assertNull(errorMessage.getErros()); // Erros deve ser nulo
    }

    @Test
    void testErrorMessageConstructorWithBindingResult() {
        // Arrange
        String path = "/some/path";
        String method = "POST";
        String message = "Validation failed";
        when(request.getRequestURI()).thenReturn(path);
        when(request.getMethod()).thenReturn(method);

        // Simular o BindingResult
        FieldError fieldError = mock(FieldError.class);
        when(fieldError.getField()).thenReturn("username");
        when(fieldError.getDefaultMessage()).thenReturn("Username is required");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        // Act
        ErrorMessage errorMessage = new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, message, bindingResult);

        // Assert
        assertNotNull(errorMessage);
        assertEquals(path, errorMessage.getPath());
        assertEquals(method, errorMessage.getMethod());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), errorMessage.getStatus());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), errorMessage.getStatusText());
        assertEquals(message, errorMessage.getMessage());

        // Verificar se erros foram adicionados corretamente
        Map<String, String> errors = errorMessage.getErros();
        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertEquals("Username is required", errors.get("username"));
    }

    @Test
    void testAddErrorsWithMultipleFieldErrors() {
        // Arrange
        String path = "/another/path";
        String method = "PUT";
        String message = "Multiple validation errors";
        when(request.getRequestURI()).thenReturn(path);
        when(request.getMethod()).thenReturn(method);

        // Simular o BindingResult com múltiplos erros de campo
        FieldError fieldError1 = mock(FieldError.class);
        FieldError fieldError2 = mock(FieldError.class);
        when(fieldError1.getField()).thenReturn("username");
        when(fieldError1.getDefaultMessage()).thenReturn("Username is required");
        when(fieldError2.getField()).thenReturn("email");
        when(fieldError2.getDefaultMessage()).thenReturn("Email is invalid");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        // Act
        ErrorMessage errorMessage = new ErrorMessage(request, HttpStatus.BAD_REQUEST, message, bindingResult);

        // Assert
        Map<String, String> errors = errorMessage.getErros();
        assertNotNull(errors);
        assertEquals(2, errors.size());
        assertEquals("Username is required", errors.get("username"));
        assertEquals("Email is invalid", errors.get("email"));
    }
}
