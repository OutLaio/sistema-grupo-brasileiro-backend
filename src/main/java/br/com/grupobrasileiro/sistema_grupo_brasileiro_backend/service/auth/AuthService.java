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
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


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

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${client.url}")
    private String clientUrl;

    public TokenView doLogin(LoginForm form, AuthenticationManager authenticationManager) {
        User user = userRepository.findByEmail(form.email()).orElseThrow(
                () -> new EntityNotFoundException("O e-mail informado não está cadastrado. Verifique os dados e tente novamente.")
        );
        if (user.getDisabled()) throw new UserIsNotActiveException("Sua conta está desativada. Entre em contato com o administrador para mais informações.");
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

        String token = tokenService.generatePasswordToken(user);
        String resetUrl = clientUrl + "/redefinir-senha?token=" + token;
        String userName = user.getEmployee().getName() + " " + user.getEmployee().getLastName();

        Context context = new Context();
        context.setVariable("resetUrl", resetUrl);
        context.setVariable("userName", userName);

        String body = templateEngine.process("request-password", context);

        PasswordRequest sendEmailForm = new PasswordRequest("sgsm.noreply@gmail.com", form.email(), "Password Reset", body);
        emailService.send(sendEmailForm);
    }

    public void resetPassword(ResetPasswordForm form) {
        String emailFromToken = tokenService.validatePasswordToken(form.token());
        if (emailFromToken == null) {
            throw new InvalidTokenException("Link expirado ou inválido! Por favor, solicite um novo link de recuperação.");
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

    public String requestRegister() {
        String token = tokenService.generateRegisterToken();
        return clientUrl + "/cadastro?token=" + token;
    }

    public void verifyToken(String token) {
        if (tokenService.validateRegisterToken(token) == null)
            throw new TokenExpiredException("Link expirado ou inválido! Por favor, solicite um novo link.", tokenService.expireOn(token));
    }
}
