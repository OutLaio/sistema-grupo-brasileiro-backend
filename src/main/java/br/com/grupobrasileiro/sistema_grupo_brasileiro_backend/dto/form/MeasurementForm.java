package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import jakarta.validation.constraints.NotBlank;

public record MeasurementForm(
	@NotBlank(message = "Height is required!") 
	float height,
	
	@NotBlank(message = "Length is required!") 
	float length,
	
	Long bAgencyBoardId) {

}
