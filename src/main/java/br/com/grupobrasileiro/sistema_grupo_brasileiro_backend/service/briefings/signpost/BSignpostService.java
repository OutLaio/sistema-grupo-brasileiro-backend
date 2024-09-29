package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.signpost;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form.BSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.form.BSignpostFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view.BSignpostDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.Material;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.singpost.MaterialRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.singpost.SignpostRepository;
import lombok.RequiredArgsConstructor;
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
    private BSignpostDetailedViewMapper bSignpostRegisterViewMapper;

    public BSignpostDetailedView register(BSignpostForm bSignpostForm, Briefing briefing) {
        Material material = materialRepository.getReferenceById(bSignpostForm.idMaterial());
        BSignpost bSignpost = bSignpostFormMapper.map(bSignpostForm);
        bSignpost.setBriefing(briefing);
        bSignpost.setMaterial(material);
        bSignpost = signpostRepository.save(bSignpost);
        return bSignpostRegisterViewMapper.map(bSignpost);
    }
}
