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

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker.StickerInformationType;

@DataJpaTest
public class StickerInformationTypeRepositoryTest {

    @Autowired
    private StickerInformationTypeRepository stickerInformationTypeRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa a persistência e recuperação de um StickerInformationType.
     * Verifica se o objeto é salvo e pode ser recuperado corretamente.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should save and find StickerInformationType correctly")
    void testSaveAndFindStickerInformationType() {
        // Arrange
        StickerInformationType stickerInformationType = new StickerInformationType();
        stickerInformationType.setDescription(faker.lorem().sentence());

        // Act
        StickerInformationType savedType = stickerInformationTypeRepository.save(stickerInformationType);

        // Assert
        Optional<StickerInformationType> foundType = stickerInformationTypeRepository.findById(savedType.getId());
        assertThat(foundType).isPresent();
        assertThat(foundType.get().getDescription()).isEqualTo(stickerInformationType.getDescription());
    }
}
