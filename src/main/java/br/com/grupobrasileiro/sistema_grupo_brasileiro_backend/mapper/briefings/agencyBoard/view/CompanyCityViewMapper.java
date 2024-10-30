package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyCityViewMapper implements Mapper<RouteCity, CompanyCityView> {

    @Autowired
    private CityViewMapper cityViewMapper;

    @Autowired
    private CompanyViewMapper companyViewMapper;

    @Override
    public CompanyCityView map(RouteCity companyCity) {
        if (companyCity == null) {
            return null;
        }

        return new CompanyCityView(
                companyCity.getId(),
                companyCity.getCity() != null ? cityViewMapper.map(companyCity.getCity()) : null,
                companyCity.getCompany() != null ? companyViewMapper.map(companyCity.getCompany()) : null
        );
    }
}
