package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CompanyAlreadyExistsExceptionTest {

    @Test
    public void testExceptionMessage() {
        // Expected message for the exception
        String expectedMessage = "Company already exists";
        
        // Throw the exception with the expected message and verify that the exception is thrown
        CompanyAlreadyExistsException exception = 
            assertThrows(CompanyAlreadyExistsException.class, 
                         () -> { 
                             throw new CompanyAlreadyExistsException(expectedMessage); 
                         });

        // Verify that the exception message matches the expected message
        assertEquals(expectedMessage, exception.getMessage(), 
                     "The exception message should match the expected message");
    }
}
