package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.BInternalCampaignsDetailsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.BInternalCampaignsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;

@Component
public class BInternalCampaignDetailedViewMapper implements Mapper<BInternalCampaign, BInternalCampaignsDetailsView> {
	
	@Autowired
    private BInternalCampaignViewMapper bInternalCampaignViewMapper;

    @Autowired
    private ProjectViewMapper projectViewMapper;

    @Autowired
    private BriefingViewMapper briefingViewMapper;

	@Override
	public BInternalCampaignsDetailsView map(BInternalCampaign i) {
		Briefing briefing = i.getBriefing();
        Project project = briefing != null ? briefing.getProject() : null;

        BInternalCampaignsView bInternalCampaignsView = bInternalCampaignViewMapper.map(i);
        ProjectView projectView = project != null ? projectViewMapper.map(project) : null;
        BriefingView briefingView = briefing != null ? briefingViewMapper.map(briefing) : null;

        return new BInternalCampaignsDetailsView(
        		bInternalCampaignsView,
                projectView,
                briefingView
        );
	}

}
