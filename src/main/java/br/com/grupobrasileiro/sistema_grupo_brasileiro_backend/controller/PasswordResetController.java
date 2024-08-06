package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.ResetPasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.InvalidTokenException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PasswordResetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordResetController.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPasswordForm data) {
        LOGGER.info("Starting password reset operation");

        String emailFromToken = tokenService.validateToken(data.token());
        if (emailFromToken == null) {
            LOGGER.warn("Invalid or expired token.");
            throw new InvalidTokenException("Invalid or expired token");
        }
        
        User user = (User) userRepository.findByEmail(emailFromToken);
        if (user == null) {
            LOGGER.warn("User not found.");
            throw new EntityNotFoundException("Usuário não encontrado com e-mail: " + user.getEmail());   
        }

        user.setPassword(passwordEncoder.encode(data.newPassword()));
        userRepository.save(user);
        
        tokenService.invalidateToken(data.token());
        
        LOGGER.info("Password successfully reset");
        return ResponseEntity.ok("Password successfully changed!");

    }

}
