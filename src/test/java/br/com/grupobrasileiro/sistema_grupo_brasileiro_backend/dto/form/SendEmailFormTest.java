package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.email.PasswordRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

public class SendEmailFormTest {

    private final Faker faker = new Faker();
    private final Validator validator;

    public SendEmailFormTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void testValidForm() {
        String emailFrom = faker.internet().emailAddress();
        String emailTo = faker.internet().emailAddress();
        String subject = faker.lorem().sentence(3);
        String text = faker.lorem().paragraph();

        PasswordRequest form = new PasswordRequest(emailFrom, emailTo, subject, text);

        Set<ConstraintViolation<PasswordRequest>> violations = validator.validate(form);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidEmailFrom() {
        String invalidEmailFrom = "invalid-email";
        String emailTo = faker.internet().emailAddress();
        String subject = faker.lorem().sentence(3);
        String text = faker.lorem().paragraph();

        PasswordRequest form = new PasswordRequest(invalidEmailFrom, emailTo, subject, text);

        Set<ConstraintViolation<PasswordRequest>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidEmailTo() {
        String emailFrom = faker.internet().emailAddress();
        String invalidEmailTo = "invalid-email";
        String subject = faker.lorem().sentence(3);
        String text = faker.lorem().paragraph();

        PasswordRequest form = new PasswordRequest(emailFrom, invalidEmailTo, subject, text);

        Set<ConstraintViolation<PasswordRequest>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNullEmailFrom() {
        String emailTo = faker.internet().emailAddress();
        String subject = faker.lorem().sentence(3);
        String text = faker.lorem().paragraph();

        PasswordRequest form = new PasswordRequest(null, emailTo, subject, text);

        Set<ConstraintViolation<PasswordRequest>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testNullEmailTo() {
        String emailFrom = faker.internet().emailAddress();
        String subject = faker.lorem().sentence(3);
        String text = faker.lorem().paragraph();

        PasswordRequest form = new PasswordRequest(emailFrom, null, subject, text);

        Set<ConstraintViolation<PasswordRequest>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testEmptySubject() {
        String emailFrom = faker.internet().emailAddress();
        String emailTo = faker.internet().emailAddress();
        String text = faker.lorem().paragraph();

        PasswordRequest form = new PasswordRequest(emailFrom, emailTo, "", text);

        Set<ConstraintViolation<PasswordRequest>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testSubjectTooLong() {
        String emailFrom = faker.internet().emailAddress();
        String emailTo = faker.internet().emailAddress();
        String longSubject = faker.lorem().characters(101);
        String text = faker.lorem().paragraph();

        PasswordRequest form = new PasswordRequest(emailFrom, emailTo, longSubject, text);

        Set<ConstraintViolation<PasswordRequest>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testEmptyText() {
        String emailFrom = faker.internet().emailAddress();
        String emailTo = faker.internet().emailAddress();
        String subject = faker.lorem().sentence(3);

        PasswordRequest form = new PasswordRequest(emailFrom, emailTo, subject, "");

        Set<ConstraintViolation<PasswordRequest>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testAllFieldsNull() {
        PasswordRequest form = new PasswordRequest(null, null, null, null);

        Set<ConstraintViolation<PasswordRequest>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());
    }
}

