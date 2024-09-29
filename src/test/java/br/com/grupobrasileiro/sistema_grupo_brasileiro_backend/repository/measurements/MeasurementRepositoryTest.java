package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.measurements;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.measurements.Measurement;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;

@DataJpaTest
@Transactional
public class MeasurementRepositoryTest {

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private BriefingRepository briefingRepository;

    private Measurement measurement;
    private Briefing briefing;

    @BeforeEach
    void setUp() {
        // Configurando o Briefing com Project e BriefingType
        Project project = new Project(); 
        BriefingType briefingType = new BriefingType(); 

        briefing = new Briefing();
        briefing.setProject(project);
        briefing.setBriefingType(briefingType);
        briefing.setStartTime(LocalDateTime.now());
        briefing.setExpectedTime(LocalDateTime.now().plusDays(7));
        briefing.setDetailedDescription("Descrição do Briefing para Teste");

        briefingRepository.save(briefing);

        // Criando a Measurement associada ao briefing
        measurement = new Measurement();
        measurement.setBriefing(briefing);
        measurement.setHeight(BigDecimal.valueOf(1.75));
        measurement.setLength(BigDecimal.valueOf(2.50));
    }

    @Test
    @DisplayName("Deve salvar e carregar Measurement com Briefing associado")
    void shouldSaveAndLoadMeasurementWithBriefing() {
        // Salvando a Measurement
        Measurement savedMeasurement = measurementRepository.save(measurement);
        assertThat(savedMeasurement.getId()).isNotNull();

        // Carregando a Measurement do banco
        Optional<Measurement> foundMeasurement = measurementRepository.findById(savedMeasurement.getId());
        assertThat(foundMeasurement).isPresent();

        // Verificando se o Briefing está corretamente associado
        Briefing foundBriefing = foundMeasurement.get().getBriefing();
        assertThat(foundBriefing).isNotNull();
        assertThat(foundBriefing.getId()).isEqualTo(briefing.getId());
    }

    @Test
    @DisplayName("Deve verificar a exclusão em cascata de Measurement com Briefing")
    void shouldDeleteMeasurementAndCascadeBriefing() {
        // Salvando a Measurement e o Briefing
        Measurement savedMeasurement = measurementRepository.save(measurement);
        assertThat(savedMeasurement.getId()).isNotNull();

        // Excluindo a Measurement
        measurementRepository.delete(savedMeasurement);

        // Verificando se a Measurement foi excluída
        Optional<Measurement> foundMeasurement = measurementRepository.findById(savedMeasurement.getId());
        assertThat(foundMeasurement).isNotPresent();
    }
}
