package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.form;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.form.GiftForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.BGift;

@Component
public class GiftFormMapper implements Mapper<GiftForm, BGift> {

    @Override
    public BGift map(GiftForm form) {

        return new BGift(
                null,
                null,
                null,
                null,
                null,
                null,
                null, 
                form.giftModel(),
                form.linkModel()
        );
    }

}
