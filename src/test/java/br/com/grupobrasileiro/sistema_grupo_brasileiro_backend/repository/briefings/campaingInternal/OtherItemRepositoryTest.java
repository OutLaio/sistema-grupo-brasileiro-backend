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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OtherItemRepositoryTest {

    @Mock
    private OtherItemRepository otherItemRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create and retrieve an OtherItem")
    void testCreateAndRetrieveOtherItem() {
        // Arrange
        OtherItem item = new OtherItem();
        item.setDescription("Item de exemplo");

        when(otherItemRepository.save(any(OtherItem.class))).thenReturn(item);
        when(otherItemRepository.findById(item.getId())).thenReturn(Optional.of(item));

        // Act
        OtherItem savedItem = otherItemRepository.save(item);
        OtherItem retrievedItem = otherItemRepository.findById(savedItem.getId()).orElse(null);

        // Assert
        assertThat(retrievedItem).isNotNull();
        assertThat(retrievedItem.getId()).isEqualTo(savedItem.getId());
        assertThat(retrievedItem.getDescription()).isEqualTo("Item de exemplo");
    }

    @Test
    @DisplayName("Should update an OtherItem")
    void testUpdateOtherItem() {
        // Arrange
        OtherItem item = new OtherItem();
        item.setDescription("Item de exemplo");
        when(otherItemRepository.save(any(OtherItem.class))).thenReturn(item);

        OtherItem savedItem = otherItemRepository.save(item);
        savedItem.setDescription("Descrição atualizada");

        when(otherItemRepository.save(savedItem)).thenReturn(savedItem);

        // Act
        OtherItem updatedItem = otherItemRepository.save(savedItem);

        // Assert
        assertThat(updatedItem.getDescription()).isEqualTo("Descrição atualizada");
    }

    @Test
    @DisplayName("Should delete an OtherItem")
    void testDeleteOtherItem() {
        // Arrange
        OtherItem item = new OtherItem();
        item.setDescription("Item de exemplo");
        when(otherItemRepository.save(any(OtherItem.class))).thenReturn(item);
        when(otherItemRepository.findById(item.getId())).thenReturn(Optional.of(item));

        OtherItem savedItem = otherItemRepository.save(item);

        // Act
        otherItemRepository.delete(savedItem);
        when(otherItemRepository.findById(savedItem.getId())).thenReturn(Optional.empty());

        Optional<OtherItem> retrievedItem = otherItemRepository.findById(savedItem.getId());

        // Assert
        assertThat(retrievedItem).isNotPresent();
    }

    @Test
    @DisplayName("Should retrieve all OtherItems")
    void testFindAllOtherItems() {
        // Arrange
        List<OtherItem> items = new ArrayList<>();
        OtherItem item1 = new OtherItem();
        item1.setDescription("Item 1");
        items.add(item1);

        OtherItem item2 = new OtherItem();
        item2.setDescription("Item 2");
        items.add(item2);

        when(otherItemRepository.findAll()).thenReturn(items);

        // Act
        List<OtherItem> retrievedItems = (List<OtherItem>) otherItemRepository.findAll();

        // Assert
        assertThat(retrievedItems).hasSize(2);
        assertThat(retrievedItems).extracting(OtherItem::getDescription)
                                   .containsExactlyInAnyOrder("Item 1", "Item 2");
    }
}
