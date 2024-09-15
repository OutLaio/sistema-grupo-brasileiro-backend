package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.joao.briefings.agencyBoard;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.agencyBoard.BAgencyBoardRegisterView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.signpost.SignpostRegisterView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.agencyBoards.form.BAgencyBoardsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.joao.form.BAgencyBoardFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.joao.form.OtherRouteFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.joao.form.RouteFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.joao.view.BAgencyBoardRegisterViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.joao.view.BAgencyBoardViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.joao.view.BSignpostRegisterViewMapper;
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
    private CompanyCityRepository companyCityRepository;

    @Autowired
    private OtherRouteFormMapper otherRouteFormMapper;

    @Autowired
    private BAgencyBoardRepository bAgencyBoardRepository;

    @Autowired
    private BAgencyBoardRegisterViewMapper bAgencyBoardRegisterViewMapper;

    public BAgencyBoardRegisterView register(BAgencyBoardsForm bAgencyBoardsForm, Briefing briefing) {
        BoardType boardType = boardTypeRepository.getReferenceById(bAgencyBoardsForm.idAgencyBoardType());
        AgencyBoardType agencyBoardType = agencyBoardTypeRepository.getReferenceById(bAgencyBoardsForm.idAgencyBoardType());
        BAgencyBoard bAgencyBoard = bAgencyBoardFormMapper.map(bAgencyBoardsForm);

        bAgencyBoard.setBoardType(boardType);
        bAgencyBoard.setAgencyBoardType(agencyBoardType);
        bAgencyBoard.setBriefing(briefing);

        Set<Route> routes = bAgencyBoardsForm.routes() != null ?
                bAgencyBoardsForm.routes().stream()
                        .map(routesForm -> {
                            Route route = routeFormMapper.map(routesForm);
                            CompanyCity companyCity = companyCityRepository.findById(routesForm.idCompanyCity())
                                    .orElseThrow(() -> new IllegalArgumentException("CompanyCity not found"));
                            route.setCompanyCity(companyCity);
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



        return bAgencyBoardRegisterViewMapper.map(bAgencyBoard);
    }
}
