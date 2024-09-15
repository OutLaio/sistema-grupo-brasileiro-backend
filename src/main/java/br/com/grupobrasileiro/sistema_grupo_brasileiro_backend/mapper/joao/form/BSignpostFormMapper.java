package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.joao.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.joao.signpost.form.BSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.Material;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.singpost.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BSignpostFormMapper implements Mapper<BSignpostForm, BSignpost> {

    @Autowired
    private MaterialRepository materialRepository;


    @Override
    public BSignpost map(BSignpostForm signpostForm) {
        return new BSignpost(
                null,
                null,
                null,
                signpostForm.boardLocation(),
                signpostForm.sector()
        );
    }


}
