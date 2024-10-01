package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.auth;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.LoginForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.RecoveryPasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.ResetPasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.view.TokenView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view.EmployeeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view.UserDetailsViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.email.PasswordRequest;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.InvalidTokenException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.UserIsNotActiveException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.email.EmailService;
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

    @Autowired
    private EmployeeViewMapper employeeViewMapper;

    public TokenView doLogin(LoginForm form, AuthenticationManager authenticationManager) {
        User user = userRepository.findByEmail(form.email()).orElseThrow(
                () -> new EntityNotFoundException("User not found for email: " + form.email())
        );
        if (user.getDisabled()) throw new UserIsNotActiveException("Acesso negado.");
        var usernamePassword = new UsernamePasswordAuthenticationToken(form.email(), form.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((User) auth.getPrincipal());
        return tokenResponse(token, user);
    }

    private TokenView tokenResponse(String token, User user){
        return new TokenView(token, employeeViewMapper.map(user.getEmployee()));
    }

    public void requestRecoveryPassword(RecoveryPasswordForm form) {
        User user = userRepository.findByEmail(form.email()).orElseThrow(
                () -> new EntityNotFoundException("User not found for email: " + form.email())
        );

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

        User user = userRepository.findByEmail(emailFromToken).orElseThrow(
                () -> new EntityNotFoundException("User not found for email: " + emailFromToken)
        );

        user.setPassword(passwordEncoder.encode(form.password()));
        userRepository.save(user);
        tokenService.invalidateToken(form.token());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User not found for email: " + email)
        );
    }
}
