package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.GiftType;

@DataJpaTest
public class GiftTypeRepositoryTest {

    @Autowired
    private GiftTypeRepository giftTypeRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa a persistência e recuperação de um GiftType.
     * Verifica se o objeto é salvo e pode ser recuperado corretamente.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should save and find GiftType correctly")
    void testSaveAndFindGiftType() {
        // Arrange
        GiftType giftType = new GiftType();
        giftType.setDescription(faker.lorem().sentence());

        // Act
        GiftType savedType = giftTypeRepository.save(giftType);

        // Assert
        Optional<GiftType> foundType = giftTypeRepository.findById(savedType.getId());
        assertThat(foundType).isPresent();
        assertThat(foundType.get().getDescription()).isEqualTo(giftType.getDescription());
    }

    /**
     * Testa a atualização de um GiftType.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should update a GiftType")
    void testUpdateGiftType() {
        // Arrange
        GiftType giftType = new GiftType();
        giftType.setDescription(faker.lorem().sentence());
        GiftType savedType = giftTypeRepository.save(giftType);

        // Act - Atualiza a descrição do tipo de presente
        savedType.setDescription("Descrição Atualizada");
        GiftType updatedType = giftTypeRepository.save(savedType);

        // Assert
        assertThat(updatedType.getDescription()).isEqualTo("Descrição Atualizada");
    }

    /**
     * Testa a exclusão de um GiftType.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should delete a GiftType")
    void testDeleteGiftType() {
        // Arrange
        GiftType giftType = new GiftType();
        giftType.setDescription(faker.lorem().sentence());
        GiftType savedType = giftTypeRepository.save(giftType);

        // Act
        giftTypeRepository.delete(savedType);
        Optional<GiftType> foundType = giftTypeRepository.findById(savedType.getId());

        // Assert
        assertThat(foundType).isNotPresent();
    }

    /**
     * Testa a recuperação de todos os GiftTypes.
     */
    @Test
    @DisplayName("Should retrieve all GiftTypes")
    void testFindAllGiftTypes() {
        // Arrange
        GiftType giftType1 = new GiftType();
        giftType1.setDescription(faker.lorem().sentence());
        GiftType giftType2 = new GiftType();
        giftType2.setDescription(faker.lorem().sentence());
        giftTypeRepository.save(giftType1);
        giftTypeRepository.save(giftType2);

        // Act
        Iterable<GiftType> allGiftTypes = giftTypeRepository.findAll();

        // Assert
        assertThat(allGiftTypes).hasSize(2);
    }
}
