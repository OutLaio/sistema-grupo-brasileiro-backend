package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ItineraryForm(
    
    @NotBlank(message = "Main itinerary is required!")
    String main,

    @NotBlank(message = "Connections are required!")
    String connections,

    @NotNull(message = "BAgencyBoard ID is required!")
    Long bAgencyBoardId,

    @NotNull(message = "Company ID is required!")
    Long companyId
) {
}
