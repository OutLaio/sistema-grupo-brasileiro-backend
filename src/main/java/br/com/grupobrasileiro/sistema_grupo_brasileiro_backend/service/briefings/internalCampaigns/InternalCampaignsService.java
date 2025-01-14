package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.internalCampaigns;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.form.BInternalCampaignsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.form.BInternalCampaignFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.campaingInternal.BInternalCampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InternalCampaignsService {
	
	@Autowired
	private BInternalCampaignRepository bInternalCampaignRepository;
	
	@Autowired
    private BInternalCampaignFormMapper bInternalCampaignFormMapper;
	
	public void register(BInternalCampaignsForm form, Briefing briefing) {
		BInternalCampaign bInternalCampaign = bInternalCampaignFormMapper.map(form);
		bInternalCampaign.setBriefing(briefing);
		bInternalCampaignRepository.save(bInternalCampaign);
	}

}
