package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class BriefingFormMapper implements Mapper<BriefingForm, Briefing> {

    @Autowired
    private BriefingTypeRepository briefingTypeRepository;

    @Override
    public Briefing map(BriefingForm briefingForm) {


        return new Briefing(
                null,
                null,
                null,
                LocalDate.now(),
                briefingForm.expectedDate(),
                null,
                briefingForm.detailedDescription(),
                briefingForm.otherCompany(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
                ,null
                ,null,
                null
        );
    }
}
