package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.form.BInternalCampaignsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.form.BInternalCampaignFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.OtherItem;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.StationeryType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.campaingInternal.OtherItemRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.campaingInternal.StationeryTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class BInternalCampaignFormMapperTest {

    @Mock
    private StationeryTypeRepository stationeryTypeRepository;

    @Mock
    private OtherItemRepository otherItemRepository;

    @InjectMocks
    private BInternalCampaignFormMapper bInternalCampaignFormMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMapSuccessfully() {
        // Arrange
        BInternalCampaignsForm form = new BInternalCampaignsForm(1L, 2L, "Campaign Motto");
        StationeryType stationeryType = new StationeryType();
        OtherItem otherItem = new OtherItem();

        // Simular o comportamento dos repositórios
        when(stationeryTypeRepository.findById(1L)).thenReturn(Optional.of(stationeryType));
        when(otherItemRepository.findById(2L)).thenReturn(Optional.of(otherItem));

        // Act
        BInternalCampaign result = bInternalCampaignFormMapper.map(form);

        // Assert
        assertEquals(stationeryType, result.getStationeryType());
        assertEquals(otherItem, result.getOtherItem());
        assertEquals(form.campaignMotto(), result.getCampaignMotto());
    }

    @Test
    public void testMapWithNullInput() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            bInternalCampaignFormMapper.map(null);
        });
    }

    @Test
    public void testMapWhenStationeryTypeNotFound() {
        // Arrange
        BInternalCampaignsForm form = new BInternalCampaignsForm(1L, 2L, "Campaign Motto");
        when(stationeryTypeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            bInternalCampaignFormMapper.map(form);
        });
        assertEquals("Stationery Type not found with id: 1", thrown.getMessage());
    }

    @Test
    public void testMapWhenOtherItemNotFound() {
        // Arrange
        BInternalCampaignsForm form = new BInternalCampaignsForm(1L, 2L, "Campaign Motto");
        when(stationeryTypeRepository.findById(1L)).thenReturn(Optional.of(new StationeryType()));
        when(otherItemRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            bInternalCampaignFormMapper.map(form);
        });
        assertEquals("Other Item not found with id: 2", thrown.getMessage());
    }
}
