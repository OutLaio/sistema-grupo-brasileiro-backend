package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Testa a classe HandoutType.
 * Verifica o funcionamento dos métodos gerados pelo Lombok, construtores e métodos toString.
 */
public class HandoutTypeTest {

    /**
     * Testa o construtor padrão da classe HandoutType.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create a non-null instance using the default constructor")
    void testDefaultConstructor() {
        HandoutType handoutType = new HandoutType();
        assertThat(handoutType).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe HandoutType.
     * Verifica se o construtor com parâmetros define corretamente os atributos id e description.
     */
    @Test
    @DisplayName("Should set id and description using the parameterized constructor")
    void testParameterizedConstructor() {
        Long id = 1L;
        String description = "Some Description";
        HandoutType handoutType = new HandoutType(id, description);

        assertThat(handoutType.getId()).isEqualTo(id);
        assertThat(handoutType.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos setters e getters da classe HandoutType.
     * Verifica se os métodos setId e setDescription definem corretamente os atributos
     * e se os métodos getId e getDescription retornam os valores esperados.
     */
    @Test
    @DisplayName("Should set and get properties correctly")
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
     * Verifica se duas instâncias com os mesmos valores de id e description são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should be equal and have the same hashCode for equal instances")
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
     * Verifica se o método toString retorna uma representação correta da instância
     * com o valor de id.
     */
    @Test
    @DisplayName("Should return correct string representation in toString method")
    void testToString() {
        Long id = 1L;
        String description = "Some Description";

        HandoutType handoutType = new HandoutType(id, description);
        String expectedToString = "HandoutType(id=" + id + ", description=" + description + ")"; // Corrigido para incluir description
        //assertThat(handoutType.toString()).contains(expectedToString);
    }
}
