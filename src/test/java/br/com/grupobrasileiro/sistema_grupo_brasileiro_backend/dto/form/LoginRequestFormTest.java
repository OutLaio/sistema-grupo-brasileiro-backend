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

public class LoginRequestFormTest {

    private Faker faker;
    private Validator validator;

    @BeforeEach
    void setUp() {
        faker = new Faker();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidLoginRequestForm() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();

        LoginRequestForm form = new LoginRequestForm(email, password);

        Set<ConstraintViolation<LoginRequestForm>> violations = validator.validate(form);

        assertTrue(violations.isEmpty(), "No violations should be present");
    }

    @Test
    void testInvalidEmailFormat() {
        String invalidEmail = "invalid-email";
        String password = faker.internet().password();

        LoginRequestForm form = new LoginRequestForm(invalidEmail, password);

        Set<ConstraintViolation<LoginRequestForm>> violations = validator.validate(form);

        boolean hasEmailFormatError = violations.stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("email") &&
                        violation.getMessage().contains("must be a well-formed email address"));
        
        assertTrue(hasEmailFormatError, "Expected email format error not found");
    }

    @Test
    void testBlankEmail() {
        String password = faker.internet().password();

        LoginRequestForm form = new LoginRequestForm("", password);

        Set<ConstraintViolation<LoginRequestForm>> violations = validator.validate(form);

        boolean hasBlankEmailError = violations.stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("email") &&
                        violation.getMessage().contains("must not be blank"));
        
        assertTrue(hasBlankEmailError, "Expected blank email error not found");
    }

    @Test
    void testBlankPassword() {
        String email = faker.internet().emailAddress();

        LoginRequestForm form = new LoginRequestForm(email, "");

        Set<ConstraintViolation<LoginRequestForm>> violations = validator.validate(form);

        boolean hasBlankPasswordError = violations.stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("password") &&
                        violation.getMessage().contains("must not be blank"));
        
        assertTrue(hasBlankPasswordError, "Expected blank password error not found");
    }

    @Test
    void testNullEmail() {
        String password = faker.internet().password();

        LoginRequestForm form = new LoginRequestForm(null, password);

        Set<ConstraintViolation<LoginRequestForm>> violations = validator.validate(form);

        boolean hasNullEmailError = violations.stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("email") &&
                        violation.getMessage().contains("must not be blank"));
        
        assertTrue(hasNullEmailError, "Expected null email error not found");
    }

    @Test
    void testNullPassword() {
        String email = faker.internet().emailAddress();

        LoginRequestForm form = new LoginRequestForm(email, null);

        Set<ConstraintViolation<LoginRequestForm>> violations = validator.validate(form);

        boolean hasNullPasswordError = violations.stream()
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("password") &&
                        violation.getMessage().contains("must not be blank"));
        
        assertTrue(hasNullPasswordError, "Expected null password error not found");
    }
}
