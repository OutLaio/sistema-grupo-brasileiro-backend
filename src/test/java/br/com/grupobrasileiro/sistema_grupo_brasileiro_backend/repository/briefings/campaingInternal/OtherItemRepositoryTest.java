package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.campaingInternal;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.OtherItem;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
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

    /**
     * Testa a atualização de um OtherItem.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should update an OtherItem")
    void testUpdateOtherItem() {
        // Arrange
        OtherItem item = new OtherItem();
        item.setDescription("Item de exemplo");
        OtherItem savedItem = otherItemRepository.save(item);

        // Act
        savedItem.setDescription("Descrição atualizada");
        OtherItem updatedItem = otherItemRepository.save(savedItem);

        // Assert
        assertThat(updatedItem.getDescription()).isEqualTo("Descrição atualizada");
    }

    /**
     * Testa a exclusão de um OtherItem.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should delete an OtherItem")
    void testDeleteOtherItem() {
        // Arrange
        OtherItem item = new OtherItem();
        item.setDescription("Item de exemplo");
        OtherItem savedItem = otherItemRepository.save(item);

        // Act
        otherItemRepository.delete(savedItem);
        Optional<OtherItem> retrievedItem = otherItemRepository.findById(savedItem.getId());

        // Assert
        assertThat(retrievedItem).isNotPresent();
    }

    /**
     * Testa a recuperação de todos os OtherItems.
     */
    @Test
    @DisplayName("Should retrieve all OtherItems")
    void testFindAllOtherItems() {
        // Arrange
        OtherItem item1 = new OtherItem();
        item1.setDescription("Item 1");
        otherItemRepository.save(item1);

        OtherItem item2 = new OtherItem();
        item2.setDescription("Item 2");
        otherItemRepository.save(item2);

        // Act
        Iterable<OtherItem> items = otherItemRepository.findAll();

        // Assert
        assertThat(items).hasSize(2);
    }
}

