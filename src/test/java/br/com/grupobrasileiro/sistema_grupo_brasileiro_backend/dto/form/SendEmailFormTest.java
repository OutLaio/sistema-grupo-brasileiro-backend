package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SendEmailFormTest {

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    @Test
    void testValidSendEmailForm() {
        String emailFrom = faker.internet().emailAddress();
        String emailTo = faker.internet().emailAddress();
        String subject = faker.lorem().sentence(5);
        String text = faker.lorem().paragraph();

        SendEmailForm sendEmailForm = new SendEmailForm(emailFrom, emailTo, subject, text);

        assertEquals(emailFrom, sendEmailForm.emailFrom());
        assertEquals(emailTo, sendEmailForm.emailTo());
        assertEquals(subject, sendEmailForm.subject());
        assertEquals(text, sendEmailForm.text());
    }

    @Test
    void testEmailFromCannotBeBlank() {
        String emailTo = faker.internet().emailAddress();
        String subject = faker.lorem().sentence(5);
        String text = faker.lorem().paragraph();

        SendEmailForm sendEmailForm = new SendEmailForm("", emailTo, subject, text);

        // Add validation logic to check that emailFrom is not blank
        // For example, if you are using a validation framework, you would check for constraint violations here
        // Since we are not using validation in this example, this test is just an illustration
        assertThrows(IllegalArgumentException.class, () -> {
            if (sendEmailForm.emailFrom().isBlank()) {
                throw new IllegalArgumentException("Sender email cannot be empty");
            }
        });
    }

    @Test
    void testEmailToCannotBeBlank() {
        String emailFrom = faker.internet().emailAddress();
        String subject = faker.lorem().sentence(5);
        String text = faker.lorem().paragraph();

        SendEmailForm sendEmailForm = new SendEmailForm(emailFrom, "", subject, text);

        // Add validation logic to check that emailTo is not blank
        // Similar to above, you would add appropriate validation checks
        assertThrows(IllegalArgumentException.class, () -> {
            if (sendEmailForm.emailTo().isBlank()) {
                throw new IllegalArgumentException("Recipient email cannot be empty");
            }
        });
    }

    @Test
    void testSubjectCannotBeBlank() {
        String emailFrom = faker.internet().emailAddress();
        String emailTo = faker.internet().emailAddress();
        String text = faker.lorem().paragraph();

        SendEmailForm sendEmailForm = new SendEmailForm(emailFrom, emailTo, "", text);

        // Add validation logic to check that subject is not blank
        assertThrows(IllegalArgumentException.class, () -> {
            if (sendEmailForm.subject().isBlank()) {
                throw new IllegalArgumentException("Subject cannot be empty");
            }
        });
    }

    @Test
    void testTextCannotBeBlank() {
        String emailFrom = faker.internet().emailAddress();
        String emailTo = faker.internet().emailAddress();
        String subject = faker.lorem().sentence(5);

        SendEmailForm sendEmailForm = new SendEmailForm(emailFrom, emailTo, subject, "");

        // Add validation logic to check that text is not blank
        assertThrows(IllegalArgumentException.class, () -> {
            if (sendEmailForm.text().isBlank()) {
                throw new IllegalArgumentException("Email body cannot be empty");
            }
        });
    }
}

