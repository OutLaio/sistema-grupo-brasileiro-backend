package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BriefingViewMapper implements Mapper<Briefing, BriefingView> {

    @Autowired
    private BriefingTypeViewMapper briefingTypeMapperView;

    @Override
    public BriefingView map(Briefing briefing) {
        return new BriefingView(
                briefing.getId(),
                briefingTypeMapperView.map(briefing.getBriefingType()),
                briefing.getStartTime(),
                briefing.getExpectedTime(),
                briefing.getFinishTime(),
                briefing.getDetailedDescription()
        );
    }
}
