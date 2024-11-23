package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import jakarta.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApiExceptionHandlerTest {

    private ApiExceptionHandler apiExceptionHandler;
    private HttpServletRequest request;

    @BeforeEach
    public void setUp() {
        apiExceptionHandler = new ApiExceptionHandler();
        request = mock(HttpServletRequest.class);
    }

    @Test
    public void testHandleBadCredentialsException() {
        BadCredentialsException ex = new BadCredentialsException("Invalid username or password");
        when(request.getRequestURI()).thenReturn("/login");
        when(request.getMethod()).thenReturn("POST");

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleBadCredentialsException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody().getMessage());
    }

    @Test
    public void testHandleInvalidTokenException() {
        InvalidTokenException ex = new InvalidTokenException("Invalid token");
        when(request.getRequestURI()).thenReturn("/token");
        when(request.getMethod()).thenReturn("GET");

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleInvalidTokenException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid token", response.getBody().getMessage());
    }

    @Test
    public void testHandleHttpMessageNotReadableException() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Message not readable");
        when(request.getRequestURI()).thenReturn("/data");
        when(request.getMethod()).thenReturn("POST");

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleHttpMessageNotReadableException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Message not readable", response.getBody().getMessage());
    }

    @Test
    public void testHandleNullPointerException() {
        NullPointerException ex = new NullPointerException("Null pointer exception occurred");
        when(request.getRequestURI()).thenReturn("/null");
        when(request.getMethod()).thenReturn("GET");

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleNullPointerException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Null pointer exception occurred", response.getBody().getMessage());
    }

   
    @Test
    public void testHandleEmailUniqueViolationException() {
        EmailUniqueViolationException ex = new EmailUniqueViolationException("Email already exists");
        when(request.getRequestURI()).thenReturn("/email");
        when(request.getMethod()).thenReturn("POST");

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleUniqueViolationException(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Email already exists", response.getBody().getMessage());
    }

    @Test
    public void testHandleUserIsNotActiveException() {
        UserIsNotActiveException ex = new UserIsNotActiveException("User is not active");
        when(request.getRequestURI()).thenReturn("/user");
        when(request.getMethod()).thenReturn("GET");

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleUserIsNotActiveException(ex, request);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("User is not active", response.getBody().getMessage());
    }

    @Test
    public void testHandleMethodArgumentNotValidException() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult result = mock(BindingResult.class);
        when(request.getRequestURI()).thenReturn("/validate");
        when(request.getMethod()).thenReturn("POST");

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleMethodArgumentNotValidException(request, ex, result);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        // Verifique se a mensagem correta é retornada (ajuste conforme necessário)
        assertEquals(ex.getMessage(), response.getBody().getMessage());
    }

    @Test
    public void testHandleException() {
        RuntimeException ex = new RuntimeException("Unexpected error");
        when(request.getRequestURI()).thenReturn("/unexpected");
        when(request.getMethod()).thenReturn("GET");

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleException(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error", response.getBody().getMessage());
    }

    @Test
    public void testHandleIllegalArgumentException() {
        // Arrange: Create the IllegalArgumentException instance
        IllegalArgumentException ex = new IllegalArgumentException("Illegal argument");
        
        // Mock the request to return a specific URI and method
        when(request.getRequestURI()).thenReturn("/illegal");
        when(request.getMethod()).thenReturn("GET");

        // Act: Call the exception handler method
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleIllegalArgumentException(request, ex);

        // Assert: Check the response status and body
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());  // Verify HTTP status
        assertEquals("Illegal argument", response.getBody().getMessage());  // Verify the error message
    }

    @Test
    public void testHandleCollaboratorAlreadyAssignedException() {
        CollaboratorAlreadyAssignedException ex = new CollaboratorAlreadyAssignedException("Collaborator already assigned");
        when(request.getRequestURI()).thenReturn("/collaborator");
        when(request.getMethod()).thenReturn("POST");

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleCollaboratorAlreadyAssignedException(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Collaborator already assigned", response.getBody().getMessage());
    }

    @Test
    public void testHandleFileStorageException() {
        FileStorageException ex = new FileStorageException("File storage error");
        when(request.getRequestURI()).thenReturn("/file");
        when(request.getMethod()).thenReturn("POST");

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleFileStorageException(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error: File storage error", response.getBody().getMessage());
    }

    @Test
    public void testHandleMyFileNotFoundException() {
        MyFileNotFoundException ex = new MyFileNotFoundException("File not found");
        when(request.getRequestURI()).thenReturn("/myfile");
        when(request.getMethod()).thenReturn("GET");

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleMyFileNotFoundException(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("File not found: File not found", response.getBody().getMessage());
    }
}
