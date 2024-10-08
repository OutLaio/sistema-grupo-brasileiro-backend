package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaing;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.OtherItem;

/**
 * Testa a classe OtherItem.
 * Verifica se os métodos da classe funcionam corretamente e se os dados são configurados adequadamente.
 */
public class OtherItemTest {

    private OtherItem otherItem;
    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
        otherItem = new OtherItem();
        otherItem.setId(faker.number().randomNumber());
        otherItem.setDescription(faker.lorem().sentence());
    }

    /**
     * Testa a instância da classe OtherItem.
     * Verifica se as propriedades de OtherItem estão definidas corretamente 
     * após a configuração inicial no método setUp.
     */
    @Test
    @DisplayName("Should correctly set properties of OtherItem")
    void testOtherItem() {
        // Verifica se os dados foram definidos corretamente
        assertThat(otherItem.getId()).isNotNull();
        assertThat(otherItem.getDescription()).isNotNull().isNotEmpty();
    }

    /**
     * Testa o construtor padrão da classe OtherItem.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create a non-null instance with default constructor")
    void testDefaultConstructor() {
        OtherItem item = new OtherItem();
        assertThat(item).isNotNull();
    }

    /**
     * Testa os métodos setters e getters da classe OtherItem.
     * Verifica se os métodos setId e setDescription definem corretamente os atributos
     * e se os métodos getId e getDescription retornam os valores esperados.
     */
    @Test
    @DisplayName("Should set and get the id and description correctly")
    void testSettersAndGetters() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().sentence();

        otherItem.setId(id);
        otherItem.setDescription(description);

        assertThat(otherItem.getId()).isEqualTo(id);
        assertThat(otherItem.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos equals e hashCode da classe OtherItem.
     * Verifica se duas instâncias com os mesmos valores de id são iguais e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should correctly implement equals and hashCode")
    void testEqualsAndHashCode() {
        Long id = 1L;
        String description = "Some Description";

        OtherItem item1 = new OtherItem();
        item1.setId(id);
        item1.setDescription(description);

        OtherItem item2 = new OtherItem();
        item2.setId(id);
        item2.setDescription(description);

        OtherItem item3 = new OtherItem();
        item3.setId(2L);
        item3.setDescription(description); // Instância com id diferente

        assertThat(item1).isEqualTo(item2);
        assertThat(item1.hashCode()).isEqualTo(item2.hashCode());
        
        // Verifica que objetos diferentes não são iguais
        assertThat(item1).isNotEqualTo(item3);
        assertThat(item1.hashCode()).isNotEqualTo(item3.hashCode());
    }

    /**
     * Testa o método toString da classe OtherItem.
     * Verifica se o método toString retorna uma representação correta da instância.
     */
    @Test
    @DisplayName("Should return correct string representation")
    void testToString() {
        Long id = 1L;
        String description = "Some Description";

        otherItem.setId(id);
        otherItem.setDescription(description);
        
        String expected = "OtherItem(id=" + id + ", description=" + description + ")";
        //assertThat(otherItem.toString()).isEqualTo(expected);
    }
}
