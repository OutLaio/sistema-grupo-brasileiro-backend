package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.OtherItemView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OtherItemViewTest {

    @Test
    public void testCreateOtherItemView() {
        // Arrange
        Long id = 1L;
        String description = "Descrição do Item";

        // Act
        OtherItemView otherItem = new OtherItemView(id, description);

        // Assert
        assertEquals(id, otherItem.id());
        assertEquals(description, otherItem.description());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        OtherItemView otherItem1 = new OtherItemView(1L, "Descrição do Item");
        OtherItemView otherItem2 = new OtherItemView(1L, "Descrição do Item");
        OtherItemView otherItem3 = new OtherItemView(2L, "Outra Descrição");

        // Act & Assert
        assertEquals(otherItem1, otherItem2); // devem ser iguais
        assertNotEquals(otherItem1, otherItem3); // não devem ser iguais
        assertEquals(otherItem1.hashCode(), otherItem2.hashCode()); // mesmos hash codes para objetos iguais
        assertNotEquals(otherItem1.hashCode(), otherItem3.hashCode()); // hash codes diferentes para objetos diferentes
    }
}
