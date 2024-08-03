package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmailRequestFormTest {

    @Test
    public void testEmailRequestForm() {
        // Arrange
        String email = "test@example.com";

        // Act
        EmailRequestForm emailRequestForm = new EmailRequestForm(email);

        // Assert
        assertEquals(email, emailRequestForm.email());
    }
}
