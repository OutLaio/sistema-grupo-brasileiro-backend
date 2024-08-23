package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view;

public record ItineraryView(
    
    Long id,
    String main,
    String connections,
    Long bAgencyBoardId,
    Long companyId
) {
}
