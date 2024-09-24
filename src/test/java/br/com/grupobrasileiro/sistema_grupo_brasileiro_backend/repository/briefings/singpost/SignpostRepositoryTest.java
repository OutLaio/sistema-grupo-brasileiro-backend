package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.singpost;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.Material;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;

@DataJpaTest
public class SignpostRepositoryTest {

    @Autowired
    private SignpostRepository signpostRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private BriefingRepository briefingRepository;

    private Material material;
    private Briefing briefing;
    private BSignpost signpost;

    @BeforeEach
    void setUp() {
        // Criar e salvar um Material fictício
        material = new Material();
        material.setDescription("Material de Teste");
        material = materialRepository.save(material); // Persistir o Material

        // Criar e salvar um Briefing fictício
        briefing = new Briefing();
        briefing.setDetailedDescription("Descrição detalhada de teste");
        briefing.setStartTime(LocalDateTime.now());
        briefing.setExpectedTime(LocalDateTime.now().plusDays(7));
        briefing = briefingRepository.save(briefing); // Persistir o Briefing

        // Criar e configurar um BSignpost fictício
        signpost = new BSignpost();
        signpost.setMaterial(material);
        signpost.setBriefing(briefing);
        signpost.setBoardLocation("Locação de Exemplo");
        signpost.setSector("Setor de Exemplo");
    }

    @Test
    @Rollback(false)
    void testSaveAndFindSignpost() {
        // Act
        BSignpost savedSignpost = signpostRepository.save(signpost);

        // Assert
        Optional<BSignpost> foundSignpost = signpostRepository.findById(savedSignpost.getId());
        assertThat(foundSignpost).isPresent();
        assertThat(foundSignpost.get().getBoardLocation()).isEqualTo("Locação de Exemplo");
        assertThat(foundSignpost.get().getSector()).isEqualTo("Setor de Exemplo");
    }

    @Test
    @Rollback(false)
    void testDeleteSignpost() {
        // Act
        BSignpost savedSignpost = signpostRepository.save(signpost);
        signpostRepository.delete(savedSignpost);

        // Assert
        Optional<BSignpost> foundSignpost = signpostRepository.findById(savedSignpost.getId());
        assertThat(foundSignpost).isNotPresent();
    }
}
