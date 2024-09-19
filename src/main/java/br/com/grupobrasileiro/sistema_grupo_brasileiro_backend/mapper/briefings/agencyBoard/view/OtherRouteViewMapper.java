package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.OtherRouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.OtherRoute;

@Component
public class OtherRouteViewMapper implements Mapper<OtherRoute, OtherRouteView> {


    @Override
    public OtherRouteView map(OtherRoute otherRoute) {

        return new OtherRouteView(
                otherRoute.getId(),
                otherRoute.getCompany(),
                otherRoute.getCity(),
                otherRoute.getType()
        );
    }
}
