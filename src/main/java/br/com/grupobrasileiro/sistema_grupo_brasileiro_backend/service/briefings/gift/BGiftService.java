package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.gift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.form.GiftForm;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.form.GiftFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.BGift;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.BGiftRepository;


@Service
public class BGiftService {

    @Autowired
    private BGiftRepository giftRepository;

    @Autowired
    private GiftFormMapper giftFormMapper;

    public void register(GiftForm GiftForm, Briefing briefing) {
        BGift bGift = giftFormMapper.map(GiftForm);
        bGift.setBriefing(briefing);
        giftRepository.save(bGift);
    }
}

