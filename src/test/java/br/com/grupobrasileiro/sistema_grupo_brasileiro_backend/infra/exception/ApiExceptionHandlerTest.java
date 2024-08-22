package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import com.github.javafaker.Faker;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
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
    public void testEntityNotFoundException() throws Exception {
        // Configurar o mock para HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        String fakeUri = faker.internet().url();
        when(request.getRequestURI()).thenReturn(fakeUri);

        // Criar uma instância da exceção EntityNotFoundException
        EntityNotFoundException ex = new EntityNotFoundException(faker.lorem().sentence());

        // Usar reflexão para invocar o método privado
        Method method = ApiExceptionHandler.class.getDeclaredMethod("entityNotFoundException", EntityNotFoundException.class, HttpServletRequest.class);
        method.setAccessible(true);
        
        ResponseEntity<ErrorMessage> response = (ResponseEntity<ErrorMessage>) method.invoke(apiExceptionHandler, ex, request);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ex.getMessage(), response.getBody().getMessage());
        assertEquals(fakeUri, response.getBody().getPath());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), response.getBody().getStatusText());
    }

    @Test
    public void testBadCredentialsException() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/login");

        BadCredentialsException ex = new BadCredentialsException("Invalid credentials");

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleBadCredentialsException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid credentials", ex.getMessage());
        assertEquals("/login", response.getBody().getPath());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), response.getBody().getStatusText());
    }

    @Test
    public void testInvalidTokenException() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/auth");

        InvalidTokenException ex = new InvalidTokenException("Invalid token");

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleInvalidTokenException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid token", ex.getMessage());
        assertEquals("/auth", response.getBody().getPath());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), response.getBody().getStatusText());
    }

    @Test
    public void testMethodArgumentNotValidException() {
        // Mock de HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/register");

        // Mock de MethodArgumentNotValidException
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);

        // Mock de BindingResult
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getErrorCount()).thenReturn(1);
        when(bindingResult.getFieldError()).thenReturn(new FieldError("objectName", "field", "defaultMessage"));

        // Chama o método com todos os parâmetros
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.methodArgumentNotValidException(request, ex, bindingResult);

        // Verifica se os resultados estão corretos
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Campo(s) invalido(s)", response.getBody().getMessage());
        assertEquals("/register", response.getBody().getPath());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), response.getBody().getStatusText());
    }

    @Test
    public void testIllegalArgumentException() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/some-endpoint");

        IllegalArgumentException ex = new IllegalArgumentException("Invalid argument");

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.illegalArgumentException(request, ex, null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Internal server error", response.getBody().getMessage());
        assertEquals("/api/some-endpoint", response.getBody().getPath());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), response.getBody().getStatusText());
    }
    
    @Test
    public void testCollaboratorAlreadyAssignedException() throws Exception {
        // Configurar o mock para HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        String fakeUri = faker.internet().url();
        when(request.getRequestURI()).thenReturn(fakeUri);

        // Criar uma instância da exceção CollaboratorAlreadyAssignedException
        String errorMessage = faker.lorem().sentence();
        CollaboratorAlreadyAssignedException ex = new CollaboratorAlreadyAssignedException(errorMessage);

        // Chama o método handleCollaboratorAlreadyAssignedException
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleCollaboratorAlreadyAssignedException(ex, request);

        // Assertions
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(fakeUri, response.getBody().getPath());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.CONFLICT.getReasonPhrase(), response.getBody().getStatusText());
    }
    
    @Test
    public void testInvalidRoleException() throws Exception {
        // Configurar o mock para HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        String fakeUri = faker.internet().url();
        when(request.getRequestURI()).thenReturn(fakeUri);

        // Criar uma instância da exceção InvalidRoleException
        String errorMessage = faker.lorem().sentence();
        InvalidRoleException ex = new InvalidRoleException(errorMessage);

        // Chama o método handleInvalidRoleException
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleNullPointerException(ex, request);

        // Assertions
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Role do usuário inválida para o projeto", response.getBody().getMessage());
        assertEquals(fakeUri, response.getBody().getPath());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), response.getBody().getStatusText());
    }

    @Test
    public void testUnauthorizedException() throws Exception {
        // Configurar o mock para HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        String fakeUri = faker.internet().url();
        when(request.getRequestURI()).thenReturn(fakeUri);

        // Criar uma instância da exceção UnauthorizedException
        String errorMessage = faker.lorem().sentence();
        UnauthorizedException ex = new UnauthorizedException(errorMessage);

        // Chama o método handleUnauthorizedException
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleNullPointerException(ex, request);

        // Assertions
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Colaborador atribuído ao projeto com sucesso.", response.getBody().getMessage());
        assertEquals(fakeUri, response.getBody().getPath());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), response.getBody().getStatusText());
    }

    @Test
    public void testProjectNotFoundException() throws Exception {
        // Configurar o mock para HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        String fakeUri = faker.internet().url();
        when(request.getRequestURI()).thenReturn(fakeUri);

        // Criar uma instância da exceção ProjectNotFoundException
        String errorMessage = faker.lorem().sentence();
        ProjectNotFoundException ex = new ProjectNotFoundException(errorMessage);

        // Chama o método handleProjectNotFoundException
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleProjectNotFoundException(ex, request);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(fakeUri, response.getBody().getPath());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), response.getBody().getStatusText());
    }

    @Test
    public void testEmailUniqueViolationException() throws Exception {
        // Configurar o mock para HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        String fakeUri = faker.internet().url();
        when(request.getRequestURI()).thenReturn(fakeUri);

        // Criar uma instância da exceção EmailUniqueViolationException
        String errorMessage = faker.lorem().sentence();
        EmailUniqueViolationException ex = new EmailUniqueViolationException(errorMessage);

        // Chama o método uniqueViolationException
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.uniqueViolationException(ex, request);

        // Assertions
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(fakeUri, response.getBody().getPath());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.CONFLICT.getReasonPhrase(), response.getBody().getStatusText());
    }

    @Test
    public void testUserIsNotActiveException() throws Exception {
        // Configurar o mock para HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        String fakeUri = faker.internet().url();
        when(request.getRequestURI()).thenReturn(fakeUri);

        // Criar uma instância da exceção UserIsNotActiveException
        String errorMessage = faker.lorem().sentence();
        UserIsNotActiveException ex = new UserIsNotActiveException(errorMessage);

        // Chama o método userIsNotActiveException
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.userIsNotActiveException(ex, request);

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(fakeUri, response.getBody().getPath());
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.FORBIDDEN.getReasonPhrase(), response.getBody().getStatusText());
    }

    @Test
    public void testHandleException() throws Exception {
        // Configurar o mock para HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        String fakeUri = faker.internet().url();
        when(request.getRequestURI()).thenReturn(fakeUri);

        // Criar uma instância de uma exceção genérica
        Exception ex = new RuntimeException(faker.lorem().sentence());

        // Chama o método handleException
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleException(ex, request);

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Internal server error", response.getBody().getMessage());
        assertEquals(fakeUri, response.getBody().getPath());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), response.getBody().getStatusText());
    }

    @Test
    public void testHandleHttpMessageNotReadableException() throws Exception {
        // Configurar o mock para HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        String fakeUri = faker.internet().url();
        when(request.getRequestURI()).thenReturn(fakeUri);

        // Criar uma instância da exceção HttpMessageNotReadableException
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(faker.lorem().sentence());

        // Chama o método handleHttpMessageNotReadableException
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleHttpMessageNotReadableException(ex, request);

        // Assertions
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("O valor fornecido é inválido. Por favor, verifique os valores e tente novamente", response.getBody().getMessage());
        assertEquals(fakeUri, response.getBody().getPath());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), response.getBody().getStatusText());
    }
    
    @Test
    public void testHandleNullPointerException() throws Exception {
        // Configurar o mock para HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        String fakeUri = faker.internet().url();
        when(request.getRequestURI()).thenReturn(fakeUri);

        // Criar uma instância da exceção NullPointerException
        NullPointerException ex = new NullPointerException(faker.lorem().sentence());

        // Chama o método handleNullPointerException
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleNullPointerException(ex, request);

        // Assertions
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("O valor fornecido é inválido. Por favor, verifique os valores e tente novamente", response.getBody().getMessage());
        assertEquals(fakeUri, response.getBody().getPath());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), response.getBody().getStatusText());
    }

    @Test
    public void testErrorMessageConstructorSimple() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String fakeUri = "http://example.com/fake-uri";
        when(request.getRequestURI()).thenReturn(fakeUri);
        String fakeMethod = "GET";
        when(request.getMethod()).thenReturn(fakeMethod);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Test message";

        ErrorMessage errorMessage = new ErrorMessage(request, status, message);

        assertEquals(fakeUri, errorMessage.getPath());
        assertEquals(fakeMethod, errorMessage.getMethod());
        assertEquals(status.value(), errorMessage.getStatus());
        assertEquals(status.getReasonPhrase(), errorMessage.getStatusText());
        assertEquals(message, errorMessage.getMessage());
        assertNull(errorMessage.getErros()); // erros deve ser null, pois não estamos passando um BindingResult
    }
    
    @Test
    public void testErrorMessageConstructorWithBindingResult() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String fakeUri = "http://example.com/fake-uri";
        when(request.getRequestURI()).thenReturn(fakeUri);
        String fakeMethod = "POST";
        when(request.getMethod()).thenReturn(fakeMethod);

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        String message = "Invalid fields";

        // Criar BindingResult mockado
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("objectName", "field1", "Field1 error");
        FieldError fieldError2 = new FieldError("objectName", "field2", "Field2 error");
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError1, fieldError2));

        ErrorMessage errorMessage = new ErrorMessage(request, status, message, bindingResult);

        assertEquals(fakeUri, errorMessage.getPath());
        assertEquals(fakeMethod, errorMessage.getMethod());
        assertEquals(status.value(), errorMessage.getStatus());
        assertEquals(status.getReasonPhrase(), errorMessage.getStatusText());
        assertEquals(message, errorMessage.getMessage());
        assertNotNull(errorMessage.getErros());
        assertEquals(2, errorMessage.getErros().size());
        assertEquals("Field1 error", errorMessage.getErros().get("field1"));
        assertEquals("Field2 error", errorMessage.getErros().get("field2"));
    }
    
    @Test
    public void testErrorMessageInitialization() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        request.setMethod("GET");

        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = "Resource not found";

        ErrorMessage errorMessage = new ErrorMessage(request, status, message);

        assertEquals("/api/test", errorMessage.getPath());
        assertEquals("GET", errorMessage.getMethod());
        assertEquals(HttpStatus.NOT_FOUND.value(), errorMessage.getStatus());
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), errorMessage.getStatusText());
        assertEquals(message, errorMessage.getMessage());
        assertEquals(null, errorMessage.getErros()); // Verifica que erros está nulo
    }
    

    @Test
    public void testErrorMessageInitializationWithBindingResult() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        request.setMethod("POST");

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        String message = "Validation errors";

        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("object", "field1", "Field 1 error");
        FieldError fieldError2 = new FieldError("object", "field2", "Field 2 error");
        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError1, fieldError2));

        ErrorMessage errorMessage = new ErrorMessage(request, status, message, bindingResult);

        assertEquals("/api/test", errorMessage.getPath());
        assertEquals("POST", errorMessage.getMethod());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), errorMessage.getStatus());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), errorMessage.getStatusText());
        assertEquals(message, errorMessage.getMessage());

        Map<String, String> expectedErrors = new HashMap<>();
        expectedErrors.put("field1", "Field 1 error");
        expectedErrors.put("field2", "Field 2 error");

        assertEquals(expectedErrors, errorMessage.getErros());
    }
    
    @Test
    public void testToString() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRequestURI()).thenReturn("/test-uri");
        Mockito.when(request.getMethod()).thenReturn("GET");

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Test error message";

        ErrorMessage errorMessage = new ErrorMessage(request, status, message);

        // Obtém a saída do método toString()
        String toStringOutput = errorMessage.toString();

        // Verifica se a saída do toString contém os valores esperados
        assertTrue(toStringOutput.contains("/test-uri"), "toString output should contain the URI");
        assertTrue(toStringOutput.contains("GET"), "toString output should contain the HTTP method");
        assertTrue(toStringOutput.contains(String.valueOf(status.value())), "toString output should contain the status code");
        assertTrue(toStringOutput.contains(status.getReasonPhrase()), "toString output should contain the status text");
        assertTrue(toStringOutput.contains(message), "toString output should contain the error message");
    }

    	@Test
        public void testDefaultConstructor() {
            ErrorMessage errorMessage = new ErrorMessage();
            
            assertNull(errorMessage.getPath(), "Path should be null");
            assertNull(errorMessage.getMethod(), "Method should be null");
            assertEquals(0, errorMessage.getStatus(), "Status should be 0");
            assertNull(errorMessage.getStatusText(), "Status text should be null");
            assertNull(errorMessage.getMessage(), "Message should be null");
            assertNull(errorMessage.getErros(), "Errors map should be null");
        }
    }

