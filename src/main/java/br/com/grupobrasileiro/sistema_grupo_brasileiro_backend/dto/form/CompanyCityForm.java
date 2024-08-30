package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import jakarta.validation.constraints.NotNull;

public record CompanyCityForm(
        @NotNull(message = "CityId is required!")
        Long cityId,

        @NotNull(message = "CompanyId is required!")
        Long companyId
) {

}
