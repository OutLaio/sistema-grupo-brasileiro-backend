package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view;

import java.util.List;
import java.util.Set;

public record BAgencyBoardView(
        Long id,
        AgencyBoardTypeView agencyBoardType,
        BoardTypeView boardType,
        Set<RouteView> routes,
        Set<OtherRouteView> otherRoutes,
        String boardLocation,
        String observations
) {
}
