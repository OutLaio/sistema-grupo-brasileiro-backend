package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.sticker.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.form.BStickerForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.BSticker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.sticker.StickerInformationTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.sticker.StickerTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BStickerFormMapper implements Mapper<BStickerForm, BSticker> {
    @Autowired
    private StickerTypeRepository stickerTypeRepository;

    @Autowired
    private StickerInformationTypeRepository stickerInformationTypeRepository;

    @Override
    public BSticker map(BStickerForm i) {
        return new BSticker(
            null,
            null,
            stickerTypeRepository.findById(i.idStickerType()).orElseThrow(
                () -> new EntityNotFoundException("Sticker type not found with id: " + i.idStickerType())
            ),
            i.idStickerInformationType() == null ? null :
                stickerInformationTypeRepository.findById(i.idStickerInformationType()).orElseThrow(
                    () -> new EntityNotFoundException("Sticker information type not found with id: " + i.idStickerInformationType())
            ),
            i.sector(),
            i.observations()
        );
    }
}
