package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class EntityNotFoundExceptionTest {

    @Test
    public void testExceptionMessage() {
        // Expected message for the exception
        String expectedMessage = "Entity not found";
        
        // Throw the exception with the expected message and verify that the exception is thrown
        EntityNotFoundException exception = 
            assertThrows(EntityNotFoundException.class, 
                         () -> { 
                             throw new EntityNotFoundException(expectedMessage); 
                         });

        // Verify that the exception message matches the expected message
        assertEquals(expectedMessage, exception.getMessage(), 
                     "The exception message should match the expected message");
    }
}
