package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CompanyAlreadyExistsExceptionTest {

    @Test
    public void testCompanyAlreadyExistsException() {
        // Defina uma mensagem de erro
        String errorMessage = "Company already exists";

        // Verifique se a exceção é lançada e capture a exceção
        CompanyAlreadyExistsException exception = assertThrows(CompanyAlreadyExistsException.class, () -> {
            throw new CompanyAlreadyExistsException(errorMessage);
        });

        // Verifique se a mensagem da exceção é a esperada
        assertEquals(errorMessage, exception.getMessage());
    }
}
