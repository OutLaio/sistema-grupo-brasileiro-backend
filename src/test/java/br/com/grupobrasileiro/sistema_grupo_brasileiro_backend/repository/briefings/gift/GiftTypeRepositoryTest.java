package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.GiftType;
import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GiftTypeRepositoryTest {

    @Mock
    private GiftTypeRepository giftTypeRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    @DisplayName("Should save and find GiftType correctly")
    void testSaveAndFindGiftType() {
        // Arrange
        GiftType giftType = new GiftType();
        giftType.setDescription(faker.lorem().sentence());
        when(giftTypeRepository.save(any(GiftType.class))).thenReturn(giftType);
        when(giftTypeRepository.findById(anyLong())).thenReturn(Optional.of(giftType));

        // Act
        GiftType savedType = giftTypeRepository.save(giftType);
        savedType.setId(1L);
        Optional<GiftType> foundType = giftTypeRepository.findById(savedType.getId());

        // Assert
        assertThat(foundType).isPresent();
        assertThat(foundType.get().getDescription()).isEqualTo(giftType.getDescription());
    }

    @Test
    @DisplayName("Should update a GiftType")
    void testUpdateGiftType() {
        // Arrange
        GiftType giftType = new GiftType();
        giftType.setDescription(faker.lorem().sentence());
        when(giftTypeRepository.save(any(GiftType.class))).thenReturn(giftType);
        when(giftTypeRepository.findById(anyLong())).thenReturn(Optional.of(giftType));

        GiftType savedType = giftTypeRepository.save(giftType);
        savedType.setDescription("Descrição Atualizada");

        // Act
        GiftType updatedType = giftTypeRepository.save(savedType);
        updatedType.setId(1L);
        Optional<GiftType> foundType = giftTypeRepository.findById(updatedType.getId());

        // Assert
        assertThat(foundType).isPresent();
        assertThat(foundType.get().getDescription()).isEqualTo("Descrição Atualizada");
    }

    @Test
    @DisplayName("Should delete a GiftType")
    void testDeleteGiftType() {
        // Arrange
        GiftType giftType = new GiftType();
        giftType.setDescription(faker.lorem().sentence());
        when(giftTypeRepository.save(any(GiftType.class))).thenReturn(giftType);
        when(giftTypeRepository.findById(anyLong())).thenReturn(Optional.of(giftType));

        GiftType savedType = giftTypeRepository.save(giftType);

        // Act
        giftTypeRepository.delete(savedType);
        when(giftTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<GiftType> foundType = giftTypeRepository.findById(savedType.getId());

        // Assert
        assertThat(foundType).isNotPresent();
    }

    @Test
    @DisplayName("Should retrieve all GiftTypes")
    void testFindAllGiftTypes() {
        // Arrange
        GiftType giftType1 = new GiftType();
        giftType1.setDescription(faker.lorem().sentence());
        GiftType giftType2 = new GiftType();
        giftType2.setDescription(faker.lorem().sentence());
        List<GiftType> giftTypes = new ArrayList<>();
        giftTypes.add(giftType1);
        giftTypes.add(giftType2);
        when(giftTypeRepository.findAll()).thenReturn(giftTypes);

        // Act
        Iterable<GiftType> allGiftTypes = giftTypeRepository.findAll();

        // Assert
        assertThat(allGiftTypes).hasSize(2);
    }
}
