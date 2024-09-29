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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
@Tag(name = "Authentication", description = "Authentication management and access control")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Endpoint para registrar um novo colaborador.
     *
     * @param form {@link UserDetailsForm} contendo os dados de usuário e funcionário.
     * @return uma resposta HTTP 201 Created com a visão do colaborador ou um erro apropriado.
     */
    @Operation(summary = "Register a new employee", description = "Create a new user and associated collaborator.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User registered successfully", 
            content = @Content(schema = @Schema(implementation = EmployeeView.class))),
        @ApiResponse(responseCode = "400", description = "Invalid validation data", 
            content = @Content),
        @ApiResponse(responseCode = "409", description = "Email already exists", 
            content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDetailsForm form) {
        User user = userService.create(form.userForm());
        EmployeeView employeeView = employeeService.addEmployee(form.employeeForm(), user);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeView);
    }

    /**
     * Endpoint para realizar login de um usuário.
     *
     * @param form {@link LoginForm} contendo as credenciais de login.
     * @return token JWT para autenticação do usuário.
     */
    @Operation(summary = "Login", description = "Authenticates the user with the provided credentials.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful", 
            content = @Content(schema = @Schema(implementation = TokenView.class))),
        @ApiResponse(responseCode = "401", description = "Invalid credentials", 
            content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginForm form){
        LOGGER.info("Iniciando solicitação de login para: email={}", form.email());
        TokenView view = authService.doLogin(form, authenticationManager);
        LOGGER.info("Autenticação bem-sucedida para: email={}", form.email());
        return ResponseEntity.ok(view);
    }

    /**
     * Endpoint para solicitar a redefinição de senha.
     *
     * @param form {@link RecoveryPasswordForm} contendo o e-mail do usuário.
     * @return mensagem de confirmação de envio do e-mail de redefinição.
     */
    @Operation(summary = "Request password reset", description = "Sends a password reset email.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reset email sent successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/requestReset")
    public ResponseEntity<String> requestReset(@Valid @RequestBody RecoveryPasswordForm form) {
        LOGGER.info("Solicitação de redefinição de senha para: {}", form.email());
        authService.requestRecoveryPassword(form);
        LOGGER.info("E-mail de redefinição de senha enviado para: {}", form.email());
        return ResponseEntity.ok("E-mail enviado com sucesso!");
    }

    /**
     * Endpoint para redefinir a senha do usuário.
     *
     * @param form {@link ResetPasswordForm} contendo os novos dados de senha.
     * @return mensagem de sucesso após a alteração da senha.
     */
    @Operation(summary = "Reset Password", description = "Updates the user's password based on the provided credentials")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Password changed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid token or invalid data")
    })
    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPasswordForm form) {
        LOGGER.info("Iniciando operação de redefinição de senha");
        authService.resetPassword(form);
        LOGGER.info("Senha alterada com sucesso");
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }
}
