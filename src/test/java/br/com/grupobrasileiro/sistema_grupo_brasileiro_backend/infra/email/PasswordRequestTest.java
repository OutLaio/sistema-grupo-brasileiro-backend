package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.email;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidPasswordRequest() {
        PasswordRequest request = new PasswordRequest(
            "sender@example.com",
            "recipient@example.com",
            "Subject",
            "This is the body of the email"
        );

        Set<ConstraintViolation<PasswordRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "Não deve haver violações de validação para um PasswordRequest válido");
    }

    @Test
    public void testInvalidPasswordRequest() {
        PasswordRequest request = new PasswordRequest(
            "", // emailFrom
            "invalid-email", // emailTo
            "This is a very long subject line that exceeds the maximum length of one hundred characters. It should trigger a validation error.",
            "" // text
        );

        Set<ConstraintViolation<PasswordRequest>> violations = validator.validate(request);

        assertEquals(4, violations.size(), "Deve haver 4 violações de validação");

        for (ConstraintViolation<PasswordRequest> violation : violations) {
            switch (violation.getPropertyPath().toString()) {
                case "emailFrom":
                    assertEquals("Sender email cannot be empty", violation.getMessage());
                    break;
                case "emailTo":
                    assertEquals("Invalid recipient email format", violation.getMessage());
                    break;
                case "subject":
                    assertEquals("Subject cannot be longer than 100 characters", violation.getMessage());
                    break;
                case "text":
                    assertEquals("Email body cannot be empty", violation.getMessage());
                    break;
            }
        }
    }
}
