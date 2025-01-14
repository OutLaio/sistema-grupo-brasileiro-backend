package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordRequest(
	    @NotBlank(message = "Sender email cannot be empty")
	    @Email(message = "Invalid sender email format")
	    String emailFrom,

	    @NotBlank(message = "Recipient email cannot be empty")
	    @Email(message = "Invalid recipient email format")
	    String emailTo,

	    @NotBlank(message = "Subject cannot be empty")
	    @Size(max = 100, message = "Subject cannot be longer than 100 characters")
	    String subject,

	    @NotBlank(message = "Email body cannot be empty")
	    String text
		) {

}
