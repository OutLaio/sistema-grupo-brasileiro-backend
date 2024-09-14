package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.Mikaelle.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.user.annotations.Password;
import jakarta.validation.constraints.NotNull;

/**
 * Representa um formulário de solicitação para redefinição de senha.
 */
public record PasswordForm(
        /**
         * Nova senha do usuário.
         * <p>
         * Deve atender aos requisitos especificados pela anotação {@link Password}.
         * </p>
         */
        @Password
        String passwordNew,

        /**
         * Identificador único do usuário para quem a senha será redefinida.
         */
        @NotNull(message = "The Id user  is required")
        Long id) {

}
