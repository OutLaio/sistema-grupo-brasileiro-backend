package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.joao.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Component
public class BriefingFormMapper implements Mapper<BriefingForm, Briefing> {

    @Autowired
    private BriefingTypeRepository briefingTypeRepository;

    @Override
    public Briefing map(BriefingForm briefingForm) {
        return new Briefing(briefingForm);
    }


}
