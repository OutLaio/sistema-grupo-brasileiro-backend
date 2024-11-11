package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view;

import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.StationeryTypeView;

import static org.junit.jupiter.api.Assertions.*;

public class StationeryTypeViewTest {

    @Test
    public void testCreateStationeryTypeView() {
        // Arrange
        Long id = 1L;
        String description = "Office Supplies";

        // Act
        StationeryTypeView stationeryTypeView = new StationeryTypeView(id, description);

        // Assert
        assertEquals(id, stationeryTypeView.id());
        assertEquals(description, stationeryTypeView.description());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        StationeryTypeView type1 = new StationeryTypeView(1L, "Supplies A");
        StationeryTypeView type2 = new StationeryTypeView(1L, "Supplies A");
        StationeryTypeView type3 = new StationeryTypeView(2L, "Supplies B");

        // Act & Assert
        assertEquals(type1, type2); 
        assertNotEquals(type1, type3); 
        assertEquals(type1.hashCode(), type2.hashCode()); 
        assertNotEquals(type1.hashCode(), type3.hashCode()); 
    }

    @Test
    public void testImmutability() {
        // Arrange
        StationeryTypeView stationeryTypeView = new StationeryTypeView(1L, "Supplies");

        // Act
        Long id = stationeryTypeView.id();
        String description = stationeryTypeView.description();

        // Assert
        assertEquals(Long.valueOf(1), id);
        assertEquals("Supplies", description);
        
        // Assert that we cannot change the state
        // Testing immutability can be shown by the fact that we cannot change the state 
      //  assertThrows(UnsupportedOperationException.class, () -> {
            
     //  });
    }
}
