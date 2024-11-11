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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StationeryTypeRepositoryTest {

    @Mock
    private StationeryTypeRepository stationeryTypeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create and retrieve a StationeryType")
    void testCreateAndRetrieveStationeryType() {
        // Arrange
        StationeryType stationeryType = new StationeryType();
        stationeryType.setDescription("Tipo de papel");
        when(stationeryTypeRepository.save(any(StationeryType.class))).thenReturn(stationeryType);
        when(stationeryTypeRepository.findById(anyLong())).thenReturn(Optional.of(stationeryType));

        // Act
        StationeryType savedType = stationeryTypeRepository.save(stationeryType);
        StationeryType retrievedType = stationeryTypeRepository.findById(savedType.getId()).orElse(null);

        // Assert
        assertThat(retrievedType).isNotNull();
        assertThat(retrievedType.getId()).isEqualTo(savedType.getId());
        assertThat(retrievedType.getDescription()).isEqualTo("Tipo de papel");
    }

    @Test
    @DisplayName("Should update a StationeryType")
    void testUpdateStationeryType() {
        // Arrange
        StationeryType stationeryType = new StationeryType();
        stationeryType.setDescription("Tipo de papel");
        when(stationeryTypeRepository.save(any(StationeryType.class))).thenReturn(stationeryType);
        when(stationeryTypeRepository.findById(anyLong())).thenReturn(Optional.of(stationeryType));

        StationeryType savedType = stationeryTypeRepository.save(stationeryType);
        savedType.setDescription("Tipo de papel atualizado");

        // Act
        StationeryType updatedType = stationeryTypeRepository.save(savedType);

        // Assert
        assertThat(updatedType.getDescription()).isEqualTo("Tipo de papel atualizado");
    }

    @Test
    @DisplayName("Should delete a StationeryType")
    void testDeleteStationeryType() {
        // Arrange
        StationeryType stationeryType = new StationeryType();
        stationeryType.setDescription("Tipo de papel");
        when(stationeryTypeRepository.save(any(StationeryType.class))).thenReturn(stationeryType);
        when(stationeryTypeRepository.findById(anyLong())).thenReturn(Optional.of(stationeryType));

        StationeryType savedType = stationeryTypeRepository.save(stationeryType);

        // Act
        stationeryTypeRepository.delete(savedType);
        when(stationeryTypeRepository.findById(savedType.getId())).thenReturn(Optional.empty());

        Optional<StationeryType> retrievedType = stationeryTypeRepository.findById(savedType.getId());

        // Assert
        assertThat(retrievedType).isNotPresent();
    }

    @Test
    @DisplayName("Should retrieve all StationeryTypes")
    void testFindAllStationeryTypes() {
        // Arrange
        List<StationeryType> stationeryTypes = new ArrayList<>();
        StationeryType type1 = new StationeryType();
        type1.setDescription("Tipo de papel 1");
        StationeryType type2 = new StationeryType();
        type2.setDescription("Tipo de papel 2");
        stationeryTypes.add(type1);
        stationeryTypes.add(type2);
        when(stationeryTypeRepository.findAll()).thenReturn(stationeryTypes);

        // Act
        List<StationeryType> types = (List<StationeryType>) stationeryTypeRepository.findAll();

        // Assert
        assertThat(types).hasSize(2);
        assertThat(types).extracting(StationeryType::getDescription)
                         .containsExactlyInAnyOrder("Tipo de papel 1", "Tipo de papel 2");
    }
}
