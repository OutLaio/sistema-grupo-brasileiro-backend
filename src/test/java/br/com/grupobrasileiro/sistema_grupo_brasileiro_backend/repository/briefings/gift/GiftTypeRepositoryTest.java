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
}
