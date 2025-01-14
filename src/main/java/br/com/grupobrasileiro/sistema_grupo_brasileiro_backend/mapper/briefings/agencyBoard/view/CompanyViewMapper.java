package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyViewMapper implements Mapper<Company, CompanyView> {

    @Override
    public CompanyView map(Company company) {
        return new CompanyView(
                company.getId(),
                company.getName()
        );
    }
}
