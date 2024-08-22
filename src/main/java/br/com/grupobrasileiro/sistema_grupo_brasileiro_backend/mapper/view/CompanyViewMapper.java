package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Company;



@Component
public class CompanyViewMapper implements Mapper<Company, CompanyView>{

    @Override
    public CompanyView map(Company i) {
 	
        return new CompanyView(
            i.getId(),
            i.getName()
        );
    }

}
