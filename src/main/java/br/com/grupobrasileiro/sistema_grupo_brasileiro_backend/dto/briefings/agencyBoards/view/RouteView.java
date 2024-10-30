package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view;

import java.util.Set;

public record RouteView(
        Long id,
        CompanyView company,
        Set<CityView> cities,
        String type
) {
}
