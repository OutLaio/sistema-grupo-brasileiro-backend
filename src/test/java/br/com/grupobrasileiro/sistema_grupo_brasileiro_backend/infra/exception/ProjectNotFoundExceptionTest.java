package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ProjectNotFoundExceptionTest {

    @Test
    public void testProjectNotFoundException() {
        // Defina uma mensagem de erro
        String errorMessage = "Project not found";

        // Verifique se a exceção é lançada e capture a exceção
        ProjectNotFoundException exception = assertThrows(ProjectNotFoundException.class, () -> {
            throw new ProjectNotFoundException(errorMessage);
        });

        // Verifique se a mensagem da exceção é a esperada
        assertEquals(errorMessage, exception.getMessage());
    }
}
