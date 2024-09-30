package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.measurement.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.view.MeasurementsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.measurements.Measurement;
import org.springframework.stereotype.Component;

@Component
public class MeasurementsViewMapper implements Mapper<Measurement, MeasurementsView> {
    @Override
    public MeasurementsView map(Measurement i) {
        return new MeasurementsView(
                i.getHeight(),
                i.getLength()
        );
    }
}
