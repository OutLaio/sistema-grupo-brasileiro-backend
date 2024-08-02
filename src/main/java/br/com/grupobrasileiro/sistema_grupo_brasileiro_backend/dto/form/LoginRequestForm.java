package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestForm(
		@Email
		@NotBlank
		String email, 
		
		@NotBlank
		String password){

}
