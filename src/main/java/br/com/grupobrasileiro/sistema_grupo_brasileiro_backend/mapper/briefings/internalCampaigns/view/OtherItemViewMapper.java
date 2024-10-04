package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.view;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.OtherItemView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.OtherItem;

@Component
public class OtherItemViewMapper implements Mapper<OtherItem, OtherItemView> {

	@Override
	public OtherItemView map(OtherItem i) {
		return new OtherItemView(i.getId(), i.getDescription());
	}

}
