package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;

@Component
public class BriefingTypeViewMapper implements Mapper<BriefingType, BriefingTypeView> {

    @Override
    public BriefingTypeView map(BriefingType briefingType) {
        return new BriefingTypeView(
                briefingType.getId(),
                briefingType.getDescription()
        );
    }
}
