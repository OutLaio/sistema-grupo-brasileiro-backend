package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OtherRouteForm(
    @NotNull(message = "BAgencyBoardId is required!")
    Long bAgencyBoardId,

    @NotBlank(message = "Company is required!")
    @Size(max = 255, message = "Company must be less than 255 characters!")
    String company,

    @NotBlank(message = "City is required!")
    @Size(max = 255, message = "City must be less than 255 characters!")
    String city,

    @NotBlank(message = "Type is required!")
    @Size(max = 255, message = "Type must be less than 255 characters!")
    String type) {

}
