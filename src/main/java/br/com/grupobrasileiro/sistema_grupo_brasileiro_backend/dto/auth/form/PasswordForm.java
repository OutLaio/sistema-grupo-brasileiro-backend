package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.annotations.Password;

/**
 * DTO responsável pela solicitação de redefinição de senha do usuário.
 *
 * Este objeto contém os dados necessários para realizar a operação de alteração de senha,
 * incluindo a identificação do usuário, a senha atual e a nova senha.
 */
@Schema(description = "DTO responsável pela solicitação de redefinição de senha do usuário. Contém ID do usuário, senha atual e nova senha.")
public record PasswordForm(

        /**
         * Identificador único do usuário para quem a senha será redefinida.
         * Este campo é obrigatório e deve corresponder a um ID de um usuário registrado no sistema.
         *
         * Exemplo: 12345
         */
        @NotNull(message = "The Id user is required")
        @Schema(description = "O ID único do usuário. Este campo é obrigatório.",
                example = "12345")
        Long idUser,

        /**
         * A senha atual do usuário.
         * Este campo é obrigatório e deve corresponder à senha atualmente registrada para o usuário.
         *
         * Exemplo: "currentPassword123"
         */
        @NotNull(message = "The Current Password is required")
        @Schema(description = "A senha atual do usuário. Este campo é obrigatório.",
                example = "currentPassword123")
        String currentPassword,

        /**
         * A nova senha do usuário.
         * Este campo é obrigatório e deve atender aos requisitos de segurança especificados pela anotação {@link Password}.
         * A senha deve ser forte, contendo letras maiúsculas, minúsculas, números e caracteres especiais.
         *
         * Exemplo: "NewPassword@2024"
         */
        @Password
        @Schema(description = "A nova senha do usuário. Este campo é obrigatório e deve seguir as regras de segurança especificadas pela anotação Password.",
                example = "NewPassword@2024")
        String newPassword
) { }
