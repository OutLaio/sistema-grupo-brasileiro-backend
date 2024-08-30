package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CityForm(
        @NotBlank(message = "City name is required!")
        @Size(max = 255, message = "City name must be less than 255 characters!")
        String name
) {

}
