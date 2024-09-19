package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VersionTest {

    /**
     * Testa o construtor padrão da classe Version.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    void testDefaultConstructor() {
        Version version = new Version();
        assertThat(version).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe Version.
     * Verifica se o construtor com parâmetros define corretamente os atributos id, briefing, numVersion, productLink, clientApprove, supervisorApprove e feedback.
     */
    @Test
    void testParameterizedConstructor() {
        Long id = 1L;

        Briefing briefing = new Briefing();
        briefing.setId(1L);

        Integer numVersion = 2;
        String productLink = "http://example.com/product";
        Boolean clientApprove = true;
        Boolean supervisorApprove = false;
        String feedback = "Looks good.";

        Version version = new Version(id, briefing, numVersion, productLink, clientApprove, supervisorApprove, feedback);

        assertThat(version.getId()).isEqualTo(id);
        assertThat(version.getBriefing()).isEqualTo(briefing);
        assertThat(version.getNumVersion()).isEqualTo(numVersion);
        assertThat(version.getProductLink()).isEqualTo(productLink);
        assertThat(version.getClientApprove()).isEqualTo(clientApprove);
        assertThat(version.getSupervisorApprove()).isEqualTo(supervisorApprove);
        assertThat(version.getFeedback()).isEqualTo(feedback);
    }

    /**
     * Testa os métodos setters e getters da classe Version.
     * Verifica se os métodos setId, setBriefing, setNumVersion, setProductLink, setClientApprove, setSupervisorApprove e setFeedback definem corretamente os atributos
     * e se os métodos getId, getBriefing, getNumVersion, getProductLink, getClientApprove, getSupervisorApprove e getFeedback retornam os valores esperados.
     */
    @Test
    void testSettersAndGetters() {
        Version version = new Version();
        Long id = 1L;

        Briefing briefing = new Briefing();
        briefing.setId(1L);

        Integer numVersion = 2;
        String productLink = "http://example.com/product";
        Boolean clientApprove = true;
        Boolean supervisorApprove = false;
        String feedback = "Looks good.";

        version.setId(id);
        version.setBriefing(briefing);
        version.setNumVersion(numVersion);
        version.setProductLink(productLink);
        version.setClientApprove(clientApprove);
        version.setSupervisorApprove(supervisorApprove);
        version.setFeedback(feedback);

        assertThat(version.getId()).isEqualTo(id);
        assertThat(version.getBriefing()).isEqualTo(briefing);
        assertThat(version.getNumVersion()).isEqualTo(numVersion);
        assertThat(version.getProductLink()).isEqualTo(productLink);
        assertThat(version.getClientApprove()).isEqualTo(clientApprove);
        assertThat(version.getSupervisorApprove()).isEqualTo(supervisorApprove);
        assertThat(version.getFeedback()).isEqualTo(feedback);
    }

    /**
     * Testa os métodos equals e hashCode da classe Version.
     * Verifica se duas instâncias com os mesmos valores de id, briefing, numVersion, productLink, clientApprove, supervisorApprove e feedback são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    void testEqualsAndHashCode() {
        Long id = 1L;

        Briefing briefing = new Briefing();
        briefing.setId(1L);

        Integer numVersion = 2;
        String productLink = "http://example.com/product";
        Boolean clientApprove = true;
        Boolean supervisorApprove = false;
        String feedback = "Looks good.";

        Version version1 = new Version(id, briefing, numVersion, productLink, clientApprove, supervisorApprove, feedback);
        Version version2 = new Version(id, briefing, numVersion, productLink, clientApprove, supervisorApprove, feedback);
        Version version3 = new Version(id + 1, briefing, numVersion, productLink, clientApprove, supervisorApprove, feedback); // Instância com id diferente

        assertThat(version1).isEqualTo(version2);
        assertThat(version1.hashCode()).isEqualTo(version2.hashCode());

        // Verifica que objetos diferentes não são iguais
        assertThat(version1).isNotEqualTo(version3);
        assertThat(version1.hashCode()).isNotEqualTo(version3.hashCode());
    }

    /**
     * Testa o método toString da classe Version.
     * Verifica se o método toString retorna uma representação correta da instância
     * com os valores de id, briefing, numVersion, productLink, clientApprove, supervisorApprove e feedback.
     */
    @Test
    void testToString() {
        Long id = 1L;

        Briefing briefing = new Briefing();
        briefing.setId(1L);

        Integer numVersion = 2;
        String productLink = "http://example.com/product";
        Boolean clientApprove = true;
        Boolean supervisorApprove = false;
        String feedback = "Looks good.";

        Version version = new Version(id, briefing, numVersion, productLink, clientApprove, supervisorApprove, feedback);

        String expectedToString = "Version(id=" + id +
                                  ", briefing=" + briefing +
                                  ", numVersion=" + numVersion +
                                  ", productLink=" + productLink +
                                  ", clientApprove=" + clientApprove +
                                  ", supervisorApprove=" + supervisorApprove +
                                  ", feedback=" + feedback + ")";
        assertThat(version.toString()).contains(expectedToString);
    }
}
