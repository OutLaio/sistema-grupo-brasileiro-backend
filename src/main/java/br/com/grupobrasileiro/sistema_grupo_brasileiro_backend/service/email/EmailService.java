package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.email;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.email.PasswordRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
            
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");

            helper.setFrom(email.emailFrom());
            helper.setTo(email.emailTo());
            helper.setSubject(email.subject());
            helper.setText(email.text(), true);  

            ClassPathResource logo = new ClassPathResource("img/logo-grupobrasileiro.png");
            helper.addInline("logoImage", logo);  

            emailSender.send(mimeMessage);
            LOGGER.info("Email sent successfully: to={}", email.emailTo());

        } catch (MailException | MessagingException e) {
            LOGGER.error("Error sending email to {}: {}", email.emailTo(), e.getMessage());
        }
    }
}
