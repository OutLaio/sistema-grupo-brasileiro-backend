package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RouteForm(
    @NotNull(message = "BAgencyBoardId is required!")
    Long bAgencyBoardId,

    @NotNull(message = "CityCompanyId is required!")
    Long cityCompanyId,

    @NotBlank(message = "Type is required!")
    @Size(max = 255, message = "Type must be less than 255 characters!")
    String type) {

}
