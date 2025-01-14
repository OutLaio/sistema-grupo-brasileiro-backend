package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.campaingInternal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.OtherItem;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.StationeryType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.ProjectRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.EmployeeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.ProfileRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;

public class BInternalCampaignRepositoryTest {

    @Mock
    private BInternalCampaignRepository bInternalCampaignRepository;

    @Mock
    private BriefingRepository briefingRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private BriefingTypeRepository briefingTypeRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StationeryTypeRepository stationeryTypeRepository;

    @Mock
    private OtherItemRepository otherItemRepository;

    private Briefing briefing;
    private StationeryType stationeryType;
    private OtherItem otherItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configuração de objetos mockados
        stationeryType = new StationeryType();
        stationeryType.setDescription("Tipo de Papelaria Teste");

        otherItem = new OtherItem();
        otherItem.setDescription("Outro Item Teste");

        briefing = new Briefing();
        briefing.setDetailedDescription("Descrição detalhada do briefing");

        when(stationeryTypeRepository.save(any(StationeryType.class))).thenReturn(stationeryType);
        when(otherItemRepository.save(any(OtherItem.class))).thenReturn(otherItem);
        when(briefingRepository.save(any(Briefing.class))).thenReturn(briefing);
    }

    private BInternalCampaign createBInternalCampaign(String motto) {
        BInternalCampaign campaign = new BInternalCampaign();
        campaign.setStationeryType(stationeryType);
        campaign.setOtherItem(otherItem);
        campaign.setBriefing(briefing);
        campaign.setCampaignMotto(motto);
        return campaign;
    }

    @Test
    @DisplayName("Must update a BInternalCampaign")
    void testUpdateBInternalCampaign() {
        BInternalCampaign campaign = createBInternalCampaign("Motto da campanha interna");
        when(bInternalCampaignRepository.save(any(BInternalCampaign.class))).thenReturn(campaign);

        BInternalCampaign savedCampaign = bInternalCampaignRepository.save(campaign);
        savedCampaign.setCampaignMotto("Motto atualizado");
        when(bInternalCampaignRepository.save(savedCampaign)).thenReturn(savedCampaign);

        BInternalCampaign updatedCampaign = bInternalCampaignRepository.save(savedCampaign);

        assertThat(updatedCampaign.getCampaignMotto()).isEqualTo("Motto atualizado");
    }

    @Test
    @DisplayName("Must delete a BInternalCampaign")
    void testDeleteBInternalCampaign() {
        BInternalCampaign campaign = createBInternalCampaign("Motto da campanha interna");
        when(bInternalCampaignRepository.save(any(BInternalCampaign.class))).thenReturn(campaign);
        when(bInternalCampaignRepository.findById(anyLong())).thenReturn(Optional.of(campaign));

        BInternalCampaign savedCampaign = bInternalCampaignRepository.save(campaign);

        bInternalCampaignRepository.delete(savedCampaign);
        when(bInternalCampaignRepository.findById(savedCampaign.getId())).thenReturn(Optional.empty());

        Optional<BInternalCampaign> retrievedCampaign = bInternalCampaignRepository.findById(savedCampaign.getId());

        assertThat(retrievedCampaign).isNotPresent();
    }
}
