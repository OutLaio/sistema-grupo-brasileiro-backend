package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateBAgencyBoardForm(
		@NotBlank(message = "BoardLocation is required!")
		String boardLocation,

		@NotNull(message = "CompanySharing is required!")
	    Boolean companySharing,

	    @NotBlank(message = "BoardType is required!")
		@Size(max = 20, message = "BoardType must be less than 50 characters!") 
	    String boardType,

	    @NotBlank(message = "Material is required!")
	    String material,
	    
	    String observations
) {}
