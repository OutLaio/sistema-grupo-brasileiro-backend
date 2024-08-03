package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.SendEmailForm;

public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender emailSender;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendEmail_Success() {
        // Given
        SendEmailForm emailForm = new SendEmailForm(
                "from@example.com",
                "to@example.com",
                "Subject",
                "Email body text");

        // When
        emailService.send(emailForm);

        // Then
        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setFrom("from@example.com");
        expectedMessage.setTo("to@example.com");
        expectedMessage.setSubject("Subject");
        expectedMessage.setText("Email body text");

        verify(emailSender, times(1)).send(expectedMessage);
    }

    @Test
    public void testSendEmail_Failure() {
        // Given
        SendEmailForm emailForm = new SendEmailForm(
                "from@example.com",
                "to@example.com",
                "Subject",
                "Email body text");

        doThrow(new MailException("Error sending email") {
        }).when(emailSender).send(any(SimpleMailMessage.class));

        // When
        emailService.send(emailForm);

        // Then
        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
        // You can also verify logs if you have a logging framework configured for
        // testing
    }
}
