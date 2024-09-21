package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.user;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.PasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Controlador REST para operações relacionadas a usuários e empregados.
 * Expondo endpoints da API sob o caminho "/api/v1/users".
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * Redefine a senha de um usuário.
     *
     * @param passwordForm o formulário PasswordForm contendo a nova senha e o ID do usuário
     * @return uma resposta com status 200 OK e uma mensagem de sucesso
     */
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody @Valid PasswordForm passwordForm) {
        LOGGER.info("Starting password change for userId={}", passwordForm.id());
        userService.changePassword(passwordForm);
        LOGGER.info("Password successfully changed for userId={}", passwordForm.id());
        return ResponseEntity.ok("Password successfully changed!");
    }

    /**
     * Desativa um usuário no sistema.
     * Apenas administradores ou o próprio usuário podem realizar essa ação.
     *
     * @param id ID do usuário a ser desativado.
     * @return resposta com status 204 No Content se a operação for bem-sucedida.
     */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        LOGGER.info("Deactivating user with id={}", id);
        userService.deactivateUser(id);
        LOGGER.info("Successfully deactivated user with id={}", id);
        return ResponseEntity.noContent().build();
    }
}
