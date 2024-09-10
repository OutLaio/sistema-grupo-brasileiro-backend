package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.BAgencyBoards;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoutesForm(
        @NotNull(message = "The route company cannot be null")
        CompanyForm company,

        @NotNull(message = "The route city cannot be null")
        CityForm city,

        @NotBlank(message = "The route type cannot be blank")
        String type
) {
}
