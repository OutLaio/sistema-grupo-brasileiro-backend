package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
public class BriefingTypeRepositoryTest {

    @Autowired
    private BriefingTypeRepository briefingTypeRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa a criação e recuperação de um tipo de briefing.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should create and retrieve a briefing type")
    void testCreateAndRetrieveBriefingType() {
        // Arrange
        BriefingType briefingType = new BriefingType();
        briefingType.setDescription(faker.lorem().word());

        // Act
        briefingTypeRepository.save(briefingType);
        Optional<BriefingType> retrievedBriefingType = briefingTypeRepository.findById(briefingType.getId());

        // Assert
        assertThat(retrievedBriefingType).isPresent();
        assertThat(retrievedBriefingType.get().getDescription()).isEqualTo(briefingType.getDescription());
    }

    /**
     * Testa a recuperação de um tipo de briefing que não existe.
     */
    @Test
    @DisplayName("Should return empty when retrieving non-existing briefing type")
    void testRetrieveNonExistingBriefingType() {
        // Act
        Optional<BriefingType> retrievedBriefingType = briefingTypeRepository.findById(999L); // ID que não existe

        // Assert
        assertThat(retrievedBriefingType).isNotPresent();
    }
}
