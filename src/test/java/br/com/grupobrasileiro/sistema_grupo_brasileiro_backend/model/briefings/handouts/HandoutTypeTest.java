package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class HandoutTypeTest {

    /**
     * Testa o construtor padrão da classe HandoutType.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    void testDefaultConstructor() {
        HandoutType handoutType = new HandoutType();
        assertThat(handoutType).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe HandoutType.
     * Verifica se o construtor com parâmetros define corretamente os atributos id e description.
     */
    @Test
    void testParameterizedConstructor() {
        Long id = 1L;
        String description = "Some Description";
        HandoutType handoutType = new HandoutType(id, description);

        assertThat(handoutType.getId()).isEqualTo(id);
        assertThat(handoutType.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos setters e getters da classe HandoutType.
     * Verifica se os métodos setId, setDescription definem corretamente os atributos
     * e se os métodos getId e getDescription retornam os valores esperados.
     */
    @Test
    void testSettersAndGetters() {
        HandoutType handoutType = new HandoutType();
        Long id = 1L;
        String description = "Some Description";

        handoutType.setId(id);
        handoutType.setDescription(description);

        assertThat(handoutType.getId()).isEqualTo(id);
        assertThat(handoutType.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos equals e hashCode da classe HandoutType.
     * Verifica se duas instâncias com os mesmos valores de id são iguais e se têm o mesmo hashCode.
     */
    @Test
    void testEqualsAndHashCode() {
        Long id = 1L;
        String description = "Some Description";

        HandoutType handoutType1 = new HandoutType(id, description);
        HandoutType handoutType2 = new HandoutType(id, description);
        HandoutType handoutType3 = new HandoutType(2L, description); // Instância com id diferente

        assertThat(handoutType1).isEqualTo(handoutType2);
        assertThat(handoutType1.hashCode()).isEqualTo(handoutType2.hashCode());

        // Verifica que objetos diferentes não são iguais
        assertThat(handoutType1).isNotEqualTo(handoutType3);
        assertThat(handoutType1.hashCode()).isNotEqualTo(handoutType3.hashCode());
    }

    /**
     * Testa o método toString da classe HandoutType.
     * Verifica se o método toString retorna uma representação correta da instância com o valor de id.
     */
    @Test
    void testToString() {
        Long id = 1L;
        String description = "Some Description";

        HandoutType handoutType = new HandoutType(id, description);
        String expectedToString = "HandoutType(id=" + id + ")";
        assertThat(handoutType.toString()).contains(expectedToString);
    }
}
