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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controlador REST para autenticação e registro de usuários.
 * Este controlador expõe endpoints relacionados à autenticação e operações
 * de registro de colaboradores.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final EmployeeService employeeService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    /**
     * Endpoint para registrar um novo colaborador.
     *
     * <p>Este método recebe os detalhes do usuário e do funcionário através do {@link UserDetailsForm},
     * cria um novo usuário no sistema e, em seguida, persiste os dados do funcionário associados
     * ao usuário recém-criado.</p>
     *
     * <ul>
     *     <li>Chama o {@code UserService} para validar e criar o usuário com base nas credenciais e dados fornecidos.</li>
     *     <li>Chama o {@code EmployeeService} para instanciar e persistir o colaborador associado ao usuário.</li>
     *     <li>Retorna uma resposta com o status HTTP 200 (OK) em caso de sucesso.</li>
     * </ul>
     *
     * @param form o {@link UserDetailsForm} contendo os dados de usuário e funcionário.
     * @return uma resposta HTTP 200 OK em caso de sucesso, ou um erro apropriado se ocorrer alguma validação.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDetailsForm form) {
        User user = userService.create(form.userForm());
        EmployeeView employeeView = employeeService.addEmployee(form.employeeForm(), user);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeView);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginForm form){
        LOGGER.info("Starting login request for: email={}", form.email());
        String token = authService.doLogin(form);
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
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPasswordForm form) {
        LOGGER.info("Starting password reset operation");
        authService.resetPassword(form);
        LOGGER.info("Password successfully reset");
        return ResponseEntity.ok("Password successfully changed!");
    }
}
