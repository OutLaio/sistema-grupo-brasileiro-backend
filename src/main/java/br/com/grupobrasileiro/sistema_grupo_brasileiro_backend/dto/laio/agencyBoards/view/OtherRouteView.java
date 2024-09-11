package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.agencyBoards.view;

import java.util.List;

public record OtherRouteView(
        Long id,
        String company,
        List<String> cities,
        String type
) {
}
