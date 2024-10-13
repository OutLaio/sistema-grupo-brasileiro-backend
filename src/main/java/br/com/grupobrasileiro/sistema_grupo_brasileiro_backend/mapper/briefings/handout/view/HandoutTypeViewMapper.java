package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.view;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.HandoutTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.HandoutType;

@Component
public class HandoutTypeViewMapper implements Mapper<HandoutType, HandoutTypeView> {

    @Override
    public HandoutTypeView map(HandoutType handoutType) {
        return new HandoutTypeView(
                handoutType.getId(),
                handoutType.getDescription()
        );
    }
}
