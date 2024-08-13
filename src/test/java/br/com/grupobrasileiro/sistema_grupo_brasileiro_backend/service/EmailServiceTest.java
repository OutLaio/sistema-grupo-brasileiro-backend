package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.email.PasswordRequest;

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
        PasswordRequest emailForm = new PasswordRequest(
                "from@example.com",
                "to@example.com",
                "Subject",
                "Email body text");

        // When
        emailService.send(emailForm);

        // Then
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(emailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage capturedMessage = messageCaptor.getValue();
        assertEquals("from@example.com", capturedMessage.getFrom());
        assertArrayEquals(new String[]{"to@example.com"}, capturedMessage.getTo());
        assertEquals("Subject", capturedMessage.getSubject());
        assertEquals("Email body text", capturedMessage.getText());
    }

    @Test
    public void testSendEmail_Failure() {
        // Given
        PasswordRequest emailForm = new PasswordRequest(
                "from@example.com",
                "to@example.com",
                "Subject",
                "Email body text");

        doThrow(new MailException("Error sending email") {}).when(emailSender).send(any(SimpleMailMessage.class));

        // When
        emailService.send(emailForm);

        // Then
        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
        // Optionally verify logs if you have a logging framework configured for testing
    }
}
