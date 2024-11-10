package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.printed.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.BPrintedsDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.PrintedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.BPrinted;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;

@Component
public class BPrintedsDetailedViewMapper implements Mapper<BPrinted, BPrintedsDetailedView> {
	
	@Autowired
    private BPrintedsViewMapper bPrintedsViewMapper;

    @Autowired
    private ProjectViewMapper projectViewMapper;

    @Autowired
    private BriefingViewMapper briefingViewMapper;

	@Override
	public BPrintedsDetailedView map(BPrinted i) {
		Briefing briefing = i.getBriefing();
        Project project = briefing != null ? briefing.getProject() : null;

        PrintedView printedView = bPrintedsViewMapper.map(i);
        ProjectView projectView = project != null ? projectViewMapper.map(project) : null;
        BriefingView briefingView = briefing != null ? briefingViewMapper.map(briefing) : null;

        return new BPrintedsDetailedView(
        		printedView,
                projectView,
                briefingView
        );
	}

}
