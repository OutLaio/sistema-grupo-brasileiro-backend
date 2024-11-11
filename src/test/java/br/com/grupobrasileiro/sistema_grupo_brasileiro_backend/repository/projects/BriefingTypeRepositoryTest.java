package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class BriefingTypeRepositoryTest {

    @Mock
    private BriefingTypeRepository briefingTypeRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    @DisplayName("Should create and retrieve a briefing type")
    void testCreateAndRetrieveBriefingType() {
        // Arrange
        BriefingType briefingType = createTestBriefingType();
        when(briefingTypeRepository.save(any(BriefingType.class))).thenReturn(briefingType);
        when(briefingTypeRepository.findById(briefingType.getId())).thenReturn(Optional.of(briefingType));

        // Act
        BriefingType savedType = briefingTypeRepository.save(briefingType);
        Optional<BriefingType> retrievedBriefingType = briefingTypeRepository.findById(savedType.getId());

        // Assert
        assertThat(retrievedBriefingType).isPresent();
        assertThat(retrievedBriefingType.get().getDescription()).isEqualTo(savedType.getDescription());
    }

    @Test
    @DisplayName("Should return empty when retrieving non-existing briefing type")
    void testRetrieveNonExistingBriefingType() {
        // Act
        Optional<BriefingType> retrievedBriefingType = briefingTypeRepository.findById(999L); // ID que não existe

        // Assert
        assertThat(retrievedBriefingType).isNotPresent();
    }

    private BriefingType createTestBriefingType() {
        BriefingType briefingType = new BriefingType();
        briefingType.setDescription(faker.lorem().word());
        return briefingType;
    }
}
