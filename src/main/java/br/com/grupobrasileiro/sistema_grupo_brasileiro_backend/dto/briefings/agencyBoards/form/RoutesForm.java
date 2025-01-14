package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record RoutesForm(
        @NotNull(message = "The route company cannot be null")
        @JsonAlias({"id_company"})
        Long idCompany,

        @NotNull(message = "The route cities cannot be null")
        @JsonAlias({"id_cities"})
        Set<Long> idCities,

        @NotBlank(message = "The route type cannot be blank")
        String type
) {
}
