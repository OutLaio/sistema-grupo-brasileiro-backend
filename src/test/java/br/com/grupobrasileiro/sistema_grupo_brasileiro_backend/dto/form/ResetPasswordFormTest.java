package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import com.github.javafaker.Faker;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResetPasswordFormTest {

    private Faker faker;
    private Validator validator;

    @BeforeEach
    void setUp() {
        faker = new Faker();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidResetPasswordForm() {
        String token = faker.lorem().characters(10);  // Assuming token is a random string
        String password = "Valid1Password!";

        ResetPasswordForm form = new ResetPasswordForm(token, password);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);

        assertTrue(violations.isEmpty(), "No violations should be present");
    }

    @Test
    void testEmptyToken() {
        String password = "Valid1Password!";

        ResetPasswordForm form = new ResetPasswordForm("", password);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);

        boolean hasTokenError = violations.stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("token") &&
                        violation.getMessage().contains("Token cannot be empty"));

        assertTrue(hasTokenError, "Expected token empty error not found");
    }

    @Test
    void testBlankToken() {
        String password = "Valid1Password!";

        ResetPasswordForm form = new ResetPasswordForm(" ", password);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);

        boolean hasTokenError = violations.stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("token") &&
                        violation.getMessage().contains("Token cannot be empty"));

        assertTrue(hasTokenError, "Expected token blank error not found");
    }

    @Test
    void testShortPassword() {
        String token = faker.lorem().characters(10);
        String shortPassword = "Short1!";

        ResetPasswordForm form = new ResetPasswordForm(token, shortPassword);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);

        boolean hasPasswordLengthError = violations.stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("newPassword") &&
                        violation.getMessage().contains("Password must be at least 8 characters long!"));

        assertTrue(hasPasswordLengthError, "Expected password length error not found");
    }

    @Test
    void testPasswordWithoutLowercase() {
        String token = faker.lorem().characters(10);
        String password = "VALIDPASSWORD1!";

        ResetPasswordForm form = new ResetPasswordForm(token, password);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);

        boolean hasPasswordLowercaseError = violations.stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("newPassword") &&
                        violation.getMessage().contains("Password must contain at least one lowercase letter!"));

        assertTrue(hasPasswordLowercaseError, "Expected password lowercase letter error not found");
    }

    @Test
    void testPasswordWithoutUppercase() {
        String token = faker.lorem().characters(10);
        String password = "validpassword1!";

        ResetPasswordForm form = new ResetPasswordForm(token, password);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);

        boolean hasPasswordUppercaseError = violations.stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("newPassword") &&
                        violation.getMessage().contains("Password must contain at least one uppercase letter!"));

        assertTrue(hasPasswordUppercaseError, "Expected password uppercase letter error not found");
    }

    @Test
    void testPasswordWithoutDigit() {
        String token = faker.lorem().characters(10);
        String password = "PasswordWithoutDigit!";

        ResetPasswordForm form = new ResetPasswordForm(token, password);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);

        boolean hasPasswordDigitError = violations.stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("newPassword") &&
                        violation.getMessage().contains("Password must contain at least one digit!"));

        assertTrue(hasPasswordDigitError, "Expected password digit error not found");
    }

    @Test
    void testPasswordWithoutSpecialCharacter() {
        String token = faker.lorem().characters(10);
        String password = "Password1";

        ResetPasswordForm form = new ResetPasswordForm(token, password);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);

        boolean hasPasswordSpecialCharError = violations.stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("newPassword") &&
                        violation.getMessage().contains("Password must contain at least one special character!"));

        assertTrue(hasPasswordSpecialCharError, "Expected password special character error not found");
    }

    @Test
    void testNullToken() {
        String password = "Valid1Password!";

        ResetPasswordForm form = new ResetPasswordForm(null, password);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);

        boolean hasNullTokenError = violations.stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("token") &&
                        violation.getMessage().contains("Token cannot be empty"));

        assertTrue(hasNullTokenError, "Expected null token error not found");
    }

    @Test
    void testNullPassword() {
        String token = faker.lorem().characters(10);

        ResetPasswordForm form = new ResetPasswordForm(token, null);

        Set<ConstraintViolation<ResetPasswordForm>> violations = validator.validate(form);

        boolean hasNullPasswordError = violations.stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("newPassword") &&
                        violation.getMessage().contains("Password is required!"));

        assertTrue(hasNullPasswordError, "Expected null password error not found");
    }
}
