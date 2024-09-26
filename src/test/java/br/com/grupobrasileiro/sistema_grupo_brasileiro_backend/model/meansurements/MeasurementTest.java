package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.meansurements;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testa a classe Measurement.
 * Verifica se os métodos da classe funcionam corretamente e se os dados são configurados adequadamente.
 */
public class MeasurementTest {

    private Measurement measurement;

    @BeforeEach
    void setUp() {
        // Inicializa uma nova instância de Measurement antes de cada teste
        measurement = new Measurement();
    }

    /**
     * Testa o construtor padrão da classe Measurement.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create an instance with the default constructor")
    void testDefaultConstructor() {
        assertThat(measurement).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe Measurement.
     * Verifica se o construtor com parâmetros define corretamente os atributos id, briefing, height e length.
     */
    @Test
    @DisplayName("Should set properties correctly with the parameterized constructor")
    void testParameterizedConstructor() {
        Long id = 1L;

        Briefing briefing = new Briefing();
        briefing.setId(1L);

        BigDecimal height = new BigDecimal("1.80");
        BigDecimal length = new BigDecimal("2.50");

        Measurement measurement = new Measurement(id, briefing, height, length);

        assertThat(measurement.getId()).isEqualTo(id);
        assertThat(measurement.getBriefing()).isEqualTo(briefing);
        assertThat(measurement.getHeight()).isEqualTo(height);
        assertThat(measurement.getLength()).isEqualTo(length);
    }

    /**
     * Testa os métodos setters e getters da classe Measurement.
     * Verifica se os métodos setId, setBriefing, setHeight e setLength definem corretamente os atributos
     * e se os métodos getId, getBriefing, getHeight e getLength retornam os valores esperados.
     */
    @Test
    @DisplayName("Should set and get properties correctly")
    void testSettersAndGetters() {
        Long id = 1L;

        Briefing briefing = new Briefing();
        briefing.setId(1L);

        BigDecimal height = new BigDecimal("1.80");
        BigDecimal length = new BigDecimal("2.50");

        measurement.setId(id);
        measurement.setBriefing(briefing);
        measurement.setHeight(height);
        measurement.setLength(length);

        assertThat(measurement.getId()).isEqualTo(id);
        assertThat(measurement.getBriefing()).isEqualTo(briefing);
        assertThat(measurement.getHeight()).isEqualTo(height);
        assertThat(measurement.getLength()).isEqualTo(length);
    }

    /**
     * Testa os métodos equals e hashCode da classe Measurement.
     * Verifica se duas instâncias com os mesmos valores de id, briefing, height e length são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should consider equal instances with the same values")
    void testEqualsAndHashCode() {
        Long id = 1L;

        Briefing briefing = new Briefing();
        briefing.setId(1L);

        BigDecimal height = new BigDecimal("1.80");
        BigDecimal length = new BigDecimal("2.50");

        Measurement measurement1 = new Measurement(id, briefing, height, length);
        Measurement measurement2 = new Measurement(id, briefing, height, length);
        Measurement measurement3 = new Measurement(id + 1, briefing, height, length); // Instância com id diferente

        assertThat(measurement1).isEqualTo(measurement2);
        assertThat(measurement1.hashCode()).isEqualTo(measurement2.hashCode());

        // Verifica que objetos diferentes não são iguais
        assertThat(measurement1).isNotEqualTo(measurement3);
        assertThat(measurement1.hashCode()).isNotEqualTo(measurement3.hashCode());
    }

    /**
     * Testa o método toString da classe Measurement.
     * Verifica se o método toString retorna uma representação correta da instância
     * com os valores de id, briefing, height e length.
     */
    @Test
    @DisplayName("Should return the correct string representation")
    void testToString() {
        Long id = 1L;

        Briefing briefing = new Briefing();
        briefing.setId(1L);

        BigDecimal height = new BigDecimal("1.80");
        BigDecimal length = new BigDecimal("2.50");

        Measurement measurement = new Measurement(id, briefing, height, length);

        String expectedToString = "Measurement(id=" + id +
                                  ", briefing=" + briefing +
                                  ", height=" + height +
                                  ", length=" + length + ")";
        assertThat(measurement.toString()).contains(expectedToString);
    }

    /**
     * Testa o comportamento da classe quando atributos nulos são definidos.
     * Verifica se a classe trata corretamente casos em que valores nulos são atribuídos.
     */
    @Test
    @DisplayName("Should handle null attributes correctly")
    void testNullAttributes() {
        measurement.setId(null);
        measurement.setBriefing(null);
        measurement.setHeight(null);
        measurement.setLength(null);

        assertThat(measurement.getId()).isNull();
        assertThat(measurement.getBriefing()).isNull();
        assertThat(measurement.getHeight()).isNull();
        assertThat(measurement.getLength()).isNull();
    }

    /**
     * Testa a igualdade de Measurement com atributos diferentes.
     * Verifica se dois objetos diferentes não são considerados iguais.
     */
    @Test
    @DisplayName("Should not consider different Measurement instances as equal")
    void testNotEqual() {
        Long id = 1L;
        Briefing briefing1 = new Briefing();
        briefing1.setId(1L);
        Briefing briefing2 = new Briefing();
        briefing2.setId(2L);

        BigDecimal height = new BigDecimal("1.80");
        BigDecimal length = new BigDecimal("2.50");

        Measurement measurement1 = new Measurement(id, briefing1, height, length);
        Measurement measurement2 = new Measurement(id, briefing2, height, length); // briefing diferente

        // Verifica que objetos diferentes não são iguais
        assertThat(measurement1).isNotEqualTo(measurement2);
    }
}
