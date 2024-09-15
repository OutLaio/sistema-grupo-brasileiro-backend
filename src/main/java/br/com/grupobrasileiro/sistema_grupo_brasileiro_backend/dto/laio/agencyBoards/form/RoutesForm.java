package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.agencyBoards.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoutesForm(
        @NotNull(message = "The route company city cannot be null")
        @JsonAlias({"id_companyCity"})
        Long idCompanyCity,

        @NotBlank(message = "The route type cannot be blank")
        String type
) {
}
