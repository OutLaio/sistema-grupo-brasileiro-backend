package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UserIsNotActiveExceptionTest {

    @Test
    public void testUserIsNotActiveExceptionMessage() {
    	// Set the expected message
        String expectedMessage = "User is not active";
        
     // Create an instance of the exception with the expected message
        UserIsNotActiveException exception = new UserIsNotActiveException(expectedMessage);
        
     // Check if the exception message is correct
        assertEquals(expectedMessage, exception.getMessage(), "The exception message should match the expected message.");
    }
}

