package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaing;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.OtherItem;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.StationeryType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;

public class BInternalCampaignTest {

    private BInternalCampaign bInternalCampaign;
    private StationeryType stationeryType;
    private OtherItem otherItem;
    private Briefing briefing;

    @BeforeEach
    void setUp() {
        // Configurar os dados necessários
        stationeryType = new StationeryType();
        stationeryType.setId(1L);
        stationeryType.setDescription("Stationery A");

        otherItem = new OtherItem();
        otherItem.setId(1L);
        otherItem.setDescription("Item B");

        briefing = new Briefing();
        briefing.setId(1L);

        bInternalCampaign = new BInternalCampaign();
        bInternalCampaign.setId(1L);
        bInternalCampaign.setStationeryType(stationeryType);
        bInternalCampaign.setOtherItem(otherItem);
        bInternalCampaign.setBriefing(briefing);
        bInternalCampaign.setCampaignMotto("Innovative Campaign");
    }

    @Test
    void testBInternalCampaign() {
        // Verificar se os dados foram definidos corretamente
        assertThat(bInternalCampaign.getId()).isEqualTo(1L);
        assertThat(bInternalCampaign.getStationeryType()).isEqualTo(stationeryType);
        assertThat(bInternalCampaign.getOtherItem()).isEqualTo(otherItem);
        assertThat(bInternalCampaign.getBriefing()).isEqualTo(briefing);
        assertThat(bInternalCampaign.getCampaignMotto()).isEqualTo("Innovative Campaign");
    }
}
