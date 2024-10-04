package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.internalCampaigns;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.form.BInternalCampaignsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.BInternalCampaignsDetailsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.form.BInternalCampaignFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.view.BInternalCampaignDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.OtherItem;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.StationeryType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.campaingInternal.BInternalCampaignRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.campaingInternal.OtherItemRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.campaingInternal.StationeryTypeRepository;

@Service
public class InternalCampaignsService {
	
	@Autowired
    private StationeryTypeRepository stationeryTypeRepository;
	
	@Autowired
    private OtherItemRepository otherItemRepository;
	
	@Autowired
	private BInternalCampaignRepository bInternalCampaignRepository;
	
	@Autowired
    private BInternalCampaignDetailedViewMapper bInternalCampaignDetailedViewMapper;
	
	public BInternalCampaignsDetailsView register(BInternalCampaignsForm form, Briefing briefing) {
		BInternalCampaign bInternalCampaign = new BInternalCampaignFormMapper().map(form);
		
		StationeryType stationeryType = stationeryTypeRepository.getReferenceById(form.idStationeryType());
		OtherItem otherItem = otherItemRepository.getReferenceById(form.idOtherItem());
		
		bInternalCampaign.setBriefing(briefing);
		bInternalCampaign.setStationeryType(stationeryType);
		bInternalCampaign.setOtherItem(otherItem);
		bInternalCampaign.setCampaignMotto(form.campaignMotto());
		
		bInternalCampaign = bInternalCampaignRepository.save(bInternalCampaign);
		return bInternalCampaignDetailedViewMapper.map(bInternalCampaign);
	}

}
