package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.BInternalCampaign;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.OtherItem;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.StationeryType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;

/**
 * Testa a classe BInternalCampaign.
 * Verifica se os métodos da classe funcionam corretamente e se os dados são configurados adequadamente.
 */
public class BInternalCampaignTest {

    private BInternalCampaign bInternalCampaign;
    private StationeryType stationeryType;
    private OtherItem otherItem;
    private Briefing briefing;

    @BeforeEach
    void setUp() {
        // Configurar os dados necessários para os testes
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

    /**
     * Testa a instância da classe BInternalCampaign.
     * Verifica se as propriedades do BInternalCampaign estão definidas corretamente 
     * após a configuração inicial no método setUp.
     */
    @Test
    @DisplayName("Should correctly set properties of BInternalCampaign")
    void testBInternalCampaign() {
        // Verificar se os dados foram definidos corretamente
        assertThat(bInternalCampaign.getId()).isEqualTo(1L);
        assertThat(bInternalCampaign.getStationeryType()).isEqualTo(stationeryType);
        assertThat(bInternalCampaign.getOtherItem()).isEqualTo(otherItem);
        assertThat(bInternalCampaign.getBriefing()).isEqualTo(briefing);
        assertThat(bInternalCampaign.getCampaignMotto()).isEqualTo("Innovative Campaign");
    }

    /**
     * Testa o construtor padrão da classe BInternalCampaign.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create a non-null instance with default constructor")
    void testDefaultConstructor() {
        BInternalCampaign campaign = new BInternalCampaign();
        assertThat(campaign).isNotNull();
    }

    /**
     * Testa o método setter e getter para o atributo id.
     * Verifica se o método setId define corretamente o atributo id.
     */
    @Test
    @DisplayName("Should set and get the id correctly")
    void testIdSetterAndGetter() {
        Long newId = 2L;
        bInternalCampaign.setId(newId);
        assertThat(bInternalCampaign.getId()).isEqualTo(newId);
    }

    /**
     * Testa o método setter e getter para o atributo stationeryType.
     * Verifica se o método setStationeryType define corretamente o atributo stationeryType.
     */
    @Test
    @DisplayName("Should set and get the stationeryType correctly")
    void testStationeryTypeSetterAndGetter() {
        StationeryType newStationeryType = new StationeryType();
        newStationeryType.setId(2L);
        newStationeryType.setDescription("Stationery B");
        bInternalCampaign.setStationeryType(newStationeryType);
        assertThat(bInternalCampaign.getStationeryType()).isEqualTo(newStationeryType);
    }

    /**
     * Testa o método setter e getter para o atributo otherItem.
     * Verifica se o método setOtherItem define corretamente o atributo otherItem.
     */
    @Test
    @DisplayName("Should set and get the otherItem correctly")
    void testOtherItemSetterAndGetter() {
        OtherItem newOtherItem = new OtherItem();
        newOtherItem.setId(2L);
        newOtherItem.setDescription("Item C");
        bInternalCampaign.setOtherItem(newOtherItem);
        assertThat(bInternalCampaign.getOtherItem()).isEqualTo(newOtherItem);
    }

    /**
     * Testa o método setter e getter para o atributo briefing.
     * Verifica se o método setBriefing define corretamente o atributo briefing.
     */
    @Test
    @DisplayName("Should set and get the briefing correctly")
    void testBriefingSetterAndGetter() {
        Briefing newBriefing = new Briefing();
        newBriefing.setId(2L);
        bInternalCampaign.setBriefing(newBriefing);
        assertThat(bInternalCampaign.getBriefing()).isEqualTo(newBriefing);
    }

    /**
     * Testa o método setter e getter para o atributo campaignMotto.
     * Verifica se o método setCampaignMotto define corretamente o atributo campaignMotto.
     */
    @Test
    @DisplayName("Should set and get the campaignMotto correctly")
    void testCampaignMottoSetterAndGetter() {
        String newMotto = "New Innovative Campaign";
        bInternalCampaign.setCampaignMotto(newMotto);
        assertThat(bInternalCampaign.getCampaignMotto()).isEqualTo(newMotto);
    }

    /**
     * Testa o método equals da classe BInternalCampaign.
     * Verifica se o método equals retorna true para objetos iguais.
     */
    @Test
    @DisplayName("Should return true for equal objects")
    void testEquals() {
        BInternalCampaign bInternalCampaign2 = new BInternalCampaign();
        bInternalCampaign2.setId(1L);
        bInternalCampaign2.setStationeryType(stationeryType);
        bInternalCampaign2.setOtherItem(otherItem);
        bInternalCampaign2.setBriefing(briefing);
        bInternalCampaign2.setCampaignMotto("Innovative Campaign");

        assertTrue(bInternalCampaign.equals(bInternalCampaign2));
        assertTrue(bInternalCampaign2.equals(bInternalCampaign));
    }

    /**
     * Testa o método equals da classe BInternalCampaign para objetos diferentes.
     * Verifica se o método equals retorna false para objetos diferentes.
     */
    @Test
    @DisplayName("Should return false for different objects")
    void testNotEquals() {
        BInternalCampaign bInternalCampaign2 = new BInternalCampaign();
        bInternalCampaign2.setId(2L);

        assertFalse(bInternalCampaign.equals(bInternalCampaign2));
        assertFalse(bInternalCampaign2.equals(bInternalCampaign));
    }

    /**
     * Testa o método hashCode da classe BInternalCampaign.
     * Verifica se o método hashCode retorna o mesmo valor para objetos iguais.
     */
    @Test
    @DisplayName("Should return same hashCode for equal objects")
    void testHashCode() {
        BInternalCampaign bInternalCampaign2 = new BInternalCampaign();
        bInternalCampaign2.setId(1L);
        bInternalCampaign2.setStationeryType(stationeryType);
        bInternalCampaign2.setOtherItem(otherItem);
        bInternalCampaign2.setBriefing(briefing);
        bInternalCampaign2.setCampaignMotto("Innovative Campaign");

        assertEquals(bInternalCampaign.hashCode(), bInternalCampaign2.hashCode());
    }

    /**
     * Testa o método toString da classe BInternalCampaign.
     * Verifica se o método toString retorna a representação correta da instância.
     */
    @Test
    @DisplayName("Should return correct string representation")
    void testToString() {
        String expectedToString = "BInternalCampaign{" +
                "id=1" +
                ", stationeryType=" + stationeryType +
                ", otherItem=" + otherItem +
                ", briefing=" + briefing +
                ", campaignMotto='Innovative Campaign'" +
                '}';

        assertEquals(expectedToString, bInternalCampaign.toString());
    }
}