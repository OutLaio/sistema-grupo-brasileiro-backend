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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private EmailService emailService;
    private PasswordEncoder passwordEncoder;

    public String doLogin(LoginForm form){
        User user = (User) userRepository.findByEmail(form.email()).orElseThrow(
                () -> new EntityNotFoundException("User not found for email: " + form.email())
        );
        if (user.getDisabled()) throw new UserIsNotActiveException("Acesso negado.");

        var usernamePassword = new UsernamePasswordAuthenticationToken(form.email(), form.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        return tokenService.generateToken((User) auth.getPrincipal());
    }

    public void requestRecoveryPassword(RecoveryPasswordForm form){
        User user = (User) userRepository.findByEmail(form.email()).orElseThrow(
                () -> new EntityNotFoundException("Usuário não encontrado com e-mail: " + form.email())
        );

        String token = tokenService.generateToken(user);
        String resetUrl = "http://localhost:4200/resetPassword?token=" + token;


        String body = String.format(
                "Olá!<br><br>" +
                        "Você solicitou uma redefinição de senha. Para redefinir sua senha, clique no link abaixo:<br><br>" +
                        "<a href=\"%s\">Redefinir Senha</a><br><br>" +
                        "Se você não solicitou isso, ignore este e-mail. Sua senha permanecerá inalterada e nenhuma ação adicional será necessária.<br><br>" +
                        "Atenciosamente,<br>" +
                        "Grupo Brasileiro",
                resetUrl
        );

        PasswordRequest sendEmailForm = new PasswordRequest(
                "no-reply@everdev.com",
                form.email(),
                "Password Reset",
                body
        );
        emailService.send(sendEmailForm);
    }

    public void resetPassword(ResetPasswordForm form){
        String emailFromToken = tokenService.validateToken(form.token());
        if (emailFromToken == null) throw new InvalidTokenException("Token inválido ou expirado");

        User user = (User)userRepository.findByEmail(emailFromToken).orElseThrow(
                () -> new EntityNotFoundException("User not found for email: " + emailFromToken)
        );

        user.setPassword(passwordEncoder.encode(form.password()));
        userRepository.save(user);

        tokenService.invalidateToken(form.token());
    }
}
