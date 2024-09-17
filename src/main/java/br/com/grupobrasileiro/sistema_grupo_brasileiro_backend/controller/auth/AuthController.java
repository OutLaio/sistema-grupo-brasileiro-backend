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
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
    private AuthService authService;

    /**
     * Endpoint para registrar um novo colaborador.
     *
     * @param form {@link UserDetailsForm} contendo os dados de usuário e funcionário.
     * @return uma resposta HTTP 201 Created com a visão do colaborador ou um erro apropriado.
     */
    @Operation(summary = "Registrar um novo colaborador", description = "Cria um novo usuário e um colaborador associado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso", 
            content = @Content(schema = @Schema(implementation = EmployeeView.class))),
        @ApiResponse(responseCode = "400", description = "Dados de validação inválidos", 
            content = @Content),
        @ApiResponse(responseCode = "409", description = "E-mail já existente", 
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
    @Operation(summary = "Realizar login", description = "Autentica o usuário com as credenciais fornecidas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login bem-sucedido", 
            content = @Content(schema = @Schema(implementation = TokenView.class))),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas", 
            content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginForm form){
        LOGGER.info("Iniciando solicitação de login para: email={}", form.email());
        String token = authService.doLogin(form);
        LOGGER.info("Autenticação bem-sucedida para: email={}", form.email());
        return ResponseEntity.ok(new TokenView(token));
    }

    /**
     * Endpoint para solicitar a redefinição de senha.
     *
     * @param form {@link RecoveryPasswordForm} contendo o e-mail do usuário.
     * @return mensagem de confirmação de envio do e-mail de redefinição.
     */
    @Operation(summary = "Solicitar redefinição de senha", description = "Envia um e-mail para redefinição de senha.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "E-mail de redefinição enviado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
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
    @Operation(summary = "Redefinir senha", description = "Atualiza a senha do usuário com base nas credenciais fornecidas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Token inválido ou dados inválidos")
    })
    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPasswordForm form) {
        LOGGER.info("Iniciando operação de redefinição de senha");
        authService.resetPassword(form);
        LOGGER.info("Senha alterada com sucesso");
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }
}
