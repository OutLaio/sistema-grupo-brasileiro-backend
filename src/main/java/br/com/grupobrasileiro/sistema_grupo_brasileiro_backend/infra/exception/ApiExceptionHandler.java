package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ErrorMessage> handleBadCredentialsException(BadCredentialsException ex,
                        HttpServletRequest request) {
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST,
                                                "Usuário inexistente ou senha inválida"));
        }

        @ExceptionHandler(InvalidTokenException.class)
        public ResponseEntity<ErrorMessage> handleInvalidTokenException(InvalidTokenException ex,
                        HttpServletRequest request) {
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Token inválido"));
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
                        HttpServletRequest request) {
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST,
                                                "O valor fornecido é inválido. Por favor, verifique os valores e tente novamente"));
        }

        @ExceptionHandler(NullPointerException.class)
        public ResponseEntity<ErrorMessage> handleNullPointerException(NullPointerException ex,
                        HttpServletRequest request) {
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST,
                                                "O valor fornecido é inválido. Por favor, verifique os valores e tente novamente"));
        }

        @ExceptionHandler({ Exception.class, RuntimeException.class, Throwable.class })
        public ResponseEntity<ErrorMessage> handleException(Exception ex, HttpServletRequest request) {
                log.error("Unexpected error: ", ex);
                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.INTERNAL_SERVER_ERROR,
                                                "Internal server error"));
        }

        @ExceptionHandler(EntityNotFoundException.class)
        private ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex,
                        HttpServletRequest request) {
                log.error("Api Error - " + ex);
                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
        }

        @ExceptionHandler(EmailUniqueViolationException.class)
        public ResponseEntity<ErrorMessage> uniqueViolationException(EmailUniqueViolationException ex,
                        HttpServletRequest request) {

                log.error("Api Error - " + ex);
                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.CONFLICT, ex.getMessage()));
        }

        @ExceptionHandler(UserIsNotActiveException.class)
        public ResponseEntity<ErrorMessage> userIsNotActiveException(UserIsNotActiveException ex,
                        HttpServletRequest request) {

                log.error("Api Error - " + ex);
                return ResponseEntity
                                .status(HttpStatus.FORBIDDEN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, ex.getMessage()));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorMessage> methodArgumentNotValidException(HttpServletRequest request,
                        MethodArgumentNotValidException ex, BindingResult result) {
                log.error("Api Error - " + ex);
                return ResponseEntity
                                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) invalido(s)",
                                                result));

        }
        
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorMessage> illegalArgumentException(HttpServletRequest request,
        		IllegalArgumentException ex, BindingResult result) {
                log.error("Api Error - " + ex);
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ErrorMessage(request, HttpStatus.INTERNAL_SERVER_ERROR,
                                        "Internal server error"));
        }
        
        @ExceptionHandler(InvalidRoleException.class)
        public ResponseEntity<ErrorMessage> handleNullPointerException(InvalidRoleException ex,
                        HttpServletRequest request) {
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST,
                                                "Role do usuário inválida para o projeto"));
        }
        
        @ExceptionHandler(UnauthorizedException.class)
        public ResponseEntity<ErrorMessage> handleNullPointerException(UnauthorizedException ex,
        		HttpServletRequest request) {
        	return ResponseEntity
        			.status(HttpStatus.BAD_REQUEST)
        			.contentType(MediaType.APPLICATION_JSON)
        			.body(new ErrorMessage(request, HttpStatus.BAD_REQUEST,
        					"Colaborador atribuído ao projeto com sucesso."));
        }
}
