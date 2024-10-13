package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.BHandoutView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;

@Component
public class BHandoutViewMapper implements Mapper<BHandout, BHandoutView> {

    @Autowired
    private HandoutTypeViewMapper handoutTypeViewMapper;

    @Override
    public BHandoutView map(BHandout bHandout) {
        return new BHandoutView(
                bHandout.getId(),
                handoutTypeViewMapper.map(bHandout.getHandoutType())
        );
    }
}
