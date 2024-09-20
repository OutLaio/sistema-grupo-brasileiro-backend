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

@DataJpaTest
public class BStickerRepositoryTest {

    @Autowired
    private BStickerRepository bStickerRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa a persistência e recuperação de um BSticker.
     * Verifica se o objeto é salvo e pode ser recuperado corretamente.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should save and find BSticker correctly")
    void testSaveAndFindBSticker() {
        // Arrange
        BSticker bSticker = new BSticker();
        bSticker.setName(faker.lorem().word());

        // Act
        BSticker savedSticker = bStickerRepository.save(bSticker);

        // Assert
        Optional<BSticker> foundSticker = bStickerRepository.findById(savedSticker.getId());
        assertThat(foundSticker).isPresent();
        assertThat(foundSticker.get().getName()).isEqualTo(bSticker.getName());
    }
}
