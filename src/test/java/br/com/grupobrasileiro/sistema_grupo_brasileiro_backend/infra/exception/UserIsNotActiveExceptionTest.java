package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserIsNotActiveExceptionTest {

    @Test
    public void testUserIsNotActiveExceptionMessage() {
        String expectedMessage = "User is not active";
        UserIsNotActiveException exception = new UserIsNotActiveException(expectedMessage);
        
        
        assertEquals(expectedMessage, exception.getMessage());
    }
}
