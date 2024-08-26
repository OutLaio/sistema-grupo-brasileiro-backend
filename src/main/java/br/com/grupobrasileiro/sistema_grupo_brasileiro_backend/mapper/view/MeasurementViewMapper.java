package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.MeasurementView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Measurement;

@Component
public class MeasurementViewMapper implements Mapper<Measurement, MeasurementView>{
	
    @Override
    public MeasurementView map(Measurement i) {
    	return new MeasurementView(
    		i.getHeight(),
    		i.getLength()

    	);			
    }
}
