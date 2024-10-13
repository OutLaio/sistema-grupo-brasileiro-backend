package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.StampView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.Stamp;

@Component
public class StampViewMapper implements Mapper<Stamp, StampView> {

    @Override
    public StampView map(Stamp stamp) {
        return new StampView(
                stamp.getId(),
                stamp.getDescription()
        );
    }
}
