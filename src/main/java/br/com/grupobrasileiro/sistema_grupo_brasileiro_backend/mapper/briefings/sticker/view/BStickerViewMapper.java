package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.sticker.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view.BStickerView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.BSticker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BStickerViewMapper implements Mapper<BSticker, BStickerView> {
    @Autowired
    private StickerTypeViewMapper stickerTypeViewMapper;

    @Autowired
    private StickerInformationTypeViewMapper stickerInformationTypeViewMapper;

    @Override
    public BStickerView map(BSticker i) {
        return new BStickerView(
                i.getId(),
                stickerTypeViewMapper.map(i.getStickerType()),
                i.getStickerInformationType() == null ? null :
                    stickerInformationTypeViewMapper.map(i.getStickerInformationType()
                ),
                i.getSector(),
                i.getObservations()
        );
    }
}
