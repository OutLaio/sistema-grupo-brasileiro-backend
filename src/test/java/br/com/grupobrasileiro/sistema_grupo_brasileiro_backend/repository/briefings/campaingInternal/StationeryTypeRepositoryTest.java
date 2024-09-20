package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.campaingInternal;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.StationeryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
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
}
