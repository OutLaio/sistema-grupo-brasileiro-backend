package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.BInternalCampaignsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.OtherItemView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.StationeryTypeView;

public class BInternalCampaignsViewTest {

    @Test
    public void testCreateBInternalCampaignsView() {
        // Arrange
        Long id = 1L;
        StationeryTypeView stationeryType = new StationeryTypeView(1L, "Tipo de Papel");
        OtherItemView otherItem = new OtherItemView(1L, "Item Adicional");
        String campaignMotto = "Nosso Lema da Campanha";

        // Act
        BInternalCampaignsView internalCampaign = new BInternalCampaignsView(id, stationeryType, otherItem, campaignMotto);

        // Assert
        assertEquals(id, internalCampaign.id());
        assertEquals(stationeryType, internalCampaign.stationeryType());
        assertEquals(otherItem, internalCampaign.otherItem());
        assertEquals(campaignMotto, internalCampaign.campaignMotto());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        StationeryTypeView stationeryType1 = new StationeryTypeView(1L, "Tipo de Papel");
        StationeryTypeView stationeryType2 = new StationeryTypeView(2L, "Outro Tipo");
        OtherItemView otherItem = new OtherItemView(1L, "Item Adicional");
        String campaignMotto = "Nosso Lema da Campanha";

        BInternalCampaignsView internalCampaign1 = new BInternalCampaignsView(1L, stationeryType1, otherItem, campaignMotto);
        BInternalCampaignsView internalCampaign2 = new BInternalCampaignsView(1L, stationeryType1, otherItem, campaignMotto);
        BInternalCampaignsView internalCampaign3 = new BInternalCampaignsView(2L, stationeryType2, otherItem, "Outro Lema");

        // Act & Assert
        assertEquals(internalCampaign1, internalCampaign2); // devem ser iguais
        assertNotEquals(internalCampaign1, internalCampaign3); // não devem ser iguais
        assertEquals(internalCampaign1.hashCode(), internalCampaign2.hashCode()); // mesmos hash codes para objetos iguais
        assertNotEquals(internalCampaign1.hashCode(), internalCampaign3.hashCode()); // hash codes diferentes para objetos diferentes
    }
}
