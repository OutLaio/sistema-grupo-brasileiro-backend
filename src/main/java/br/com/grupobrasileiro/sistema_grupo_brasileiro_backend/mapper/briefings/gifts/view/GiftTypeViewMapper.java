package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.GiftTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.GiftType;

@Component
public class GiftTypeViewMapper implements Mapper<GiftType, GiftTypeView> {

    @Override
    public GiftTypeView map(GiftType giftType) {
        return new GiftTypeView(
                giftType.getId(),
                giftType.getDescription()
        );
    }
}
