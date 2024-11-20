package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Non-existent username or invalid password")
    })
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        String endpoint = request.getRequestURI();
        String method = request.getMethod();
        String clientIp = request.getRemoteAddr();
        log.warn("Falha de autenticação no endpoint: [{} {}], IP do cliente: {}. Motivo: {}", method, endpoint, clientIp, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid token provided")
    })
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorMessage> handleInvalidTokenException(InvalidTokenException ex, HttpServletRequest request) {
        String endpoint = request.getRequestURI();
        String method = request.getMethod();
        String clientIp = request.getRemoteAddr();
        log.warn("Token inválido ao acessar endpoint: [{} {}], IP do cliente: {}. Detalhes: {}", method, endpoint, clientIp, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid input format")
    })
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String endpoint = request.getRequestURI();
        String method = request.getMethod();
        String clientIp = request.getRemoteAddr();
        log.warn("Formato de entrada inválido ao acessar endpoint: [{} {}], IP do cliente: {}. Detalhes: {}",method, endpoint, clientIp, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Null pointer exception occurred")
    })
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorMessage> handleNullPointerException(NullPointerException ex, HttpServletRequest request) {
        String endpoint = request.getRequestURI();
        String method = request.getMethod();
        String clientIp = request.getRemoteAddr();
        log.warn("Erro de ponteiro nulo ao acessar [{} {}]. IP do cliente: {}. Detalhes: {}",method, endpoint, clientIp, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ExceptionHandler({ Exception.class, RuntimeException.class, Throwable.class })
    public ResponseEntity<ErrorMessage> handleException(Exception ex, HttpServletRequest request) {
        String endpoint = request.getRequestURI();
        String method = request.getMethod();
        String clientIp = request.getRemoteAddr();
        log.error("Erro inesperado ao acessar [{} {}]. IP do cliente: {}. Detalhes: {}", method, endpoint, clientIp, ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorMessage(request, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Entity not found")
    })
    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ErrorMessage> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        String endpoint = request.getRequestURI();
        String method = request.getMethod();
        log.warn("Entidade não encontrada ao acessar [{} {}]. Detalhes: {}", method, endpoint, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    @ExceptionHandler(EmailUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> handleUniqueViolationException(EmailUniqueViolationException ex, HttpServletRequest request) {
        String endpoint = request.getRequestURI();
        log.warn("Conflito de e-mail ao tentar registrar. Endpoint: [{}]. Detalhes: {}", endpoint, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "User is not active")
    })
    @ExceptionHandler(UserIsNotActiveException.class)
    public ResponseEntity<ErrorMessage> handleUserIsNotActiveException(UserIsNotActiveException ex, HttpServletRequest request) {
        String endpoint = request.getRequestURI();
        String method = request.getMethod();
        log.warn("Usuário inativo ao tentar acessar [{} {}]. Detalhes: {}",
                method, endpoint, ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "422", description = "Validation failed for one or more fields")
    })
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(HttpServletRequest request,
                    MethodArgumentNotValidException ex, BindingResult result) {
        String endpoint = request.getRequestURI();
        log.warn("Erro de validação ao acessar [{}]. Detalhes: {}", endpoint, ex.getMessage());

        String details = result.getFieldErrors().stream().map(
                fieldError -> String.format("Campo '%s': %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.joining("; "));

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Falha na validação: " + details));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Illegal argument exception occurred")
    })
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException ex) {
        String endpoint = request.getRequestURI();
        String method = request.getMethod();
        log.error("Erro de argumento inválido ao acessar [{} {}]. Detalhes: {}", method, endpoint, ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorMessage(request, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Collaborator already assigned to the project")
    })
    @ExceptionHandler(CollaboratorAlreadyAssignedException.class)
    public ResponseEntity<ErrorMessage> handleCollaboratorAlreadyAssignedException(CollaboratorAlreadyAssignedException ex, HttpServletRequest request) {
        String endpoint = request.getRequestURI();
        log.warn("Conflito ao atribuir colaborador no endpoint [{}]. Detalhes: {}", endpoint, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "File storage error")
    })
    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorMessage> handleFileStorageException(FileStorageException ex, HttpServletRequest request) {
        String endpoint = request.getRequestURI();
        log.error("Erro ao armazenar o arquivo no endpoint [{}]. Detalhes: {}", endpoint, ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorMessage(request, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error: " + ex.getMessage()));
    }
    
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "File not found")
    })
    @ExceptionHandler(MyFileNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleMyFileNotFoundException(MyFileNotFoundException ex, HttpServletRequest request) {
        String endpoint = request.getRequestURI();
        log.warn("Tentativa de acesso a arquivo inexistente no endpoint [{}]. Detalhes: {}", endpoint, ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, "File not found: " + ex.getMessage()));
    }

}

