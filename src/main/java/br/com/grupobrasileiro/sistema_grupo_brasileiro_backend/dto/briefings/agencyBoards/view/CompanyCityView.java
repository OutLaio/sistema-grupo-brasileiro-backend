package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.agencyBoards.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CityView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;

public record CompanyCityView(
        Long id,
        CityView city,
        CompanyView company
) {
}