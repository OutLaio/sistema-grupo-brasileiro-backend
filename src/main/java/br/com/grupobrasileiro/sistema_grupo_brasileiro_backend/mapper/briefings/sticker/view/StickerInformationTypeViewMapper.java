package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.sticker.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view.StickerInformationTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.StickerInformationType;
import org.springframework.stereotype.Component;

@Component
public class StickerInformationTypeViewMapper implements Mapper<StickerInformationType, StickerInformationTypeView> {
    @Override
    public StickerInformationTypeView map(StickerInformationType i) {
        return new StickerInformationTypeView(
                i.getId(),
                i.getDescription()
        );
    }
}
