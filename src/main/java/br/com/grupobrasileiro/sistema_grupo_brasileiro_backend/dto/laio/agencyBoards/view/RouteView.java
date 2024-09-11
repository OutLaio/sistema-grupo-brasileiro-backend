package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.agencyBoards.view;

import java.util.List;

public record RouteView(
        Long id,
        CompanyView company,
        List<CityView> cities,
        String type
) {
}
