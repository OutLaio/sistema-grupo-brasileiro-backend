package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.form;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.form.BHandoutForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;

@Component
public class BHandoutFormMapper implements Mapper<BHandoutForm, BHandout> {

    @Override
    public BHandout map(BHandoutForm bHandoutForm) {
        return new BHandout(
            null,
            null,
            null
        );
    }
}