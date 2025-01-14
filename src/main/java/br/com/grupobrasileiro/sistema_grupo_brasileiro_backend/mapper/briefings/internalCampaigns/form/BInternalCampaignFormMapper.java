package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
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
            throw new NullPointerException("BInternalCampaignsForm at Mapper is null");
        }
        return new BInternalCampaign(
			null,
			stationeryTypeRepository.findById(i.idStationeryType()).orElseThrow(
				() -> new EntityNotFoundException("Stationery Type not found with id: " + i.idStationeryType())
			),
			otherItemRepository.findById(i.idOtherItem()).orElseThrow(
				() -> new EntityNotFoundException("Other Item not found with id: " + i.idOtherItem())
			),
			null,
			i.campaignMotto()

        );
	}
}

