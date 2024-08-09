package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

public class ResetPasswordFormTest {

    private final Faker faker = new Faker();
    private final Validator validator;

    public ResetPasswordFormTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testValidTokenAndPassword() {
        String fakeToken = faker.internet().uuid();
        String validPassword = "Valid1Password!";
        ResetPasswordForm form = new ResetPasswordForm(fakeToken, validPassword);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testEmptyToken() {
        String validPassword = "Valid1Password!";
        ResetPasswordForm form = new ResetPasswordForm("", validPassword);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testShortPassword() {
        String fakeToken = faker.internet().uuid();
        String shortPassword = "Short1!";
        ResetPasswordForm form = new ResetPasswordForm(fakeToken, shortPassword);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testPasswordWithoutLowercase() {
        String fakeToken = faker.internet().uuid();
        String password = "PASSWORD1!";
        ResetPasswordForm form = new ResetPasswordForm(fakeToken, password);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testPasswordWithoutUppercase() {
        String fakeToken = faker.internet().uuid();
        String password = "password1!";
        ResetPasswordForm form = new ResetPasswordForm(fakeToken, password);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testPasswordWithoutDigit() {
        String fakeToken = faker.internet().uuid();
        String password = "Password!";
        ResetPasswordForm form = new ResetPasswordForm(fakeToken, password);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testPasswordWithoutSpecialCharacter() {
        String fakeToken = faker.internet().uuid();
        String password = "Password1";
        ResetPasswordForm form = new ResetPasswordForm(fakeToken, password);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNullPassword() {
        String fakeToken = faker.internet().uuid();
        ResetPasswordForm form = new ResetPasswordForm(fakeToken, null);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }
}

