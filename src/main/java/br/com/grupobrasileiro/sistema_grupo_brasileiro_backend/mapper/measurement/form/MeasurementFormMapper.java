package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.measurement.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.form.MeasurementsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.measurements.Measurement;
import org.springframework.stereotype.Component;

@Component
public class MeasurementFormMapper implements Mapper<MeasurementsForm, Measurement> {
    @Override
    public Measurement map(MeasurementsForm i) {
        return new Measurement(
                null,
                null,
                i.height(),
                i.length()
        );
    }
}
