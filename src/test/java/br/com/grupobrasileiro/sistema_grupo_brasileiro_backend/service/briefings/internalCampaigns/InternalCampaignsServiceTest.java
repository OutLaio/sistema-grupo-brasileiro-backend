package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.internalCampaigns;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.InternalCampaigns.form.BInternalCampaignsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.internalCampaigns.form.BInternalCampaignFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.campaingInternal.BInternalCampaignRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InternalCampaignsServiceTest {

    @Mock
    private BInternalCampaignRepository bInternalCampaignRepository;

    @Mock
    private BInternalCampaignFormMapper bInternalCampaignFormMapper;

    @InjectMocks
    private InternalCampaignsService internalCampaignsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        // Arrange
        BInternalCampaignsForm form = new BInternalCampaignsForm(1L, 2L, "Campaign Motto");
        Briefing briefing = new Briefing();
        BInternalCampaign bInternalCampaign = new BInternalCampaign();

        // Configura o comportamento do mapper
        when(bInternalCampaignFormMapper.map(form)).thenReturn(bInternalCampaign);

        // Act
        internalCampaignsService.register(form, briefing);

        // Assert
        // Verifica se o briefing foi associado corretamente
        verify(bInternalCampaignFormMapper).map(form); 
        verify(bInternalCampaignRepository).save(bInternalCampaign); 
        assertEquals(briefing, bInternalCampaign.getBriefing());  
    }
}
