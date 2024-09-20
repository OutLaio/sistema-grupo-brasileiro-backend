package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.auth;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.LoginForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.RecoveryPasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.ResetPasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.email.PasswordRequest;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.InvalidTokenException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.UserIsNotActiveException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  TokenService tokenService;

    @Autowired
    private  EmailService emailService;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    public String doLogin(LoginForm form, AuthenticationManager authenticationManager) {
        User user = userRepository.findByEmail(form.email());
        if (user == null) {
            throw new EntityNotFoundException("User not found for email: " + form.email());
        }
        if (user.getDisabled()) throw new UserIsNotActiveException("Acesso negado.");

        var usernamePassword = new UsernamePasswordAuthenticationToken(form.email(), form.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        // Retornar um token gerado
        return tokenService.generateToken(user); // Supondo que você tenha um método para gerar o token
    }

    public void requestRecoveryPassword(RecoveryPasswordForm form) {
        User user = userRepository.findByEmail(form.email());
        if (user == null) {
            throw new EntityNotFoundException("Usuário não encontrado com e-mail: " + form.email());
        }

        String token = tokenService.generateToken(user);
        String resetUrl = "http://localhost:4200/resetPassword?token=" + token;

        String body = String.format(
                "Olá!<br><br>" +
                        "Você solicitou uma redefinição de senha. Para redefinir sua senha, clique no link abaixo:<br><br>" +
                        "<a href=\"%s\">Redefinir Senha</a><br><br>" +
                        "Se você não solicitou isso, ignore este e-mail. Sua senha permanecerá inalterada e nenhuma ação adicional será necessária.<br><br>" +
                        "Atenciosamente,<br>" +
                        "Grupo Brasileiro", resetUrl);

        PasswordRequest sendEmailForm = new PasswordRequest("no-reply@everdev.com", form.email(), "Password Reset", body);
        emailService.send(sendEmailForm);
    }

    public void resetPassword(ResetPasswordForm form) {
        String emailFromToken = tokenService.validateToken(form.token());
        if (emailFromToken == null) {
            throw new InvalidTokenException("Token inválido ou expirado");
        }

        User user = userRepository.findByEmail(emailFromToken);
        if (user == null) {
            throw new EntityNotFoundException("User not found for email: " + emailFromToken);
        }

        user.setPassword(passwordEncoder.encode(form.password()));
        userRepository.save(user);
        tokenService.invalidateToken(form.token());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return user; // Certifique-se de que `User` implementa `UserDetails`
    }
}
