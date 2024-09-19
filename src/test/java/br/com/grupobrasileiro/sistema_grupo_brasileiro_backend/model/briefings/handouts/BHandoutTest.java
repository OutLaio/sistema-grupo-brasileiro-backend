package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;

import org.junit.jupiter.api.BeforeEach;

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
    void testBHandout() {
        // Verificar se os dados foram definidos corretamente
        assertThat(bHandout.getId()).isEqualTo(1L);
        assertThat(bHandout.getHandoutType()).isEqualTo(handoutType);
        assertThat(bHandout.getBriefing()).isEqualTo(briefing);
    }
}
