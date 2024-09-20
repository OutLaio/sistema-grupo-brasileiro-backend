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

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.StickerType;

@DataJpaTest
public class StickerTypeRepositoryTest {

    @Autowired
    private StickerTypeRepository stickerTypeRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa a persistência e recuperação de um StickerType.
     * Verifica se o objeto é salvo e pode ser recuperado corretamente.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should save and find StickerType correctly")
    void testSaveAndFindStickerType() {
        // Arrange
        StickerType stickerType = new StickerType();
        stickerType.setType(faker.lorem().word());

        // Act
        StickerType savedType = stickerTypeRepository.save(stickerType);

        // Assert
        Optional<StickerType> foundType = stickerTypeRepository.findById(savedType.getId());
        assertThat(foundType).isPresent();
        assertThat(foundType.get().getType()).isEqualTo(stickerType.getType());
    }
}
