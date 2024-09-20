package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects;

import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Version;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class VersionRepositoryTest {

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private BriefingRepository briefingRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa a contagem de versões associadas a um briefing.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should count versions by briefing ID")
    void testCountVersionsByBriefingId() {
        // Arrange
        Briefing briefing = new Briefing();
        // Configura o briefing (defina os campos necessários)
        briefing.setDetailedDescription(faker.lorem().sentence());
        // Salva o briefing
        briefingRepository.save(briefing);

        // Cria e salva versões
        Version version1 = new Version();
        version1.setBriefing(briefing);
        version1.setNumVersion(1);
        version1.setProductLink(faker.internet().url());
        version1.setClientApprove(true);
        version1.setSupervisorApprove(false);
        version1.setFeedback("Initial feedback");
        
        Version version2 = new Version();
        version2.setBriefing(briefing);
        version2.setNumVersion(2);
        version2.setProductLink(faker.internet().url());
        version2.setClientApprove(true);
        version2.setSupervisorApprove(true);
        version2.setFeedback("Approved version");
        
        versionRepository.save(version1);
        versionRepository.save(version2);

        // Act
        long count = versionRepository.countVersionsByBriefingId(briefing.getId());

        // Assert
        assertThat(count).isEqualTo(2); // Verifica se a contagem é igual a 2
    }

    /**
     * Testa a contagem de versões para um briefing que não possui versões.
     */
    @Test
    @DisplayName("Should return 0 when no versions are associated with briefing")
    void testCountVersionsByNonExistingBriefingId() {
        // Act
        long count = versionRepository.countVersionsByBriefingId(999L); // ID que não existe

        // Assert
        assertThat(count).isEqualTo(0); // Verifica se a contagem é igual a 0
    }
}
