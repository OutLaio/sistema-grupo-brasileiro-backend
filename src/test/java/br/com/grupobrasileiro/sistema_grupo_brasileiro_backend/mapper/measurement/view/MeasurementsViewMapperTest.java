package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.measurement.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.view.MeasurementsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.measurements.Measurement;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;

public class MeasurementsViewMapperTest {

    private final MeasurementsViewMapper mapper = new MeasurementsViewMapper();

    @Test
    public void testMap() {
        // Arrange: cria uma instância de Measurement com valores de teste
        Measurement measurement = new Measurement();
        measurement.setId(1L);
        measurement.setBriefing(new Briefing()); 
        measurement.setHeight(new BigDecimal("180.5"));
        measurement.setLength(new BigDecimal("75.3"));

        // Act: chama o método de mapeamento
        MeasurementsView measurementsView = mapper.map(measurement);

        // Assert: verifica se os valores foram mapeados corretamente
        assertEquals(measurement.getHeight(), measurementsView.height());
        assertEquals(measurement.getLength(), measurementsView.length());
    }
}
