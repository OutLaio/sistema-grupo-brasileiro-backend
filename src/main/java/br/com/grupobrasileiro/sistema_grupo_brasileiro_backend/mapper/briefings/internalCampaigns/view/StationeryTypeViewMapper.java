package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.view;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.StationeryTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.StationeryType;

@Component
public class StationeryTypeViewMapper implements  Mapper<StationeryType, StationeryTypeView> {

	@Override
	public StationeryTypeView map(StationeryType i) {
		return new StationeryTypeView(i.getId(), i.getDescription());
	}

}
