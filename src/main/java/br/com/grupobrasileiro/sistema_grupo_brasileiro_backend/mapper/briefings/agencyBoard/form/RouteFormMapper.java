package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.RoutesForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.CompanyCityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteFormMapper implements Mapper<RoutesForm, Route> {

    @Autowired
    private CompanyCityRepository companyCityRepository;

    @Override
    public Route map(RoutesForm routesForm) {
        return new Route(
                null,
                null,
                null,
                routesForm.type()
        );
    }
}
