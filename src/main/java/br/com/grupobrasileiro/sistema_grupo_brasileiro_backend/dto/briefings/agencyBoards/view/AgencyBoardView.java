package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view;

import java.util.List;

public record AgencyBoardView(
        Long id,
        AgencyBoardTypeView agencyBoardType,
        BoardTypeView boardType,
        List<RouteView> routes,
        List<OtherRouteView> otherRoutes,
        String boardLocation,
        String observations
) {
}
