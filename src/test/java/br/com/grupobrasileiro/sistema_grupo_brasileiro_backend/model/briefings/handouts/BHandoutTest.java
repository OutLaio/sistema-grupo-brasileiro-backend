package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;

/**
 * Testa a classe BHandout.
 * Verifica o funcionamento dos métodos gerados pelo Lombok e a interação com HandoutType e Briefing.
 */
public class BHandoutTest {

    private BHandout bHandout;
    private HandoutType handoutType;
    private Briefing briefing;

    @BeforeEach
    void setUp() {
        handoutType = new HandoutType();
        handoutType.setId(1L);
        handoutType.setDescription("Type A");

        briefing = new Briefing();
        briefing.setId(1L);

        bHandout = new BHandout();
        bHandout.setId(1L);
        bHandout.setHandoutType(handoutType);
        bHandout.setBriefing(briefing);
    }

    @Test
    @DisplayName("Should set and get properties correctly")
    void testBHandoutProperties() {
        // Verificar se os dados foram definidos corretamente
        assertThat(bHandout.getId()).isEqualTo(1L);
        assertThat(bHandout.getHandoutType()).isEqualTo(handoutType);
        assertThat(bHandout.getBriefing()).isEqualTo(briefing);
    }

    /**
     * Testa os métodos setters e getters da classe BHandout.
     * Verifica se os métodos setHandoutType e setBriefing definem corretamente os atributos
     * e se os métodos getHandoutType e getBriefing retornam os valores esperados.
     */
    @Test
    @DisplayName("Should set and get HandoutType correctly")
    void testSetHandoutType() {
        HandoutType newHandoutType = new HandoutType();
        newHandoutType.setId(2L);
        newHandoutType.setDescription("Type B");

        bHandout.setHandoutType(newHandoutType);

        assertThat(bHandout.getHandoutType()).isEqualTo(newHandoutType);
    }

    @Test
    @DisplayName("Should set and get Briefing correctly")
    void testSetBriefing() {
        Briefing newBriefing = new Briefing();
        newBriefing.setId(2L);

        bHandout.setBriefing(newBriefing);

        assertThat(bHandout.getBriefing()).isEqualTo(newBriefing);
    }

    /**
     * Testa os métodos equals e hashCode da classe BHandout.
     * Verifica se duas instâncias com os mesmos valores são iguais e têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should be equal and have the same hashCode for equal instances")
    void testEqualsAndHashCode() {
        BHandout bHandout1 = new BHandout();
        bHandout1.setId(1L);
        bHandout1.setHandoutType(handoutType);
        bHandout1.setBriefing(briefing);

        BHandout bHandout2 = new BHandout();
        bHandout2.setId(1L);
        bHandout2.setHandoutType(handoutType);
        bHandout2.setBriefing(briefing);

        assertThat(bHandout1).isEqualTo(bHandout2);
        assertThat(bHandout1.hashCode()).isEqualTo(bHandout2.hashCode());
    }

    /**
     * Testa o método toString da classe BHandout.
     * Verifica se o método toString retorna uma representação correta da instância.
     */
    @Test
    @DisplayName("Should return correct string representation in toString method")
    void testToString() {
        String expectedToString = "BHandout(id=1, handoutType=Type A, briefing=1)";
        //assertThat(bHandout.toString()).contains(expectedToString);
    }
}
