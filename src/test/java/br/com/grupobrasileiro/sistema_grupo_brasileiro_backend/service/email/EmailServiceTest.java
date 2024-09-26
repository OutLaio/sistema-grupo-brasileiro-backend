package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.email;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.email.PasswordRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EmailService Tests")
class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should send an email successfully")
    void sendEmail_Success() {
        // Arrange
        PasswordRequest emailRequest = new PasswordRequest(
                "from@example.com",
                "to@example.com",
                "Subject",
                "Email body text"
        );

        // No need to do anything with the mock for this test as we're testing successful send

        // Act
        emailService.send(emailRequest);

        // Assert
        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setFrom(emailRequest.emailFrom());
        expectedMessage.setTo(emailRequest.emailTo());
        expectedMessage.setSubject(emailRequest.subject());
        expectedMessage.setText(emailRequest.text());

        verify(emailSender, times(1)).send(expectedMessage);
    }

    @Test
    @DisplayName("Should log an error when email sending fails")
    void sendEmail_Failure() {
        // Arrange
        PasswordRequest emailRequest = new PasswordRequest(
                "from@example.com",
                "to@example.com",
                "Subject",
                "Email body text"
        );

        doThrow(new MailException("Error sending email") {}).when(emailSender).send(any(SimpleMailMessage.class));

        // Act
        emailService.send(emailRequest);

        // Assert
        // Verify that the logger was called with the appropriate error message
        // (Assuming you're using a logging framework that allows verification)
        // Example (if using a library like Logback or Log4j):
        // verify(logger).error("Error sending email to {}: {}", emailRequest.emailTo(), "Error sending email");
    }
}
