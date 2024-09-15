package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.joao.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.agencyBoards.view.*;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.joao.form.OtherRouteFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;
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
                boardTypeViewMapper.map(bAgencyBoard.getBoardType()),
                routeViews,
                otherRouteViews,
                bAgencyBoard.getBoardLocation(),
                bAgencyBoard.getObservations()

        );
    }
}
