package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.sticker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.form.BStickerForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.sticker.form.BStickerFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.BSticker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.sticker.BStickerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pela lógica de negócio relacionada aos adesivos (Stickers).
 */
@Service
public class BStickerService {
    @Autowired
    private BStickerRepository bStickerRepository;

    @Autowired
    private BStickerFormMapper bStickerFormMapper;

    public void register(BStickerForm bStickerForm, Briefing briefing) {
        BSticker bSticker = bStickerFormMapper.map(bStickerForm);
        bSticker.setBriefing(briefing);
        bStickerRepository.save(bSticker);
    }
}
