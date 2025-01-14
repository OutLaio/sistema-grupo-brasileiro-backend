package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO responsável pela solicitação de recuperação de senha através do email do usuário.
 *
 * Este objeto é utilizado para enviar a solicitação de recuperação de senha para o usuário.
 * O único campo necessário é o email associado à conta do usuário.
 */
@Schema(description = "DTO responsável pela solicitação de recuperação de senha do usuário. Contém o email associado à conta para recuperação.")
public record RecoveryPasswordForm(

        /**
         * O email associado à conta do usuário para o qual a recuperação de senha será enviada.
         * Este campo é obrigatório e deve ser um email válido.
         *
         * Exemplo: "usuario@exemplo.com"
         */
        @NotBlank(message = "The email is required")
        @Schema(description = "O email do usuário para o qual a recuperação de senha será solicitada. Este campo é obrigatório e deve ser um email válido.",
                example = "usuario@exemplo.com")
        String email
) { }
