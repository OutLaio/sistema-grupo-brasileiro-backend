package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InvalidRoleExceptionTest {

    @Test
    void shouldThrowInvalidRoleExceptionWithMessage() {
        // Define a mensagem esperada para a exceção
        String expectedMessage = "This role is invalid";

        // Verifica se a exceção é lançada quando instanciamos InvalidRoleException
        InvalidRoleException exception = assertThrows(InvalidRoleException.class, () -> {
            throw new InvalidRoleException(expectedMessage);
        });

        // Verifica se a mensagem da exceção está correta
        assertEquals(expectedMessage, exception.getMessage());
    }
}
