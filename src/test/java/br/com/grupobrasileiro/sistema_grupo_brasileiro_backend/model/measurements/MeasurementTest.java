package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.measurements;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;

import static org.assertj.core.api.Assertions.assertThat;

public class MeasurementTest {

    /**
     * Testa o construtor padrão da classe Measurement.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    void testDefaultConstructor() {
        Measurement measurement = new Measurement();
        assertThat(measurement).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe Measurement.
     * Verifica se o construtor com parâmetros define corretamente os atributos id, briefing, height e length.
     */
    @Test
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
    void testSettersAndGetters() {
        Measurement measurement = new Measurement();
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
}
