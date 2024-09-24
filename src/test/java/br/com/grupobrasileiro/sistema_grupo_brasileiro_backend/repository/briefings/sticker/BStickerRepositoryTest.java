package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.sticker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.BSticker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.StickerType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;

@DataJpaTest
public class BStickerRepositoryTest {

    @Autowired
    private BStickerRepository bStickerRepository;

    @Autowired
    private StickerTypeRepository stickerTypeRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    @Test
    @Rollback(false)
    @DisplayName("Should save and find BSticker correctly")
    void testSaveAndFindBSticker() {
        // Arrange
        StickerType stickerType = new StickerType(); 
        stickerType.setDescription(faker.lorem().word());
        stickerTypeRepository.save(stickerType);

        BSticker bSticker = new BSticker();
        bSticker.setBriefing(new Briefing()); 
        bSticker.setStickerType(stickerType);
        bSticker.setSector(faker.lorem().word());
        bSticker.setObservations(faker.lorem().sentence());

        // Act
        BSticker savedSticker = bStickerRepository.save(bSticker);

        // Assert
        Optional<BSticker> foundSticker = bStickerRepository.findById(savedSticker.getId());
        assertThat(foundSticker).isPresent();
        assertThat(foundSticker.get().getSector()).isEqualTo(bSticker.getSector());
        assertThat(foundSticker.get().getObservations()).isEqualTo(bSticker.getObservations());
    }

    @Test
    @DisplayName("Should not find BSticker with nonexistent ID")
    void testNotFoundBSticker() {
        // Act
        Optional<BSticker> foundSticker = bStickerRepository.findById(-1L); // ID inexistente

        // Assert
        assertThat(foundSticker).isNotPresent();
    }
}
