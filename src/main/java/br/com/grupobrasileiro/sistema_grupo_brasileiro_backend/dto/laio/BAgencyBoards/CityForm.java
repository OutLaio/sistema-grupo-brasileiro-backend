package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.BAgencyBoards;

import jakarta.validation.constraints.NotBlank;

public record CityForm(
        @NotBlank(message = "The city name cannot be blank")
        String name
) {
}
