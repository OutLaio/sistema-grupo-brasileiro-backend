package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.campaingInternal;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.OtherItem;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.StationeryType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects.BriefingRepository;
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

    /**
     * Testa a atualização de uma campanha interna.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should update a BInternalCampaign")
    void testUpdateBInternalCampaign() {
        // Arrange
        BInternalCampaign campaign = new BInternalCampaign();
        campaign.setStationeryType(new StationeryType());
        campaign.setOtherItem(new OtherItem());
        campaign.setBriefing(briefing);
        campaign.setCampaignMotto("Motto da campanha interna");
        BInternalCampaign savedCampaign = bInternalCampaignRepository.save(campaign);

        // Act
        savedCampaign.setCampaignMotto("Motto atualizado");
        BInternalCampaign updatedCampaign = bInternalCampaignRepository.save(savedCampaign);

        // Assert
        assertThat(updatedCampaign.getCampaignMotto()).isEqualTo("Motto atualizado");
    }

    /**
     * Testa a exclusão de uma campanha interna.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should delete a BInternalCampaign")
    void testDeleteBInternalCampaign() {
        // Arrange
        BInternalCampaign campaign = new BInternalCampaign();
        campaign.setStationeryType(new StationeryType());
        campaign.setOtherItem(new OtherItem());
        campaign.setBriefing(briefing);
        campaign.setCampaignMotto("Motto da campanha interna");
        BInternalCampaign savedCampaign = bInternalCampaignRepository.save(campaign);

        // Act
        bInternalCampaignRepository.delete(savedCampaign);
        Optional<BInternalCampaign> retrievedCampaign = bInternalCampaignRepository.findById(savedCampaign.getId());

        // Assert
        assertThat(retrievedCampaign).isNotPresent();
    }

    /**
     * Testa a recuperação de todas as campanhas internas.
     */
    @Test
    @DisplayName("Should retrieve all BInternalCampaigns")
    void testFindAllBInternalCampaigns() {
        // Arrange
        BInternalCampaign campaign1 = new BInternalCampaign();
        campaign1.setStationeryType(new StationeryType());
        campaign1.setOtherItem(new OtherItem());
        campaign1.setBriefing(briefing);
        campaign1.setCampaignMotto("Motto 1");
        bInternalCampaignRepository.save(campaign1);

        BInternalCampaign campaign2 = new BInternalCampaign();
        campaign2.setStationeryType(new StationeryType());
        campaign2.setOtherItem(new OtherItem());
        campaign2.setBriefing(briefing);
        campaign2.setCampaignMotto("Motto 2");
        bInternalCampaignRepository.save(campaign2);

        // Act
        Iterable<BInternalCampaign> campaigns = bInternalCampaignRepository.findAll();

        // Assert
        assertThat(campaigns).hasSize(2);
    }
}
