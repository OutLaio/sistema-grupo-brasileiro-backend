package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.campaingInternal;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.OtherItem;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.StationeryType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BInternalCampaignRepositoryTest {

    @Autowired
    private BInternalCampaignRepository bInternalCampaignRepository;

    @Autowired
    private BriefingRepository briefingRepository; 

    private Briefing briefing;

    @BeforeEach
    void setUp() {
        // Criação de um briefing para associar à campanha interna
        briefing = new Briefing();
        briefing.setDetailedDescription("Descrição detalhada do briefing");
        // Adicione outras configurações necessárias para o briefing
        briefingRepository.save(briefing);
    }

    /**
     * Testa a criação e recuperação de uma campanha interna.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should create and retrieve a BInternalCampaign")
    void testCreateAndRetrieveBInternalCampaign() {
        // Arrange
        BInternalCampaign campaign = new BInternalCampaign();
        campaign.setStationeryType(new StationeryType()); 
        campaign.setOtherItem(new OtherItem()); 
        campaign.setBriefing(briefing);
        campaign.setCampaignMotto("Motto da campanha interna");

        // Act
        BInternalCampaign savedCampaign = bInternalCampaignRepository.save(campaign);
        BInternalCampaign retrievedCampaign = bInternalCampaignRepository.findById(savedCampaign.getId()).orElse(null);

        // Assert
        assertThat(retrievedCampaign).isNotNull();
        assertThat(retrievedCampaign.getId()).isEqualTo(savedCampaign.getId());
        assertThat(retrievedCampaign.getCampaignMotto()).isEqualTo("Motto da campanha interna");
        assertThat(retrievedCampaign.getBriefing()).isEqualTo(briefing);
    }
}
