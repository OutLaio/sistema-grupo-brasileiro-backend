package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.auth;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.Response;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.LoginForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.RecoveryPasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.ResetPasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.view.TokenView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.UserDetailsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.auth.AuthService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.EmployeeService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.UserService;
import com.auth0.jwt.exceptions.TokenExpiredException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Endpoint para registrar um novo colaborador.
     *
     * @param form {@link UserDetailsForm} contendo os dados de usuário e funcionário.
     * @return uma resposta HTTP 201 Created com uma mensagem de sucesso e a view do colaborador ou um erro apropriado.
     */
    @Operation(summary = "Register a new employee", description = "Create a new user and associated collaborator.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User registered successfully",
                content = @Content(schema = @Schema(implementation = Response.class))),
        @ApiResponse(responseCode = "400", description = "Invalid validation data", 
            content = @Content),
        @ApiResponse(responseCode = "409", description = "Email already exists", 
            content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDetailsForm form) {
        String requestId = UUID.randomUUID().toString();
        LOGGER.info("[{}] Iniciando registro de novo usuário.", requestId);

        String maskedEmail = maskEmail(form.userForm().email());
        LOGGER.debug("[{}] Dados recebidos para registro: email mascarado = {}",
                requestId, maskedEmail);

        User user = userService.create(form.userForm());
        LOGGER.debug("[{}] Usuário criado com sucesso. ID: {}", requestId, user.getId());

        EmployeeView employeeView = employeeService.addEmployee(form.employeeForm(), user);
        LOGGER.info("[{}] Usuário registrado com sucesso. ID do usuário: {}",
                requestId, employeeView.id());

        return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>("Novo usuário registrado com sucesso!", employeeView));
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
            content = @Content(schema = @Schema(implementation = Response.class))),
        @ApiResponse(responseCode = "401", description = "Invalid credentials", 
            content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginForm form){
        String requestId = UUID.randomUUID().toString();
        LOGGER.info("[{}] Iniciando solicitação de login.", requestId);

        String maskedEmail = maskEmail(form.email());
        LOGGER.debug("[{}] Dados recebidos para autenticação: email mascarado = {}",
                requestId, maskedEmail);

        TokenView view = authService.doLogin(form, authenticationManager);
        LOGGER.info("[{}] Autenticação bem-sucedida. Usuário: {}", requestId, maskedEmail);

        return ResponseEntity.ok().body(new Response<>("Login realizado com sucesso!", view));
    }

    /**
     * Endpoint para solicitar a redefinição de senha.
     *
     * @param form {@link RecoveryPasswordForm} contendo o e-mail do usuário.
     * @return mensagem de confirmação de envio do e-mail de redefinição.
     */
    @Operation(summary = "Request password reset", description = "Sends a password reset email.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reset email sent successfully",
                content = @Content(schema = @Schema(implementation = Response.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/requestReset")
    public ResponseEntity<?> requestReset(@Valid @RequestBody RecoveryPasswordForm form) {
        String requestId = UUID.randomUUID().toString();
        LOGGER.info("[{}] Iniciando solicitação de redefinição de senha.", requestId);

        String maskedEmail = maskEmail(form.email());
        LOGGER.debug("[{}] Dados recebidos para redefinição de senha: email mascarado = {}",
                requestId, maskedEmail);

        authService.requestRecoveryPassword(form);
        LOGGER.info("[{}] E-mail de redefinição de senha enviado com sucesso. Usuário: {}",
                requestId, maskedEmail);

        return ResponseEntity.ok(new Response<>("Sua solicitação de recuperação de senha foi enviada com sucesso! Verifique seu e-mail."));
    }

    /**
     * Endpoint para redefinir a senha do usuário.
     *
     * @param form {@link ResetPasswordForm} contendo os novos dados de senha.
     * @return mensagem de sucesso após a alteração da senha.
     */
    @Operation(summary = "Reset Password", description = "Updates the user's password based on the provided credentials")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Password changed successfully",
                content = @Content(schema = @Schema(implementation = Response.class))),
        @ApiResponse(responseCode = "400", description = "Invalid token or invalid data")
    })
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordForm form) {
        String requestId = UUID.randomUUID().toString();
        LOGGER.info("[{}] Iniciando operação de redefinição de senha.", requestId);

        LOGGER.debug("[{}] Dados recebidos para redefinição de senha: token = {}",
                requestId, form.token());

        authService.resetPassword(form);
        LOGGER.info("[{}] Redefinição de senha concluída com sucesso.", requestId);

        return ResponseEntity.ok(new Response<>("Senha alterada com sucesso!"));
    }

    /**
     * Mascarar o email para evitar a exposição completa nos logs.
     * Exemplo: "email@email.com" -> "e***@e***.com"
     */
    private String maskEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) {
            return "****" + email.substring(atIndex);
        }
        return email.charAt(0) + "***" + email.substring(atIndex - 1);
    }

    /**
     * Endpoint para gerar um link de cadastro de cliente.
     *
     * @return mensagem de confirmação de link gerado com o link gerado.
     */
    @Operation(summary = "Request register client", description = "Sends link for register client.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register client request successfully",
                    content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping("/requestRegister")
    public ResponseEntity<?> requestRegister() {
        String requestId = UUID.randomUUID().toString();
        LOGGER.info("[{}] Iniciando solicitação de geração de link para cadastro de cliente.", requestId);

        String link = authService.requestRegister();
        LOGGER.info("[{}] Link de registro de cliente gerado com sucesso! Link: [{}]",
                requestId, link);

        return ResponseEntity.ok(new Response<>("Link de cadastro de cliente gerado com sucesso!", link));
    }

    /**
     * Endpoint para verificar um link de cadastro de cliente.
     *
     * @return mensagem de confirmação de link verificado.
     */
    @Operation(summary = "Verify register client link", description = "Verify link for register client.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register client request verified",
                    content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client request link")
    })
    @PostMapping("/verifyToken")
    public ResponseEntity<?> verifyRequestRegister(@RequestParam String token) {
        String requestId = UUID.randomUUID().toString();
        LOGGER.info("[{}] Iniciando verificação de link para cadastro de cliente. Token: {}", requestId, token);

        authService.verifyToken(token);
        LOGGER.info("[{}] Link de registro de cliente verificado com sucesso!", requestId);

        return ResponseEntity.ok(new Response<>("Link de cadastro de cliente verificado com sucesso!"));
    }

}
