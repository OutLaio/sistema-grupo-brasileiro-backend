package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testa a classe Briefing.
 * Verifica se os métodos da classe funcionam corretamente e se os dados são configurados adequadamente.
 */
public class BriefingTest {

    private Briefing briefing;

    @BeforeEach
    void setUp() {
        // Inicializa uma nova instância de Briefing antes de cada teste
        briefing = new Briefing();
    }

    /**
     * Testa o construtor padrão da classe Briefing.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create an instance with the default constructor")
    void testDefaultConstructor() {
        assertThat(briefing).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe Briefing.
     * Verifica se o construtor com parâmetros define corretamente os atributos startTime, expectedTime e detailedDescription.
     */
    @Test
    @DisplayName("Should set properties correctly with the parameterized constructor")
    void testParameterizedConstructor() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime expectedTime = LocalDateTime.now().plusDays(10);
        String detailedDescription = "Briefing description";

        briefing.setStartTime(startTime);
        briefing.setExpectedTime(expectedTime);
        briefing.setDetailedDescription(detailedDescription);

        assertThat(briefing.getStartTime()).isEqualTo(startTime);
        assertThat(briefing.getExpectedTime()).isEqualTo(expectedTime);
        assertThat(briefing.getDetailedDescription()).isEqualTo(detailedDescription);
    }

    /**
     * Testa os métodos setters e getters da classe Briefing.
     * Verifica se os métodos setStartTime, setExpectedTime e setDetailedDescription definem corretamente os atributos
     * e se os métodos getStartTime, getExpectedTime e getDetailedDescription retornam os valores esperados.
     */
    @Test
    @DisplayName("Should set and get properties correctly")
    void testSettersAndGetters() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime expectedTime = LocalDateTime.now().plusDays(10);
        String detailedDescription = "Briefing description";

        briefing.setStartTime(startTime);
        briefing.setExpectedTime(expectedTime);
        briefing.setDetailedDescription(detailedDescription);

        assertThat(briefing.getStartTime()).isEqualTo(startTime);
        assertThat(briefing.getExpectedTime()).isEqualTo(expectedTime);
        assertThat(briefing.getDetailedDescription()).isEqualTo(detailedDescription);
    }

    /**
     * Testa os métodos equals e hashCode da classe Briefing.
     * Verifica se duas instâncias com os mesmos valores de startTime, expectedTime e detailedDescription são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should consider equal instances with the same values")
    void testEqualsAndHashCode() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime expectedTime = LocalDateTime.now().plusDays(10);
        String detailedDescription = "Briefing description";

        Briefing briefing1 = new Briefing();
        briefing1.setStartTime(startTime);
        briefing1.setExpectedTime(expectedTime);
        briefing1.setDetailedDescription(detailedDescription);

        Briefing briefing2 = new Briefing();
        briefing2.setStartTime(startTime);
        briefing2.setExpectedTime(expectedTime);
        briefing2.setDetailedDescription(detailedDescription);

        Briefing briefing3 = new Briefing();
        briefing3.setStartTime(startTime.plusDays(1)); // Instância com startTime diferente
        briefing3.setExpectedTime(expectedTime);
        briefing3.setDetailedDescription(detailedDescription);

        assertThat(briefing1).isEqualTo(briefing2);
        assertThat(briefing1.hashCode()).isEqualTo(briefing2.hashCode());

        // Verifica que objetos diferentes não são iguais
        assertThat(briefing1).isNotEqualTo(briefing3);
        assertThat(briefing1.hashCode()).isNotEqualTo(briefing3.hashCode());
    }

    /**
     * Testa o método toString da classe Briefing.
     * Verifica se o método toString retorna uma representação correta da instância
     * com os valores de startTime, expectedTime e detailedDescription.
     */
    @Test
    @DisplayName("Should return the correct string representation")
    void testToString() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime expectedTime = LocalDateTime.now().plusDays(10);
        String detailedDescription = "Briefing description";

        briefing.setStartTime(startTime);
        briefing.setExpectedTime(expectedTime);
        briefing.setDetailedDescription(detailedDescription);

        String expectedToString = "Briefing(id=null, project=null, briefingType=null, startTime=" + startTime + 
                                  ", expectedTime=" + expectedTime + 
                                  ", finishTime=null, detailedDescription=" + detailedDescription +
                                  ", otherCompany=null, versions=[], dialogs=[], measurement=null, " +
                                  "companies=[], printed=null, gift=null, agencyBoard=null, sticker=null, " +
                                  "signpost=null, handout=null, internalCampaign=null)";

        assertThat(briefing.toString()).contains(expectedToString);
    }

    /**
     * Testa o comportamento da classe quando atributos nulos são definidos.
     * Verifica se a classe trata corretamente casos em que valores nulos são atribuídos.
     */
    @Test
    @DisplayName("Should handle null attributes correctly")
    void testNullAttributes() {
        briefing.setStartTime(null);
        briefing.setExpectedTime(null);
        briefing.setDetailedDescription(null);

        assertThat(briefing.getStartTime()).isNull();
        assertThat(briefing.getExpectedTime()).isNull();
        assertThat(briefing.getDetailedDescription()).isNull();
    }

    /**
     * Testa a igualdade de Briefing com atributos diferentes.
     * Verifica se dois objetos diferentes não são considerados iguais.
     */
    @Test
    @DisplayName("Should not consider different Briefing instances as equal")
    void testNotEqual() {
        LocalDateTime startTime1 = LocalDateTime.now();
        LocalDateTime expectedTime1 = LocalDateTime.now().plusDays(10);
        String detailedDescription1 = "Briefing description 1";

        LocalDateTime startTime2 = LocalDateTime.now().plusDays(1);
        LocalDateTime expectedTime2 = LocalDateTime.now().plusDays(15);
        String detailedDescription2 = "Briefing description 2";

        Briefing briefing1 = new Briefing();
        briefing1.setStartTime(startTime1);
        briefing1.setExpectedTime(expectedTime1);
        briefing1.setDetailedDescription(detailedDescription1);

        Briefing briefing2 = new Briefing();
        briefing2.setStartTime(startTime2);
        briefing2.setExpectedTime(expectedTime2);
        briefing2.setDetailedDescription(detailedDescription2);

        // Verifica que objetos diferentes não são iguais
        assertThat(briefing1).isNotEqualTo(briefing2);
    }
}
