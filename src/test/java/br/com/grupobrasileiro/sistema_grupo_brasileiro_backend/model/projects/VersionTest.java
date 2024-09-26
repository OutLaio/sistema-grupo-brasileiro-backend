package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VersionTest {

    /**
     * Testa o construtor padrão da classe Version.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Test Default Constructor")
    void testDefaultConstructor() {
        Version version = new Version();
        assertThat(version).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe Version.
     * Verifica se o construtor com parâmetros define corretamente os atributos id, briefing, numVersion, productLink, clientApprove, supervisorApprove e feedback.
     */
    @Test
    @DisplayName("Test Parameterized Constructor")
    void testParameterizedConstructor() {
        Long id = 1L;
        Briefing briefing = createBriefing(1L);
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
    @DisplayName("Test Setters and Getters")
    void testSettersAndGetters() {
        Version version = new Version();
        Long id = 1L;
        Briefing briefing = createBriefing(1L);
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
    @DisplayName("Test Equals and HashCode")
    void testEqualsAndHashCode() {
        Long id = 1L;
        Briefing briefing = createBriefing(1L);
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
    @DisplayName("Test ToString Method")
    void testToString() {
        Long id = 1L;
        Briefing briefing = createBriefing(1L);
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

    /**
     * Testa o método toString da classe Version com valores nulos.
     * Verifica se o método toString lida corretamente com atributos nulos.
     */
    @Test
    @DisplayName("Test ToString Method with Null Values")
    void testToStringWithNullValues() {
        Version version = new Version();

        String expectedToString = "Version(id=null, briefing=null, numVersion=null, productLink=null, clientApprove=null, supervisorApprove=null, feedback=null)";
        assertThat(version.toString()).isEqualTo(expectedToString);
    }

    /**
     * Testa o método equals da classe Version com valores nulos.
     * Verifica se a comparação com uma instância nula é tratada corretamente.
     */
    @Test
    @DisplayName("Test Equals with Null Values")
    void testEqualsWithNull() {
        Version version = new Version();
        assertThat(version).isNotEqualTo(null);
    }

    /**
     * Testa a criação de duas versões com atributos diferentes.
     * Verifica se as versões com atributos diferentes são tratadas como diferentes.
     */
    @Test
    @DisplayName("Test Different Versions Are Not Equal")
    void testDifferentVersionsAreNotEqual() {
        Long id = 1L;
        Briefing briefing = createBriefing(1L);
        Integer numVersion = 1;
        String productLink = "http://example.com/product";
        Boolean clientApprove = true;
        Boolean supervisorApprove = false;
        String feedback = "Feedback 1";

        Version version1 = new Version(id, briefing, numVersion, productLink, clientApprove, supervisorApprove, feedback);
        Version version2 = new Version(id + 1, briefing, numVersion, productLink, clientApprove, supervisorApprove, "Feedback 2"); // Diferente feedback e id

        assertThat(version1).isNotEqualTo(version2);
    }

    // Método auxiliar para criar um Briefing
    private Briefing createBriefing(Long id) {
        Briefing briefing = new Briefing();
        briefing.setId(id);
        return briefing;
    }
}
