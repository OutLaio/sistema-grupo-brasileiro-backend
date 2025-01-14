package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.RouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RouteViewMapper implements Mapper<Route, RouteView> {

    @Autowired
    private CompanyViewMapper companyViewMapper;

    @Autowired
    private CityViewMapper cityViewMapper;

    @Override
    public RouteView map(Route route) {
        if (route == null) {
            throw new NullPointerException("Route at Mapper is null");
        }

        System.out.println("RouteCities: " +
                route.getRouteCities()
        );
        return new RouteView(
                route.getId(),
                companyViewMapper.map(route.getCompany()),
                route.getRouteCities().stream().map(
                        routeCity -> cityViewMapper.map(routeCity.getCity())
                ).collect(Collectors.toSet()),
                route.getType()
        );
    }
}
