package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.bHandout;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.form.BHandoutForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.form.BHandoutFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.handout.BHandoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BHandoutService {

    @Autowired
    private BHandoutRepository bHandoutRepository;

    @Autowired
    private BHandoutFormMapper bHandoutFormMapper;

    public void register(BHandoutForm bHandoutForm, Briefing briefing) {
        BHandout bHandout = bHandoutFormMapper.map(bHandoutForm);
        bHandout.setBriefing(briefing);
        bHandoutRepository.save(bHandout);
    }
}

