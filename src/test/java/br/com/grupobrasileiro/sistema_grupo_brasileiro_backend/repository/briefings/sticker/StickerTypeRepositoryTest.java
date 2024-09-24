package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.sticker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        stickerType.setDescription(faker.lorem().word());

        // Act
        StickerType savedType = stickerTypeRepository.save(stickerType);

        // Assert
        Optional<StickerType> foundType = stickerTypeRepository.findById(savedType.getId());
        assertThat(foundType).isPresent();
        assertThat(foundType.get().getDescription()).isEqualTo(stickerType.getDescription());
    }

    /**
     * Testa a atualização de um StickerType.
     * Verifica se o objeto é atualizado corretamente.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should update StickerType correctly")
    void testUpdateStickerType() {
        // Arrange
        StickerType stickerType = new StickerType();
        stickerType.setDescription(faker.lorem().word());
        StickerType savedType = stickerTypeRepository.save(stickerType);

        // Act
        savedType.setDescription(faker.lorem().word());
        StickerType updatedType = stickerTypeRepository.save(savedType);

        // Assert
        Optional<StickerType> foundType = stickerTypeRepository.findById(updatedType.getId());
        assertThat(foundType).isPresent();
        assertThat(foundType.get().getDescription()).isEqualTo(updatedType.getDescription());
    }

    /**
     * Testa a exclusão de um StickerType.
     * Verifica se o objeto é excluído corretamente.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should delete StickerType correctly")
    void testDeleteStickerType() {
        // Arrange
        StickerType stickerType = new StickerType();
        stickerType.setDescription(faker.lorem().word());
        StickerType savedType = stickerTypeRepository.save(stickerType);

        // Act
        stickerTypeRepository.delete(savedType);

        // Assert
        Optional<StickerType> foundType = stickerTypeRepository.findById(savedType.getId());
        assertThat(foundType).isNotPresent();
    }

    /**
     * Testa a busca de um StickerType inexistente.
     * Verifica se retorna um valor vazio.
     */
    @Test
    @DisplayName("Should not find non-existent StickerType")
    void testFindNonExistentStickerType() {
        // Act
        Optional<StickerType> foundType = stickerTypeRepository.findById(Long.MAX_VALUE);

        // Assert
        assertThat(foundType).isNotPresent();
    }

    /**
     * Testa a tentativa de salvar um StickerType com descrição nula.
     * Verifica se lança uma exceção conforme esperado.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should throw exception when saving null description")
    void testSaveStickerTypeWithNullDescription() {
        // Arrange
        StickerType stickerType = new StickerType();
        stickerType.setDescription(null); // Null value for non-nullable field

        // Act & Assert
        assertThrows(javax.persistence.EntityExistsException.class, () -> {
            stickerTypeRepository.save(stickerType);
        });
    }
}
