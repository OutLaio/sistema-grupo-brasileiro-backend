package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.companiesBriefing.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.form.CompaniesBriefingsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.companies.CompaniesBriefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompaniesBriefingFormMapper implements Mapper<CompaniesBriefingsForm, CompaniesBriefing> {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public CompaniesBriefing map(CompaniesBriefingsForm i) {

        return new CompaniesBriefing(
                null,
                companyRepository.findById(i.idCompany()).orElseThrow(
                        () -> new IllegalArgumentException("Company not found")
                ),
                null
        );
    }
}
