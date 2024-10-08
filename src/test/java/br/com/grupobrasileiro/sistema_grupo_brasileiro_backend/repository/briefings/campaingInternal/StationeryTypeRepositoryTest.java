package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.campaingInternal;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.StationeryType;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StationeryTypeRepositoryTest {

    @Autowired
    private StationeryTypeRepository stationeryTypeRepository;

    /**
     * Testa a criação e recuperação de um StationeryType.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should create and retrieve a StationeryType")
    void testCreateAndRetrieveStationeryType() {
        // Arrange
        StationeryType stationeryType = new StationeryType();
        stationeryType.setDescription("Tipo de papel");

        // Act
        StationeryType savedType = stationeryTypeRepository.save(stationeryType);
        StationeryType retrievedType = stationeryTypeRepository.findById(savedType.getId()).orElse(null);

        // Assert
        assertThat(retrievedType).isNotNull();
        assertThat(retrievedType.getId()).isEqualTo(savedType.getId());
        assertThat(retrievedType.getDescription()).isEqualTo("Tipo de papel");
    }

    /**
     * Testa a atualização de um StationeryType.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should update a StationeryType")
    void testUpdateStationeryType() {
        // Arrange
        StationeryType stationeryType = new StationeryType();
        stationeryType.setDescription("Tipo de papel");
        StationeryType savedType = stationeryTypeRepository.save(stationeryType);

        // Act
        savedType.setDescription("Tipo de papel atualizado");
        StationeryType updatedType = stationeryTypeRepository.save(savedType);

        // Assert
        assertThat(updatedType.getDescription()).isEqualTo("Tipo de papel atualizado");
    }

    /**
     * Testa a exclusão de um StationeryType.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should delete a StationeryType")
    void testDeleteStationeryType() {
        // Arrange
        StationeryType stationeryType = new StationeryType();
        stationeryType.setDescription("Tipo de papel");
        StationeryType savedType = stationeryTypeRepository.save(stationeryType);

        // Act
        stationeryTypeRepository.delete(savedType);
        Optional<StationeryType> retrievedType = stationeryTypeRepository.findById(savedType.getId());

        // Assert
        assertThat(retrievedType).isNotPresent();
    }

    /**
     * Testa a recuperação de todos os StationeryTypes.
     */
    @Test
    @DisplayName("Should retrieve all StationeryTypes")
    void testFindAllStationeryTypes() {
        // Arrange
        stationeryTypeRepository.deleteAll(); // Limpa todos os tipos existentes

        StationeryType type1 = new StationeryType();
        type1.setDescription("Tipo de papel 1");
        stationeryTypeRepository.save(type1);

        StationeryType type2 = new StationeryType();
        type2.setDescription("Tipo de papel 2");
        stationeryTypeRepository.save(type2);

        // Act
        List<StationeryType> types = (List<StationeryType>) stationeryTypeRepository.findAll();

        // Assert
        assertThat(types).hasSize(2);
        assertThat(types).extracting(StationeryType::getDescription)
                         .containsExactlyInAnyOrder("Tipo de papel 1", "Tipo de papel 2");
    }
}

