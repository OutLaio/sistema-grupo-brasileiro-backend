package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.email.PasswordRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender emailSender;

    private Faker faker;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testSendEmail_Success() {
        // Given
        String from = faker.internet().emailAddress();
        String to = faker.internet().emailAddress();
        String subject = faker.lorem().sentence();
        String body = faker.lorem().paragraph();
        
        PasswordRequest emailForm = new PasswordRequest(
                from,
                to,
                subject,
                body);

        // When
        emailService.send(emailForm);

        // Then
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(emailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage capturedMessage = messageCaptor.getValue();
        assertEquals(from, capturedMessage.getFrom(), "From address should match.");
        assertArrayEquals(new String[]{to}, capturedMessage.getTo(), "To address should match.");
        assertEquals(subject, capturedMessage.getSubject(), "Subject should match.");
        assertEquals(body, capturedMessage.getText(), "Email body text should match.");
    }

    @Test
    public void testSendEmail_Failure() {
        // Given
        String from = faker.internet().emailAddress();
        String to = faker.internet().emailAddress();
        String subject = faker.lorem().sentence();
        String body = faker.lorem().paragraph();
        
        PasswordRequest emailForm = new PasswordRequest(
                from,
                to,
                subject,
                body);

        // Simulate failure
        doThrow(new MailException("Error sending email") {}).when(emailSender).send(any(SimpleMailMessage.class));

        // When
        emailService.send(emailForm);

        // Then
        // Verify that send() was called even though it failed
        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testSendEmail_InvalidContent() {
        // Given
        String invalidFrom = ""; // Invalid 'from' address
        String to = faker.internet().emailAddress();
        String subject = faker.lorem().sentence();
        String body = faker.lorem().paragraph();
        
        PasswordRequest emailForm = new PasswordRequest(
                invalidFrom,
                to,
                subject,
                body);

        // When
        emailService.send(emailForm);

        // Then
        // Capture the argument to see if it was sent
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(emailSender, times(1)).send(messageCaptor.capture());

        SimpleMailMessage capturedMessage = messageCaptor.getValue();
        // Check if 'from' address is invalid
        assertEquals(invalidFrom, capturedMessage.getFrom(), "From address should be invalid.");
    }
}
