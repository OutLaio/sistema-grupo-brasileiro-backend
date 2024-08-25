package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.CollaboratorAlreadyAssignedException;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CollaboratorAlreadyAssignedExceptionTest {

    @Test
    public void testExceptionMessage() {
        String expectedMessage = "Collaborator is already assigned";
        
        // Lança a exceção com a mensagem esperada
        CollaboratorAlreadyAssignedException exception = 
            assertThrows(CollaboratorAlreadyAssignedException.class, 
                         () -> { 
                             throw new CollaboratorAlreadyAssignedException(expectedMessage); 
                         });

        // Verifica se a mensagem da exceção é a esperada
        assertEquals(expectedMessage, exception.getMessage(), 
                     "A mensagem da exceção deve corresponder à mensagem esperada");
    }
}
