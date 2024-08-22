package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class InvalidTokenExceptionTest {

    @Test
    public void testInvalidTokenExceptionMessage() {
    	//Set the expected message
        String expectedMessage = "Token inválido";
        
      //Create an instance of the exception with the expected message
        InvalidTokenException exception = new InvalidTokenException(expectedMessage);
        
      //Check if the exception message is correct
        assertEquals(expectedMessage, exception.getMessage(), "The exception message should match the expected message.");
    }
}



