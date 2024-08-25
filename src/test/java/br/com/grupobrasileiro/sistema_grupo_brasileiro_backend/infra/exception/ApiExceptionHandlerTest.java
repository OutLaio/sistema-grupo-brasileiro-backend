package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.github.javafaker.Faker;

import jakarta.servlet.http.HttpServletRequest;

public class ApiExceptionHandlerTest {

	private final Faker faker = new Faker();
    private final ApiExceptionHandler apiExceptionHandler = new ApiExceptionHandler();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleBadCredentialsExceptionWithFaker() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(faker.internet().url());

        BadCredentialsException ex = new BadCredentialsException(faker.lorem().sentence());

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleBadCredentialsException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Usuário inexistente ou senha inválida", response.getBody().getMessage());
        assertEquals(faker.internet().url(), response.getBody().getPath());
    }

    @Test
    public void testHandleInvalidTokenExceptionWithFaker() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(faker.internet().url());

        InvalidTokenException ex = new InvalidTokenException(faker.lorem().sentence());

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleInvalidTokenException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Token inválido", response.getBody().getMessage());
        assertEquals(faker.internet().url(), response.getBody().getPath());
    }

    @Test
    public void testHandleHttpMessageNotReadableExceptionWithFaker() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(faker.internet().url());

        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(faker.lorem().sentence());

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleHttpMessageNotReadableException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("O valor fornecido é inválido. Por favor, verifique os valores e tente novamente", response.getBody().getMessage());
        assertEquals(faker.internet().url(), response.getBody().getPath());
    }

    @Test
    public void testHandleNullPointerExceptionWithFaker() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(faker.internet().url());

        NullPointerException ex = new NullPointerException(faker.lorem().sentence());

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleNullPointerException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("O valor fornecido é inválido. Por favor, verifique os valores e tente novamente", response.getBody().getMessage());
        assertEquals(faker.internet().url(), response.getBody().getPath());
    }

    @Test
    public void testHandleEntityNotFoundExceptionWithFaker() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String fakeUrl = faker.internet().url();
        String fakeMessage = faker.lorem().sentence();

        when(request.getRequestURI()).thenReturn(fakeUrl);

        EntityNotFoundException ex = new EntityNotFoundException(fakeMessage);

        // Usando reflexão para acessar o método privado
        Method method = ApiExceptionHandler.class.getDeclaredMethod("handleEntityNotFoundException", EntityNotFoundException.class, HttpServletRequest.class);
        method.setAccessible(true);

        ResponseEntity<ErrorMessage> response = (ResponseEntity<ErrorMessage>) method.invoke(apiExceptionHandler, ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(fakeMessage, response.getBody().getMessage());
        assertEquals(fakeUrl, response.getBody().getPath());
    }

    @Test
    public void testHandleUniqueViolationExceptionWithFaker() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(faker.internet().url());

        EmailUniqueViolationException ex = new EmailUniqueViolationException(faker.lorem().sentence());

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleUniqueViolationException(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(faker.lorem().sentence(), response.getBody().getMessage());
        assertEquals(faker.internet().url(), response.getBody().getPath());
    }

    @Test
    public void testHandleUserIsNotActiveExceptionWithFaker() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(faker.internet().url());

        UserIsNotActiveException ex = new UserIsNotActiveException(faker.lorem().sentence());

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleUserIsNotActiveException(ex, request);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(faker.lorem().sentence(), response.getBody().getMessage());
        assertEquals(faker.internet().url(), response.getBody().getPath());
    }

    @Test
    public void testHandleMethodArgumentNotValidExceptionWithFaker() {
        // Mocking HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);
        String urlPath = faker.internet().url(); // Generating the URL only once
        when(request.getRequestURI()).thenReturn(urlPath);

        // Mocking MethodArgumentNotValidException and BindingResult
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldError()).thenReturn(new FieldError("object", "field", faker.lorem().sentence()));

        // Calling the handler method with all required parameters
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleMethodArgumentNotValidException(request, ex, bindingResult);

        // Asserting the response
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Campo(s) invalido(s)", response.getBody().getMessage());
        assertEquals(urlPath, response.getBody().getPath()); // Use the same URL that was mocked
    }


    @Test
    public void testHandleIllegalArgumentExceptionWithFaker() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(faker.internet().url());

        IllegalArgumentException ex = new IllegalArgumentException(faker.lorem().sentence());

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleIllegalArgumentException(request, ex, null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Internal server error", response.getBody().getMessage());
        assertEquals(faker.internet().url(), response.getBody().getPath());
    }

    @Test
    public void testHandleInvalidRoleExceptionWithFaker() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(faker.internet().url());

        InvalidRoleException ex = new InvalidRoleException(faker.lorem().sentence());

        // Use reflexão para acessar o método privado
        java.lang.reflect.Method method = ApiExceptionHandler.class.getDeclaredMethod("handleInvalidRoleException", InvalidRoleException.class, HttpServletRequest.class);
        method.setAccessible(true);

        ResponseEntity<ErrorMessage> response = (ResponseEntity<ErrorMessage>) method.invoke(apiExceptionHandler, ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Role do usuário inválida para o projeto", response.getBody().getMessage());
        assertEquals(faker.internet().url(), response.getBody().getPath());
    }

    @Test
    public void testHandleUnauthorizedExceptionWithFaker() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(faker.internet().url());

        UnauthorizedException ex = new UnauthorizedException(faker.lorem().sentence());

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Colaborador atribuído ao projeto com sucesso.", response.getBody().getMessage());
        assertEquals(faker.internet().url(), response.getBody().getPath());
    }

    @Test
    public void testHandleProjectNotFoundExceptionWithFaker() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(faker.internet().url());

        ProjectNotFoundException ex = new ProjectNotFoundException(faker.lorem().sentence());

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleProjectNotFoundException(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(faker.lorem().sentence(), response.getBody().getMessage());
        assertEquals(faker.internet().url(), response.getBody().getPath());
    }

    @Test
    public void testHandleCollaboratorAlreadyAssignedExceptionWithFaker() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(faker.internet().url());

        CollaboratorAlreadyAssignedException ex = new CollaboratorAlreadyAssignedException(faker.lorem().sentence());

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleCollaboratorAlreadyAssignedException(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(faker.lorem().sentence(), response.getBody().getMessage());
        assertEquals(faker.internet().url(), response.getBody().getPath());
    }

    @Test
    public void testHandleCompanyAlreadyExistsExceptionWithFaker() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(faker.internet().url());

        CompanyAlreadyExistsException ex = new CompanyAlreadyExistsException(faker.lorem().sentence());

        ResponseEntity<ErrorMessage> response = apiExceptionHandler.handleCompanyAlreadyExistsException(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(faker.lorem().sentence(), response.getBody().getMessage());
        assertEquals(faker.internet().url(), response.getBody().getPath());
    }
}
