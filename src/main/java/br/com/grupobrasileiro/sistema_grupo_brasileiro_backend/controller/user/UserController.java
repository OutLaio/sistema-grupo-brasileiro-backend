package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.user;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.Response;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.PasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.UserService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador REST para operações relacionadas a usuários e empregados.
 * Expondo endpoints da API sob o caminho "/api/v1/users".
 */
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Operations related to users in the system")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    /**
     * Redefine a senha de um usuário.
     *
     * @param passwordForm o formulário PasswordForm contendo a nova senha e o ID do usuário
     * @return uma resposta com status 200 OK e uma mensagem de sucesso
     */
    @Operation(summary = "Redefinir senha", description = "Redefine a senha de um usuário no sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Password changed successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error in the data provided",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordForm passwordForm) {
        String requestId = UUID.randomUUID().toString();
        logger.info("[{}] Iniciando processo de alteração de senha para o usuário.", requestId);

        userService.changePassword(passwordForm);
        logger.info("[{}] Senha alterada com sucesso.", requestId);

        return ResponseEntity.ok().body(new Response<>("Senha alterada com sucesso!"));
    }

    /**
     * Desativa um usuário no sistema.
     * Apenas administradores ou o próprio usuário podem realizar essa ação.
     * 
     * @param id ID do usuário a ser desativado.
     * @return resposta com status 200 OK e uma mensagem de sucesso.
     */
    @Operation(summary = "Deactivate user", description = "Deactivates a user in the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User successfully deactivated",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateUser(@PathVariable Long id) {
        String requestId = UUID.randomUUID().toString();
        logger.info("[{}] Iniciando desativação do usuário com ID: {}", requestId, id);

        userService.deactivateUser(id);
        logger.info("[{}] Usuário desativado com sucesso. ID do usuário: {}", requestId, id);

        return ResponseEntity.ok().body(new Response<>("Usuário desativado com sucesso!"));
    }
}
