package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.signpost;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form.BSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.form.BSignpostFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.singpost.SignpostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pela lógica de negócio relacionada às placas de sinalização (signposts).
 */
@Service
public class BSignpostService {

    @Autowired
    private SignpostRepository signpostRepository;

    @Autowired
    private BSignpostFormMapper bSignpostFormMapper;

    public void register(BSignpostForm bSignpostForm, Briefing briefing) {
        BSignpost bSignpost = bSignpostFormMapper.map(bSignpostForm);
        bSignpost.setBriefing(briefing);
        signpostRepository.save(bSignpost);
    }
}
