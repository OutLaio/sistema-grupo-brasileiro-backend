package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.BInternalCampaignsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.OtherItemView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.StationeryTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.OtherItem;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.StationeryType;

@Component
public class BInternalCampaignViewMapper implements Mapper<BInternalCampaign, BInternalCampaignsView> {
	
	@Autowired
	OtherItemViewMapper otherItemViewMapper;
	
	@Autowired
	StationeryTypeViewMapper stationeryTypeViewMapper;

	@Override
	public BInternalCampaignsView map(BInternalCampaign i) {
		OtherItem otherItem = i.getOtherItem();
		StationeryType stationeryType = i.getStationeryType();
		
		OtherItemView otherItemView = otherItemViewMapper.map(otherItem);
		StationeryTypeView stationeryTypeView = stationeryTypeViewMapper.map(stationeryType);
		
		return new BInternalCampaignsView(
				i.getId(),
				stationeryTypeView,
				otherItemView,
				i.getCampaignMotto()
		);
	}

}
