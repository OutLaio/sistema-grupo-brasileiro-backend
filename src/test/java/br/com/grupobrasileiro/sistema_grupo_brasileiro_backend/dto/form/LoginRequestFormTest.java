package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

public class LoginRequestFormTest {

    private final Faker faker = new Faker();
    private final Validator validator;

    public LoginRequestFormTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testValidEmailAndPassword() {
        String fakeEmail = faker.internet().emailAddress();
        String fakePassword = faker.internet().password();
        LoginRequestForm form = new LoginRequestForm(fakeEmail, fakePassword);

        Set<ConstraintViolation<LoginRequestForm>> violations = validator.validate(form);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidEmail() {
        String invalidEmail = "invalid-email";
        String fakePassword = faker.internet().password();
        LoginRequestForm form = new LoginRequestForm(invalidEmail, fakePassword);

        Set<ConstraintViolation<LoginRequestForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNullEmail() {
        String fakePassword = faker.internet().password();
        LoginRequestForm form = new LoginRequestForm(null, fakePassword);

        Set<ConstraintViolation<LoginRequestForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testEmptyPassword() {
        String fakeEmail = faker.internet().emailAddress();
        LoginRequestForm form = new LoginRequestForm(fakeEmail, "");

        Set<ConstraintViolation<LoginRequestForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNullPassword() {
        String fakeEmail = faker.internet().emailAddress();
        LoginRequestForm form = new LoginRequestForm(fakeEmail, null);

        Set<ConstraintViolation<LoginRequestForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }
}

