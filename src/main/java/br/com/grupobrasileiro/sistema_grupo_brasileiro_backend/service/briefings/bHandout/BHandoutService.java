package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.bHandout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.form.BHandoutForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.BHandoutDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.form.BHandoutFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.view.BHandoutDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.HandoutType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.handout.BHandoutRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.handout.HandoutTypeRepository;


@Service
public class BHandoutService {

    @Autowired
    private BHandoutRepository bHandoutRepository;

    @Autowired
    private HandoutTypeRepository handoutTypeRepository;

    @Autowired
    private BHandoutFormMapper bHandoutFormMapper;

    @Autowired
    private BHandoutDetailedViewMapper bHandoutDetailedViewMapper;

    public BHandoutDetailedView register(BHandoutForm bHandoutForm, Briefing briefing) {
        HandoutType handoutType = handoutTypeRepository.getReferenceById(bHandoutForm.idHandoutType());

        BHandout bHandout = bHandoutFormMapper.map(bHandoutForm);

        bHandout.setHandoutType(handoutType);
        bHandout.setBriefing(briefing);

        bHandout = bHandoutRepository.save(bHandout);

        return bHandoutDetailedViewMapper.map(bHandout);
    }
}

