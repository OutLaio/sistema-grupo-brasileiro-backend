package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project; // Certifique-se de que esta classe está presente
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
public class BriefingRepositoryTest {

    @Autowired
    private BriefingRepository briefingRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa a criação e recuperação de um briefing.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should create and retrieve a briefing")
    void testCreateAndRetrieveBriefing() {
        // Arrange
        Project project = new Project(); // Crie e configure um projeto adequado
        BriefingType briefingType = new BriefingType(); // Crie e configure um tipo de briefing adequado

        Briefing briefing = new Briefing();
        briefing.setProject(project);
        briefing.setBriefingType(briefingType);
        briefing.setStartTime(LocalDateTime.now());
        briefing.setExpectedTime(LocalDateTime.now().plusDays(5));
        briefing.setDetailedDescription(faker.lorem().paragraph());
        briefing.setOtherCompany(faker.company().name());

        // Act
        briefingRepository.save(briefing);
        Optional<Briefing> retrievedBriefing = briefingRepository.findById(briefing.getId());

        // Assert
        assertThat(retrievedBriefing).isPresent();
        assertThat(retrievedBriefing.get().getDetailedDescription()).isEqualTo(briefing.getDetailedDescription());
    }

    /**
     * Testa a recuperação de um briefing que não existe.
     */
    @Test
    @DisplayName("Should return empty when retrieving non-existing briefing")
    void testRetrieveNonExistingBriefing() {
        // Act
        Optional<Briefing> retrievedBriefing = briefingRepository.findById(999L); // ID que não existe

        // Assert
        assertThat(retrievedBriefing).isNotPresent();
    }
}
