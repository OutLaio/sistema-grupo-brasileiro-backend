package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.BGiftDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.BGiftView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.BGift;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;

@Component
public class BGiftDetailedViewMapper implements Mapper<BGift, BGiftDetailedView> {

    @Autowired
    private BGiftViewMapper bGiftViewMapper;

    @Autowired
    private BriefingViewMapper briefingViewMapper;

    @Autowired
    private ProjectViewMapper projectViewMapper;

    @Override
    public BGiftDetailedView map(BGift bGift) {

        Briefing briefing = bGift.getBriefing();

        Project project = briefing != null ? briefing.getProject() : null;

 
        BGiftView bGiftView = bGiftViewMapper.map(bGift);
        ProjectView projectView = project != null ? projectViewMapper.map(project) : null;
        BriefingView briefingView = briefing != null ? briefingViewMapper.map(briefing) : null;

        return new BGiftDetailedView(
                bGiftView,
                projectView,
                briefingView
        );
    }
}