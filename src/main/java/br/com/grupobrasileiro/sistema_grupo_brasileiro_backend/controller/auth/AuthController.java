package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.auth;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.LoginForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.RecoveryPasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.ResetPasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.view.TokenView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.UserDetailsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.auth.AuthService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.EmployeeService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para autenticação e registro de usuários.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;
    private final EmployeeService employeeService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    /**
     * Endpoint para registrar um novo colaborador.
     *
     * @param form o {@link UserDetailsForm} contendo os dados de usuário e funcionário.
     * @return uma resposta HTTP 201 Created com os detalhes do colaborador registrado.
     */
    @PostMapping("/register")
    public ResponseEntity<EmployeeView> register(@Valid @RequestBody UserDetailsForm form) {
        User user = userService.create(form.userForm());
        EmployeeView employeeView = employeeService.addEmployee(form.employeeForm(), user);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeView);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenView> login(@Valid @RequestBody LoginForm form) {
        LOGGER.info("Starting login request for: email={}", form.email());
        String token = authService.doLogin(form, authenticationManager);
        LOGGER.info("Successful authentication for: email={}", form.email());
        return ResponseEntity.ok(new TokenView(token));
    }

    @PostMapping("/requestReset")
    public ResponseEntity<String> requestReset(@Valid @RequestBody RecoveryPasswordForm form) {
        LOGGER.info("Starting password reset request for: {}", form.email());
        authService.requestRecoveryPassword(form);
        LOGGER.info("Password reset email sent to: {}", form.email());
        return ResponseEntity.ok("E-mail enviado com sucesso!");
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordForm form) {
        LOGGER.info("Starting password reset operation");
        authService.resetPassword(form);
        LOGGER.info("Password successfully reset");
        return ResponseEntity.ok("Password successfully changed!");
    }
}
