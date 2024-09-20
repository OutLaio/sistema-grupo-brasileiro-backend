package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CityView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.City;
import org.springframework.stereotype.Component;

@Component
public class CityViewMapper implements Mapper<City, CityView> {

    @Override
    public CityView map(City city) {
        return new CityView(
                city.getId(),
                city.getName()
        );
    }
}
