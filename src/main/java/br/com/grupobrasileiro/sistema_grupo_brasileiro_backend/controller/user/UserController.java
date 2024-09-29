package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.user;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.PasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Redefine a senha de um usuário.
     *
     * @param passwordForm o formulário PasswordForm contendo a nova senha e o ID do usuário
     * @return uma resposta com status 200 OK e uma mensagem de sucesso
     */
    @Operation(summary = "Redefinir senha", description = "Redefine a senha de um usuário no sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Password changed successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error in the data provided", content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody @Valid PasswordForm passwordForm) {
        userService.changePassword(passwordForm);
        return ResponseEntity.ok("Password successfully changed!");
    }

    /**
     * Desativa um usuário no sistema.
     * Apenas administradores ou o próprio usuário podem realizar essa ação.
     * 
     * @param id ID do usuário a ser desativado.
     * @return resposta com status 204 No Content se a operação for bem-sucedida.
     */
    @Operation(summary = "Deactivate user", description = "Deactivates a user in the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User successfully deactivated"),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.noContent().build();
    }
}
