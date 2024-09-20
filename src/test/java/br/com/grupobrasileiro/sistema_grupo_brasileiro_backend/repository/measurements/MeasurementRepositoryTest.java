package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.measurements;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.meansurements.Measurement;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;

@DataJpaTest
public class MeasurementRepositoryTest {

    @Autowired
    private MeasurementRepository measurementRepository;

    private Measurement measurement;

    @BeforeEach
    void setUp() {
        Briefing briefing = new Briefing(); // Crie uma instância de Briefing conforme necessário
        // Configure o briefing se necessário, e salve-o no repositório se necessário

        measurement = new Measurement();
        measurement.setBriefing(briefing); // Associar um briefing
        measurement.setHeight(BigDecimal.valueOf(1.75)); // Exemplo de altura
        measurement.setLength(BigDecimal.valueOf(2.0)); // Exemplo de comprimento
    }

    /**
     * Testa a persistência e recuperação de uma Measurement.
     */
    @Test
    @Rollback(false) 
    void testSaveAndFindMeasurement() {
        // Act
        Measurement savedMeasurement = measurementRepository.save(measurement);
        
        // Assert
        Optional<Measurement> foundMeasurement = measurementRepository.findById(savedMeasurement.getId());
        assertThat(foundMeasurement).isPresent();
        assertThat(foundMeasurement.get().getHeight()).isEqualTo(BigDecimal.valueOf(1.75));
        assertThat(foundMeasurement.get().getLength()).isEqualTo(BigDecimal.valueOf(2.0));
    }

    /**
     * Testa a exclusão de uma Measurement.
     */
    @Test
    @Rollback(false)
    void testDeleteMeasurement() {
        // Act
        Measurement savedMeasurement = measurementRepository.save(measurement);
        measurementRepository.delete(savedMeasurement);

        // Assert
        Optional<Measurement> foundMeasurement = measurementRepository.findById(savedMeasurement.getId());
        assertThat(foundMeasurement).isNotPresent();
    }
}
