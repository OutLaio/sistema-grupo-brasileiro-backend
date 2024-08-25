package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class UnauthorizedExceptionTest {

    @Test
    public void testExceptionMessage() {
        // Expected message for the exception
        String expectedMessage = "Unauthorized access";

        // Throw the exception with the expected message and verify that the exception is thrown
        UnauthorizedException exception = 
            assertThrows(UnauthorizedException.class, 
                         () -> { 
                             throw new UnauthorizedException(expectedMessage); 
                         });

        // Verify that the exception message matches the expected message
        assertEquals(expectedMessage, exception.getMessage(), 
                     "The exception message should match the expected message");
    }
}
