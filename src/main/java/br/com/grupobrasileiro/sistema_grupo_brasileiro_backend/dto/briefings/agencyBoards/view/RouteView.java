package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.agencyBoards.view.CompanyCityView;


public record RouteView(
        Long id,

        CompanyCityView companyCityView,


        String type
) {
}
