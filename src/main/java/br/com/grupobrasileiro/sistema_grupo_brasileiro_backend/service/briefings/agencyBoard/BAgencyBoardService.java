package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.agencyBoard;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.BAgencyBoardsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form.BAgencyBoardFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form.OtherRouteFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form.RouteFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.BAgencyBoardDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.*;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BAgencyBoardService {

    @Autowired
    private BoardTypeRepository boardTypeRepository;

    @Autowired
    private AgencyBoardTypeRepository agencyBoardTypeRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private OtherRouteRepository otherRouteRepository;

    @Autowired
    private BAgencyBoardFormMapper bAgencyBoardFormMapper;

    @Autowired
    private RouteFormMapper routeFormMapper;

    @Autowired
    private OtherRouteFormMapper otherRouteFormMapper;

    @Autowired
    private BAgencyBoardRepository bAgencyBoardRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private RouteCityRepository routeCityRepository;

    @Autowired
    private BAgencyBoardDetailedViewMapper bAgencyBoardRegisterViewMapper;

    public void register(BAgencyBoardsForm bAgencyBoardsForm, Briefing briefing) {
        BAgencyBoard bAgencyBoard = bAgencyBoardFormMapper.map(bAgencyBoardsForm);

        AgencyBoardType agencyBoardType = agencyBoardTypeRepository.findById(bAgencyBoardsForm.idAgencyBoardType()).orElseThrow(
                () -> new EntityNotFoundException("Agency Board Type not found")
        );
        bAgencyBoard.setAgencyBoardType(agencyBoardType);

        if (bAgencyBoardsForm.idBoardType() != null){
            BoardType boardType = boardTypeRepository.findById(bAgencyBoardsForm.idAgencyBoardType()).orElseThrow(
                    () -> new EntityNotFoundException("Board Type not found")
            );
            bAgencyBoard.setBoardType(boardType);
        }

        bAgencyBoard.setBriefing(briefing);
        // TODO: Modularizar código para salvar rotas e outras rotas
        // TODO: Mudar a ordem de salvar rotas e outras rotas para evitar dependências entre eles
        Set<Route> routes = bAgencyBoardsForm.routes() != null ?
            bAgencyBoardsForm.routes().stream()
                .map(routesForm -> {
                    Route route = routeFormMapper.map(routesForm);
                    Set<RouteCity> routeCities = routesForm.idCities().stream().map(
                            idCity -> {
                                City city = cityRepository.findById(idCity).orElseThrow(
                                        () -> new EntityNotFoundException("City not found with id: " + idCity)
                                );
                                return new RouteCity(
                                        null,
                                        city,
                                        route
                                        );
                            }
                    ).collect(Collectors.toSet());
                    routeCityRepository.saveAll(routeCities);
                    route.setBAgencyBoard(bAgencyBoard); // Associa BAgencyBoard
                    return route;
                })
                .collect(Collectors.toSet()) : Set.of();
        bAgencyBoard.setRoutes(routes);

        Set<OtherRoute> otherRoutes = bAgencyBoardsForm.otherRoutes() != null ?
                bAgencyBoardsForm.otherRoutes().stream()
                        .map(otherRoutesForm -> {
                            OtherRoute otherRoute = otherRouteFormMapper.map(otherRoutesForm);
                            otherRoute.setBAgencyBoard(bAgencyBoard); // Associa BAgencyBoard
                            return otherRoute;
                        })
                        .collect(Collectors.toSet()) : Set.of();

        bAgencyBoard.setOtherRoutes(otherRoutes);

        bAgencyBoardRepository.saveAndFlush(bAgencyBoard);
        routeRepository.saveAll(routes);
        otherRouteRepository.saveAll(otherRoutes);
    }
}
