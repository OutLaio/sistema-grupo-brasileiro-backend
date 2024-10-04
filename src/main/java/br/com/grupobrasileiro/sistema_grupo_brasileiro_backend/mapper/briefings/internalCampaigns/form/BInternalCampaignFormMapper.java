package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.form.BInternalCampaignsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.OtherItem;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.StationeryType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.campaingInternal.OtherItemRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.campaingInternal.StationeryTypeRepository;

@Component
public class BInternalCampaignFormMapper implements Mapper<BInternalCampaignsForm, BInternalCampaign> {
	
	@Autowired
    private StationeryTypeRepository stationeryTypeRepository;
	
	@Autowired
    private OtherItemRepository otherItemRepository;

	@Override
	public BInternalCampaign map(BInternalCampaignsForm i) {
		if (i == null) {
            return null;
        }
		
		StationeryType stationeryType = stationeryTypeRepository.getReferenceById(i.idStationeryType());
		OtherItem otherItem = otherItemRepository.getReferenceById(i.idOtherItem());
        
        return new BInternalCampaign(
                null,
                stationeryType,
                otherItem,
                null,
                i.campaignMotto()
        );
	}
}

