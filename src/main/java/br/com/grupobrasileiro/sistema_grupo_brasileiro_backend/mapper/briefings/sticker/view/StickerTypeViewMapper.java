package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.sticker.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view.StickerTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.StickerType;
import org.springframework.stereotype.Component;

@Component
public class StickerTypeViewMapper implements Mapper<StickerType, StickerTypeView> {
    @Override
    public StickerTypeView map(StickerType i) {
        return new StickerTypeView(
                i.getId(),
                i.getDescription()
        );
    }
}
