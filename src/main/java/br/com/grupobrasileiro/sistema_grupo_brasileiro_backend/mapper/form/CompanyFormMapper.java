package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;



import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.CompanyForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Company;

@Component
public class CompanyFormMapper implements Mapper<CompanyForm, Company>{
    @Override
    public Company map(CompanyForm i) {
        
        return new Company(
            null,
            i.name().toUpperCase()
        );
    }
}
