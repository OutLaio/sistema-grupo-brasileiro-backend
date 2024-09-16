package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.user;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form.PasswordForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    private UserService userService;

    /**
     * Redefine a senha de um usuário.
     *
     * @param passwordForm o formulário PasswordForm contendo a nova senha e o ID do usuário
     * @return uma resposta com status 200 OK e uma mensagem de sucesso
     */
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
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.noContent().build();
    }
}
