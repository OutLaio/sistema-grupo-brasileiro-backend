package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.auth.form;

import jakarta.validation.constraints.NotBlank;

public record RecoveryPasswordForm(
        @NotBlank(message = "The email is required")
        String email
) {
}
