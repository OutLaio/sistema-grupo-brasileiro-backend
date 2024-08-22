package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class EmailUniqueViolationExceptionTest {

    @Test
    public void testEmailUniqueViolationExceptionMessage() {
        // Set the expected message
        String expectedMessage = "Email already exists";
        
     // Create an instance of the exception with the expected message
        EmailUniqueViolationException exception = new EmailUniqueViolationException(expectedMessage);
        
     // Check if the exception message is correct
        assertEquals(expectedMessage, exception.getMessage(), "The exception message should match the expected message.");
    }
}
