package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.email;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.email.PasswordRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender emailSender;

    public void send(PasswordRequest email) {

        try {
            LOGGER.info("Preparing email to send: from={}, to={}, subject={}", email.emailFrom(), email.emailTo(), email.subject());

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(email.emailFrom());
            simpleMailMessage.setTo(email.emailTo());
            simpleMailMessage.setSubject(email.subject());
            simpleMailMessage.setText(email.text());

            emailSender.send(simpleMailMessage);
            LOGGER.info("Email sent successfully: to={}", email.emailTo());

        } catch (MailException e) {
            LOGGER.error("Error sending email to {}: {}", email.emailTo(), e.getMessage());
        }
    }
}