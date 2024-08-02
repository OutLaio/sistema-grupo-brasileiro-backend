package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ResetPasswordForm(
	    @NotBlank(message = "Token cannot be empty")
	    String token,

        @NotBlank(message = "Password is required!")
        @Size(min = 8, message = "Password must be at least 8 characters long!")
        @Pattern(regexp = "^(?=.*[a-z]).*$", message = "Password must contain at least one lowercase letter!")
        @Pattern(regexp = "^(?=.*[A-Z]).*$", message = "Password must contain at least one uppercase letter!")
        @Pattern(regexp = "^(?=.*\\d).*$", message = "Password must contain at least one digit!")
        @Pattern(regexp = "^(?=.*[@$!%*?&]).*$", message = "Password must contain at least one special character!")
	    String newPassword
	) {}