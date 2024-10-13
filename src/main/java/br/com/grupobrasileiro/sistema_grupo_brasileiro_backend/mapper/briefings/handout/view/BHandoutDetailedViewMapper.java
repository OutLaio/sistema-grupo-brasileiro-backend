package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.BHandoutDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.BHandoutView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;

@Component
public class BHandoutDetailedViewMapper implements Mapper<BHandout, BHandoutDetailedView> {

    @Autowired
    private BHandoutViewMapper bHandoutViewMapper;

    @Autowired
    private BriefingViewMapper briefingViewMapper;

    @Autowired
    private ProjectViewMapper projectViewMapper;

    @Override
    public BHandoutDetailedView map(BHandout bHandout) {

        Briefing briefing = bHandout.getBriefing();

        Project project = briefing != null ? briefing.getProject() : null;

        BHandoutView bHandoutView = bHandoutViewMapper.map(bHandout);
        ProjectView projectView = project != null ? projectViewMapper.map(project) : null;
        BriefingView briefingView = briefing != null ? briefingViewMapper.map(briefing) : null;

        return new BHandoutDetailedView(
                bHandoutView,
                projectView,
                briefingView
        );
    }
}