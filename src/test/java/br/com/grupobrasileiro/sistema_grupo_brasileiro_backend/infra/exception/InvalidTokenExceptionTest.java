package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class InvalidTokenExceptionTest {

    @Test
    public void testInvalidTokenExceptionMessage() {
        String expectedMessage = "Token inválido";
        InvalidTokenException exception = new InvalidTokenException(expectedMessage);
        
        
        assertEquals(expectedMessage, exception.getMessage());
    }
}
