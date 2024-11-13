package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CollaboratorAlreadyAssignedExceptionTest {

    @Test
    public void testCollaboratorAlreadyAssignedException() {
        // Defina uma mensagem de erro
        String errorMessage = "Collaborator is already assigned to the project";

        // Verifique se a exceção é lançada e capture a exceção
        CollaboratorAlreadyAssignedException exception = assertThrows(CollaboratorAlreadyAssignedException.class, () -> {
            throw new CollaboratorAlreadyAssignedException(errorMessage);
        });

        // Verifique se a mensagem da exceção é a esperada
        assertEquals(errorMessage, exception.getMessage());
    }
}
