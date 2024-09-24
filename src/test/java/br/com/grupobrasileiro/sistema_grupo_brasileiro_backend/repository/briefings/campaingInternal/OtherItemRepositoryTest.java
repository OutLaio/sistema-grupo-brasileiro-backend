package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.campaingInternal;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.OtherItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OtherItemRepositoryTest {

    @Autowired
    private OtherItemRepository otherItemRepository;

    /**
     * Testa a criação e recuperação de um OtherItem.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should create and retrieve an OtherItem")
    void testCreateAndRetrieveOtherItem() {
        // Arrange
        OtherItem item = new OtherItem();
        item.setDescription("Item de exemplo");

        // Act
        OtherItem savedItem = otherItemRepository.save(item);
        OtherItem retrievedItem = otherItemRepository.findById(savedItem.getId()).orElse(null);

        // Assert
        assertThat(retrievedItem).isNotNull();
        assertThat(retrievedItem.getId()).isEqualTo(savedItem.getId());
        assertThat(retrievedItem.getDescription()).isEqualTo("Item de exemplo");
    }
}
