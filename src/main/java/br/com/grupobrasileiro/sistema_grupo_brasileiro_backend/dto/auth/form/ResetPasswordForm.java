package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordForm(
        @NotBlank(message = "Token is required")
        String token,

        @NotBlank(message = "Password is required")
        String password
) {
}
