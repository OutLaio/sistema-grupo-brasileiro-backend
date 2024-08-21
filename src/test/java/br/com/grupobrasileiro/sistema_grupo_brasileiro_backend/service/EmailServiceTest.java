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


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.EmailService;

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
        assertEquals("from@example.com", capturedMessage.getFrom(), "From address should match.");
        assertArrayEquals(new String[]{"to@example.com"}, capturedMessage.getTo(), "To address should match.");
        assertEquals("Subject", capturedMessage.getSubject(), "Subject should match.");
        assertEquals("Email body text", capturedMessage.getText(), "Email body text should match.");
    }

    @Test
    public void testSendEmail_Failure() {
        // Given
        PasswordRequest emailForm = new PasswordRequest(
                "from@example.com",
                "to@example.com",
                "Subject",
                "Email body text");

        // Simulate failure
        doThrow(new MailException("Error sending email") {}).when(emailSender).send(any(SimpleMailMessage.class));

        // When & Then
        try {
            emailService.send(emailForm);
        } catch (Exception e) {
          
            assertEquals("Error sending email", e.getMessage(), "Exception message should match.");
        }

        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    
    @Test
    public void testSendEmail_InvalidContent() {
        // Given
        PasswordRequest emailForm = new PasswordRequest(
                "", // Invalid 'from' address
                "to@example.com",
                "Subject",
                "Email body text");

        // When & Then
        try {
            emailService.send(emailForm);
        } catch (Exception e) {
            // The system is expected to handle an email with an invalid 'from' address
            assertEquals("Invalid email address", e.getMessage(), "Exception message should match for invalid email.");
        }

        verify(emailSender, times(0)).send(any(SimpleMailMessage.class));
    }
}
