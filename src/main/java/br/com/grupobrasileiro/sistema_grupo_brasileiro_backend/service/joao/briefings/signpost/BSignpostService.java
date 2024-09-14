package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.joao.briefings.signpost;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.signpost.SignpostRegisterView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.signpost.form.BSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.joao.form.BSignpostFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.joao.view.BSignpostRegisterViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.Material;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.singpost.MaterialRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.singpost.SignpostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pela lógica de negócio relacionada aos pontos de sinalização (signposts).
 */
@Service
public class BSignpostService {

    @Autowired
    private SignpostRepository signpostRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private BSignpostFormMapper bSignpostFormMapper;

    @Autowired
    private BSignpostRegisterViewMapper bSignpostRegisterViewMapper;


    public SignpostRegisterView register(BSignpostForm bSignpostForm, Briefing briefing) {
        Material material = materialRepository.getReferenceById(bSignpostForm.idMaterial());
        BSignpost bSignpost = bSignpostFormMapper.map(bSignpostForm);
        bSignpost.setBriefing(briefing);
        bSignpost.setMaterial(material);
        bSignpost = signpostRepository.save(bSignpost);
        return bSignpostRegisterViewMapper.map(bSignpost);
    }
}
