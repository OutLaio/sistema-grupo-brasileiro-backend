package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO responsável pela solicitação de redefinição de senha utilizando um token.
 *
 * Este objeto contém o token de recuperação enviado ao usuário e a nova senha que será definida.
 * Ambos os campos são obrigatórios para concluir o processo de redefinição de senha.
 */
@Schema(description = "DTO para solicitação de redefinição de senha. Contém o token de recuperação e a nova senha do usuário.")
public record ResetPasswordForm(

        /**
         * Token de recuperação gerado e enviado ao usuário.
         * Este token é necessário para validar a solicitação de redefinição de senha.
         *
         * Exemplo: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
         */
        @NotBlank(message = "Token is required")
        @Schema(description = "Token de recuperação gerado e enviado ao usuário. Este campo é obrigatório para validar a solicitação de redefinição de senha.",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token,

        /**
         * Nova senha que será atribuída ao usuário.
         * Este campo é obrigatório e a senha deve atender aos requisitos definidos.
         *
         * Exemplo: "NovaSenha123!"
         */
        @NotBlank(message = "Password is required")
        @Schema(description = "A nova senha do usuário. Este campo é obrigatório e deve atender aos requisitos de segurança definidos.",
                example = "NovaSenha123!")
        String password
) { }
