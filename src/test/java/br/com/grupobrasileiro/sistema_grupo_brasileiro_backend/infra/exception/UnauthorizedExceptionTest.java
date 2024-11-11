package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UnauthorizedExceptionTest {

    @Test
    public void testUnauthorizedException() {
        // Defina uma mensagem de erro
        String errorMessage = "Unauthorized access";

        // Verifique se a exceção é lançada e capture a exceção
        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            throw new UnauthorizedException(errorMessage);
        });

        // Verifique se a mensagem da exceção é a esperada
        assertEquals(errorMessage, exception.getMessage());
    }
}
