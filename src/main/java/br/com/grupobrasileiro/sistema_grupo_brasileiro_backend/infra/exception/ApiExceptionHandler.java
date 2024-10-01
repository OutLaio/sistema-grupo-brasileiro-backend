package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Non-existent username or invalid password")
    })
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> handleBadCredentialsException(BadCredentialsException ex,
                    HttpServletRequest request) {
        return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST,
                                ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid token provided")
    })
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorMessage> handleInvalidTokenException(InvalidTokenException ex,
                    HttpServletRequest request) {
        return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid input format")
    })
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
                    HttpServletRequest request) {
        return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST,
                                        ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Null pointer exception occurred")
    })
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorMessage> handleNullPointerException(NullPointerException ex,
                    HttpServletRequest request) {
        return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST,
                                        ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @ExceptionHandler({ Exception.class, RuntimeException.class, Throwable.class })
    public ResponseEntity<ErrorMessage> handleException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error: ", ex);
        return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ErrorMessage(request, HttpStatus.INTERNAL_SERVER_ERROR,
                                        ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Entity not found")
    })
    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ErrorMessage> handleEntityNotFoundException(EntityNotFoundException ex,
                    HttpServletRequest request) {
        log.error("Api Error - " + ex);
        return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    @ExceptionHandler(EmailUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> handleUniqueViolationException(EmailUniqueViolationException ex,
                    HttpServletRequest request) {

        log.error("Api Error - " + ex);
        return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "User is not active")
    })
    @ExceptionHandler(UserIsNotActiveException.class)
    public ResponseEntity<ErrorMessage> handleUserIsNotActiveException(UserIsNotActiveException ex,
                    HttpServletRequest request) {

        log.error("Api Error - " + ex);
        return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "422", description = "Validation failed for one or more fields")
    })
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(HttpServletRequest request,
                    MethodArgumentNotValidException ex, BindingResult result) {
        log.error("Api Error - " + ex);
        return ResponseEntity
                        .status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(),
                                        result));

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Illegal argument exception occurred")
    })
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(HttpServletRequest request,
            IllegalArgumentException ex, BindingResult result) {
        log.error("Api Error - " + ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.INTERNAL_SERVER_ERROR,
                                ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid user role for the project")
    })
    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ErrorMessage> handleInvalidRoleException(InvalidRoleException ex,
                    HttpServletRequest request) {
        return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST,
                                        ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Unauthorized user")
    })
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorMessage> handleUnauthorizedException(UnauthorizedException ex,
            HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST,
                        ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleProjectNotFoundException(ProjectNotFoundException ex,
                    HttpServletRequest request) {
        log.error("Api Error - " + ex);
        return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Collaborator already assigned to the project")
    })
    @ExceptionHandler(CollaboratorAlreadyAssignedException.class)
    public ResponseEntity<ErrorMessage> handleCollaboratorAlreadyAssignedException(CollaboratorAlreadyAssignedException ex,
                    HttpServletRequest request) {

        log.error("Api Error - " + ex);
        return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Company already exists")
    })
    @ExceptionHandler(CompanyAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleCompanyAlreadyExistsException(CompanyAlreadyExistsException ex,
                    HttpServletRequest request) {

        log.error("Api Error - " + ex);
        return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "File storage error")
    })
    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorMessage> handleFileStorageException(FileStorageException ex,
                    HttpServletRequest request) {
        log.error("Api Error - " + ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.INTERNAL_SERVER_ERROR,
                                "Internal server error: " + ex.getMessage()));
    }
    
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "File not found")
    })
    @ExceptionHandler(MyFileNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleMyFileNotFoundException(MyFileNotFoundException ex,
                                                                     HttpServletRequest request) {
        log.error("Api Error - " + ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND,
                        "File not found: " + ex.getMessage()));
    }

}
