package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.auth.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginForm(
        @NotBlank(message = "The email field cannot be blank")
        String email,

        @NotNull(message = "The password field cannot be null")
        String password
) {
}
