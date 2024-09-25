package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.RouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyCityView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteViewMapper implements Mapper<Route, RouteView> {

    @Autowired
    private CompanyCityViewMapper companyCityViewMapper;

    @Override
    public RouteView map(Route route) {
        if (route == null) {
            return null;
        }
        CompanyCityView companyCityView = route.getCompanyCity() != null ? companyCityViewMapper.map(route.getCompanyCity()) : null;
        return new RouteView(
                route.getId(),
                companyCityView,
                route.getType()
        );
    }
}
