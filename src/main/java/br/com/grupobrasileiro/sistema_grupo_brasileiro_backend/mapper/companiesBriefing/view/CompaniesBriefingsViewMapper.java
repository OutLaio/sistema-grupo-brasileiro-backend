package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.companiesBriefing.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.view.CompaniesBriefingsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.CompanyViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.companies.CompaniesBriefing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CompaniesBriefingsViewMapper implements Mapper<Set<CompaniesBriefing>, CompaniesBriefingsView> {

    @Autowired
    private CompanyViewMapper companyViewMapper;

    @Override
    public CompaniesBriefingsView map(Set<CompaniesBriefing> i) {
        return new CompaniesBriefingsView(
                i.stream().map(
                        companiesBriefing -> companyViewMapper.map(companiesBriefing.getCompany())
                ).collect(Collectors.toSet())
        );
    }
}
