package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.EmailRequestForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.SendEmailForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.EmailService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PasswordRecoveryController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordRecoveryController.class);
	 
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final EmailService emailService;
    
    
    @PostMapping("/solicitaReset")
    public ResponseEntity<String> resetPassword(@RequestBody EmailRequestForm data) {
        LOGGER.info("Starting password reset request for: {}", data.email());

        try {
        	User user = (User) userRepository.findByEmail(data.email());
            if (user == null) {
                LOGGER.warn("User not found with email: {}", data.email());
                return ResponseEntity.status(404).body("User not found");
            }

            String token = tokenService.generateToken(user);
            String resetUrl = "http://localhost:8080/resetPassword?token=" + token;

            String body = String.format(
                    "Hello!<br><br>" +
                    "You requested a password reset. To reset your password, please click the link below:<br><br>" +
                    "<a href=\"%s\">Reset Password</a><br><br>" +
                    "If you did not request this, please ignore this email. Your password will remain unchanged and no further action will be needed.<br><br>" +
                    "Best regards,<br>" +
                    "EverDev Team",
                    resetUrl
            );

            SendEmailForm sendEmailForm = new SendEmailForm(
                    "no-reply@everdev.com", 
                    data.email(), 
                    "Password Reset",
                    body 
            );

            emailService.send(sendEmailForm);

            LOGGER.info("Password reset email sent to: {}", data.email());
            return ResponseEntity.ok("Email sent successfully!");

        } catch (RuntimeException e) {
            LOGGER.error("Error finding user: {}", e.getMessage());
            return ResponseEntity.status(404).body("User not found");
        }
    
    }
}
