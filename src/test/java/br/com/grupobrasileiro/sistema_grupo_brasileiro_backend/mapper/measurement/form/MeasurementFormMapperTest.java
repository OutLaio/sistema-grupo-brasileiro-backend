package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.measurement.form;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.form.MeasurementsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.measurements.Measurement;

import java.math.BigDecimal;

class MeasurementFormMapperTest {

    @InjectMocks
    private MeasurementFormMapper measurementFormMapper;

    private Faker faker;
    private MeasurementsForm measurementsForm;

    @BeforeEach
    void setUp() {
        // Inicializa o Mockito e o Faker
        MockitoAnnotations.openMocks(this);
        faker = new Faker();

        // Gera dados aleatórios para MeasurementsForm
        measurementsForm = new MeasurementsForm(
                BigDecimal.valueOf(faker.number().randomDouble(2, 1, 200)), // Altura
                BigDecimal.valueOf(faker.number().randomDouble(2, 1, 500))  // Comprimento
        );
    }

    @Test
    @DisplayName("Should map MeasurementsForm to Measurement correctly without briefing")
    void shouldMapMeasurementsFormToMeasurement() {
        // Mapeia MeasurementsForm para Measurement
        Measurement result = measurementFormMapper.map(measurementsForm);

        // Verifica se o resultado não é nulo
        assertNotNull(result);

        // Verifica que os valores de height e length foram corretamente mapeados
        assertEquals(measurementsForm.height(), result.getHeight());
        assertEquals(measurementsForm.length(), result.getLength());

        // Verifica que briefing e id são nulos
        assertNull(result.getId());
        assertNull(result.getBriefing());
    }
}

