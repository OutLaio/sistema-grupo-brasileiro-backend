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
        ResetPasswordForm form = new ResetPasswordForm(validPassword);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testEmptyPassword() {
        String emptyPassword = "";
        ResetPasswordForm form = new ResetPasswordForm(emptyPassword);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testShortPassword() {
        String shortPassword = "Short1!";
        ResetPasswordForm form = new ResetPasswordForm(shortPassword);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testPasswordWithoutLowercase() {
        String passwordWithoutLowercase = "PASSWORD1!";
        ResetPasswordForm form = new ResetPasswordForm(passwordWithoutLowercase);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testPasswordWithoutUppercase() {
        String passwordWithoutUppercase = "password1!";
        ResetPasswordForm form = new ResetPasswordForm(passwordWithoutUppercase);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testPasswordWithoutDigit() {
        String passwordWithoutDigit = "Password!";
        ResetPasswordForm form = new ResetPasswordForm(passwordWithoutDigit);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testPasswordWithoutSpecialCharacter() {
        String passwordWithoutSpecialCharacter = "Password1";
        ResetPasswordForm form = new ResetPasswordForm(passwordWithoutSpecialCharacter);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNullPassword() {
        ResetPasswordForm form = new ResetPasswordForm(null);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }
}

