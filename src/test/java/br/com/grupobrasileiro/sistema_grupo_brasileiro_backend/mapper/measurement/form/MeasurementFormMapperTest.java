package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.measurement.form;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.form.MeasurementsForm;

/**
 * Testes para a classe MeasurementFormMapper.
 * Verifica o mapeamento de MeasurementsForm para Measurement.
 */

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.measurements.Measurement;

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

    /**
     * Testa o mapeamento de MeasurementsForm para Measurement sem briefing.
     * Espera-se que os valores de altura e comprimento sejam mapeados corretamente.
     */
    @Test
    @DisplayName("Should map MeasurementsForm to Measurement correctly without briefing")
    void mapMeasurementsFormToMeasurement() {
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

    /**
     * Testa o mapeamento de MeasurementsForm com valores nulos.
     * Espera-se que uma IllegalArgumentException seja lançada ao mapear valores nulos.
     */
    @Test
    @DisplayName("Should map MeasurementsForm with null values correctly")
    void mapWithNullValues() {
        // Define MeasurementsForm com altura e comprimento nulos
        measurementsForm = new MeasurementsForm(null, null);

        // Mapeia MeasurementsForm e espera que não seja nulo
        Measurement result = measurementFormMapper.map(measurementsForm);

        // Verifica que o resultado não é nulo
        assertNotNull(result);

        // Verifica que altura e comprimento são nulos
        assertNull(result.getHeight());
        assertNull(result.getLength());
    }



    /**
     * Testa o mapeamento de MeasurementsForm com altura e comprimento iguais a zero.
     * Espera-se que os valores de altura e comprimento sejam mapeados como zero.
     */
    @Test
    @DisplayName("Should map MeasurementsForm with zero height and length")
    void mapWithZeroValues() {
        // Define MeasurementsForm com altura e comprimento iguais a zero
        measurementsForm = new MeasurementsForm(BigDecimal.ZERO, BigDecimal.ZERO);

        // Mapeia MeasurementsForm para Measurement
        Measurement result = measurementFormMapper.map(measurementsForm);

        assertNotNull(result);
        // Verifica se os valores de height e length são iguais a zero
        assertEquals(BigDecimal.ZERO, result.getHeight());
        assertEquals(BigDecimal.ZERO, result.getLength());
        assertNull(result.getId());
        assertNull(result.getBriefing());
    }
}

