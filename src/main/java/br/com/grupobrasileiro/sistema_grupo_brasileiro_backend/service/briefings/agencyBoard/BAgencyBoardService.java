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

    public void register(BAgencyBoardsForm form, Briefing briefing) {
        BAgencyBoard bAgencyBoard = bAgencyBoardFormMapper.map(form);
        bAgencyBoard.setBriefing(briefing);
        bAgencyBoardRepository.saveAndFlush(bAgencyBoard);

        form.routes().forEach(
            routesForm -> {
                Route route = routeFormMapper.map(routesForm);
                route.setBAgencyBoard(bAgencyBoard);
                routeRepository.saveAndFlush(route);

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
            }
        );

        Set<OtherRoute> otherRoutes = form.otherRoutes().stream().map(
            otherRoutesForm -> {
                OtherRoute otherRoute = otherRouteFormMapper.map(otherRoutesForm);
                otherRoute.setBAgencyBoard(bAgencyBoard);
                return otherRoute;
            }
        ).collect(Collectors.toSet());
        otherRouteRepository.saveAll(otherRoutes);
    }
}
