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

import java.util.List;
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
        Briefing briefing = createTestBriefing();
        saveTestVersion(briefing, 1, "Initial feedback", true, false);
        saveTestVersion(briefing, 2, "Approved version", true, true);

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

    /**
     * Testa a contagem de versões após a exclusão de uma versão.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should update count after deleting a version")
    void testCountAfterDeletingVersion() {
        // Arrange
        Briefing briefing = createTestBriefing();
        Version version1 = saveTestVersion(briefing, 1, "Feedback 1", true, false);
        saveTestVersion(briefing, 2, "Feedback 2", true, true);

        // Act - Excluir uma versão
        versionRepository.delete(version1);

        // Assert - Verifica a contagem de versões
        long count = versionRepository.countVersionsByBriefingId(briefing.getId());
        assertThat(count).isEqualTo(1); // Verifica se a contagem é igual a 1
    }

    /**
     * Testa a atualização de uma versão existente.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should update an existing version")
    void testUpdateVersion() {
        // Arrange
        Briefing briefing = createTestBriefing();
        Version version = saveTestVersion(briefing, 1, "Initial feedback", true, false);

        // Act - Atualiza a versão
        version.setFeedback("Updated feedback");
        Version updatedVersion = versionRepository.save(version);

        // Assert - Verifica se a atualização foi feita
        assertThat(updatedVersion.getFeedback()).isEqualTo("Updated feedback");
    }

   

    /**
     * Testa a criação e recuperação de uma versão.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should create and retrieve a version")
    void testCreateAndRetrieveVersion() {
        // Arrange
        Briefing briefing = createTestBriefing();
        Version version = new Version();
        version.setBriefing(briefing);
        version.setNumVersion(1);
        version.setProductLink(faker.internet().url());
        version.setClientApprove(true);
        version.setSupervisorApprove(false);
        version.setFeedback("Test feedback");

        // Act
        versionRepository.save(version);
        Optional<Version> retrievedVersion = versionRepository.findById(version.getId());

        // Assert
        assertThat(retrievedVersion).isPresent();
        assertThat(retrievedVersion.get().getFeedback()).isEqualTo(version.getFeedback());
    }

    private Briefing createTestBriefing() {
        Briefing briefing = new Briefing();
        briefing.setDetailedDescription(faker.lorem().sentence());
        return briefingRepository.save(briefing);
    }

    private Version saveTestVersion(Briefing briefing, int versionNumber, String feedback, boolean clientApprove, boolean supervisorApprove) {
        Version version = new Version();
        version.setBriefing(briefing);
        version.setNumVersion(versionNumber);
        version.setProductLink(faker.internet().url());
        version.setClientApprove(clientApprove);
        version.setSupervisorApprove(supervisorApprove);
        version.setFeedback(feedback);
        return versionRepository.save(version);
    }
}
