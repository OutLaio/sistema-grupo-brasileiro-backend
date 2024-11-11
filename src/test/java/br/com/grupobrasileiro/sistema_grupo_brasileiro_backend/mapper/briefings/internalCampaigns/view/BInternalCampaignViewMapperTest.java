package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.BInternalCampaignsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.OtherItemView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.view.StationeryTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.view.BInternalCampaignViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.view.OtherItemViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.view.StationeryTypeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.OtherItem;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.StationeryType;

public class BInternalCampaignViewMapperTest {

    @InjectMocks
    private BInternalCampaignViewMapper bInternalCampaignViewMapper;

    @Mock
    private OtherItemViewMapper otherItemViewMapper;

    @Mock
    private StationeryTypeViewMapper stationeryTypeViewMapper;

    private BInternalCampaign internalCampaign;
    private OtherItem otherItem;
    private StationeryType stationeryType;
    private OtherItemView otherItemView;
    private StationeryTypeView stationeryTypeView;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup do objeto a ser mapeado
        otherItem = new OtherItem();
        otherItem.setDescription("Some Value"); // Defina o campo conforme necessário

        stationeryType = new StationeryType();
        stationeryType.setDescription("Another Value"); // Defina o campo conforme necessário

        internalCampaign = new BInternalCampaign();
        internalCampaign.setId(1L);
        internalCampaign.setOtherItem(otherItem);
        internalCampaign.setStationeryType(stationeryType);
        internalCampaign.setCampaignMotto("Campaign Motto");

        // Setup do mock do mapeador
        otherItemView = new OtherItemView(1L, "Some Value");
        stationeryTypeView = new StationeryTypeView(1L, "Another Value");
        
        when(otherItemViewMapper.map(otherItem)).thenReturn(otherItemView);
        when(stationeryTypeViewMapper.map(stationeryType)).thenReturn(stationeryTypeView);
    }

    @Test
    public void testMap() {
        // Chamar o método map
        BInternalCampaignsView result = bInternalCampaignViewMapper.map(internalCampaign);

        // Verificações
        assertEquals(1L, result.id());
        assertEquals(otherItemView, result.otherItem());
        assertEquals(stationeryTypeView, result.stationeryType());
        assertEquals("Campaign Motto", result.campaignMotto());

        // Verificar se os métodos de mapeamento foram chamados
        verify(otherItemViewMapper).map(otherItem);
        verify(stationeryTypeViewMapper).map(stationeryType);
    }
}
