package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.handout.HandoutTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.form.BHandoutForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;

@Component
public class BHandoutFormMapper implements Mapper<BHandoutForm, BHandout> {

    @Autowired
    private HandoutTypeRepository handoutTypeRepository;

    @Override
    public BHandout map(BHandoutForm i) {
        return new BHandout(
            null,
            handoutTypeRepository.findById(i.idHandoutType()).orElseThrow(
                () -> new EntityNotFoundException("Handout Type not found for ID: " + i.idHandoutType())
            ),
            null
        );
    }
}