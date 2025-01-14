package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BSignpostDetailedViewMapper implements Mapper<BSignpost, BSignpostDetailedView> {

    @Autowired
    private BSignpostViewMapper bSignpostViewMapper;

    @Autowired
    private ProjectViewMapper projectViewMapper;

    @Autowired
    private BriefingViewMapper briefingViewMapper;

    @Override
    public BSignpostDetailedView map(BSignpost bSignpost) {
        Briefing briefing = bSignpost.getBriefing();
        Project project = briefing != null ? briefing.getProject() : null;

        BSignpostView bSignpostView = bSignpostViewMapper.map(bSignpost);
        ProjectView projectView = project != null ? projectViewMapper.map(project) : null;
        BriefingView briefingView = briefing != null ? briefingViewMapper.map(briefing) : null;

        return new BSignpostDetailedView(
                bSignpostView,
                projectView,
                briefingView
        );
    }
}
