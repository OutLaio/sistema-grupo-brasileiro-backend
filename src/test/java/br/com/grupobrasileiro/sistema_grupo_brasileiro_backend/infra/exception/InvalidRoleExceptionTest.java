package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class InvalidRoleExceptionTest {

    @Test
    public void testInvalidRoleExceptionMessage() {
    	//Set the expected message
        String expectedMessage = "Role inválido";
        
      //Create an instance of the exception with the expected message
        InvalidRoleException exception = new InvalidRoleException(expectedMessage);
        
      //Check if the exception message is correct
        assertEquals(expectedMessage, exception.getMessage(), "The exception message should match the expected message.");
    }
}



