package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view;

public record RouteView(
        Long id,
        CompanyCityView companyCityView,
        String type
) {
}
