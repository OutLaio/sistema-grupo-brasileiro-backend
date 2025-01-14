package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.email;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.email.PasswordRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@DisplayName("EmailService Tests")
class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @Mock
    private MimeMessage mimeMessage; 

    @InjectMocks
    private EmailService emailService;

    private List<String> logMessages; 

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage); // Retorna o mock do MimeMessage

        // Configurar uma lista para capturar mensagens de log
        logMessages = new ArrayList<>();
        Logger logger = LoggerFactory.getLogger(EmailService.class);
        // Redirecionar a saída do logger para a lista
        LoggerTestAppender appender = new LoggerTestAppender(logMessages);
        appender.start();
        ((ch.qos.logback.classic.Logger) logger).addAppender(appender);
    }

    @Test
    @DisplayName("Should send an email successfully")
    void sendEmail_Success() throws Exception { // Adicione a exceção
        // Arrange
        PasswordRequest emailRequest = new PasswordRequest(
                "from@example.com",
                "to@example.com",
                "Subject",
                "Email body text"
        );

        // Act
        emailService.send(emailRequest);

        // Assert
        verify(emailSender, times(1)).send(mimeMessage);
    }

    @Test
    @DisplayName("Should log an error when email sending fails")
    void sendEmail_Failure() throws Exception { 
        // Arrange
        PasswordRequest emailRequest = new PasswordRequest(
                "from@example.com",
                "to@example.com",
                "Subject",
                "Email body text"
        );

        doThrow(new MailException("Error sending email") {}).when(emailSender).send(any(MimeMessage.class));

        // Act
        emailService.send(emailRequest);

        // Assert
        assertTrue(logMessages.stream().anyMatch(msg -> msg.contains("Error sending email to")));
    }

    // Classe interna para capturar mensagens de log
    private static class LoggerTestAppender extends ch.qos.logback.core.AppenderBase<ch.qos.logback.classic.spi.ILoggingEvent> {
        private final List<String> logMessages;

        public LoggerTestAppender(List<String> logMessages) {
            this.logMessages = logMessages;
        }

        @Override
        protected void append(ch.qos.logback.classic.spi.ILoggingEvent eventObject) {
            logMessages.add(eventObject.getFormattedMessage());
        }
    }
}