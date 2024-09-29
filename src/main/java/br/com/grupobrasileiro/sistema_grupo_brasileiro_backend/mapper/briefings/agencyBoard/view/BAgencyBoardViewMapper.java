package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.OtherRouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.RouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BAgencyBoardViewMapper implements Mapper<BAgencyBoard, BAgencyBoardView> {

    @Autowired
    private BoardTypeViewMapper boardTypeViewMapper;

    @Autowired
    private AgencyBoardTypeViewMapper agencyBoardTypeViewMapper;

    @Autowired
    private RouteViewMapper routeViewMapper;

    @Autowired
    private OtherRouteViewMapper otherRouteViewMapper;

    @Override
    public BAgencyBoardView map(BAgencyBoard bAgencyBoard) {
        if (bAgencyBoard == null) {
            return null;
        }

        Set<RouteView> routeViews = bAgencyBoard.getRoutes() != null ?
                bAgencyBoard.getRoutes().stream()
                        .map(routeViewMapper::map)
                        .collect(Collectors.toSet()) : Set.of();

        Set<OtherRouteView> otherRouteViews = bAgencyBoard.getOtherRoutes() != null ?
                bAgencyBoard.getOtherRoutes().stream()
                        .map(otherRouteViewMapper::map)
                        .collect(Collectors.toSet()) : Set.of();


        return new BAgencyBoardView(
                bAgencyBoard.getId(),
                agencyBoardTypeViewMapper.map(bAgencyBoard.getAgencyBoardType()),
                bAgencyBoard.getBoardType() != null ? boardTypeViewMapper.map(bAgencyBoard.getBoardType()) : null,
                routeViews,
                otherRouteViews,
                bAgencyBoard.getBoardLocation(),
                bAgencyBoard.getObservations()

        );
    }
}
