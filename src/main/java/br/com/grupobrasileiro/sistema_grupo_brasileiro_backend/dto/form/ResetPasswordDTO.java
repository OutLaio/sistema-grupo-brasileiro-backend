package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordDTO(
	    @NotBlank(message = "Token cannot be empty")
	    String token,

	    @NotBlank(message = "New password cannot be empty")
	    @Size(min = 6, max = 8, message = "Password must be between 6 and 8 characters long")
	    String newPassword
	) {}