package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostView;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BSignpostViewMapper implements Mapper<BSignpost, BSignpostView> {

    @Autowired
    private MaterialViewMapper materialViewMapper;

    @Override
    public BSignpostView map(BSignpost bSignpost) {
        return new BSignpostView(
                bSignpost.getId(),
                materialViewMapper.map(bSignpost.getMaterial()),
                bSignpost.getBoardLocation(),
                bSignpost.getSector()
        );
    }
}
