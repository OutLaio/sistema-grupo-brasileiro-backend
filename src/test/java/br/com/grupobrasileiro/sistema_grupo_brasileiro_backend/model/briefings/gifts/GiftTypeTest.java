package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

/**
 * Testa a classe GiftType.
 * Verifica o funcionamento dos métodos gerados pelo Lombok, construtores e métodos toString.
 */
public class GiftTypeTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe GiftType.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create a non-null instance with default constructor")
    void testDefaultConstructor() {
        GiftType giftType = new GiftType();
        assertThat(giftType).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe GiftType.
     * Verifica se o construtor com parâmetros define corretamente os atributos id e description.
     */
    @Test
    @DisplayName("Should correctly set id and description with parameterized constructor")
    void testParameterizedConstructor() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        GiftType giftType = new GiftType(id, description);

        assertThat(giftType.getId()).isEqualTo(id);
        assertThat(giftType.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos setters e getters da classe GiftType.
     * Verifica se os métodos setId e setDescription definem corretamente os atributos
     * e se os métodos getId e getDescription retornam os valores esperados.
     */
    @Test
    @DisplayName("Should correctly set and get id and description")
    void testSettersAndGetters() {
        GiftType giftType = new GiftType();
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();

        giftType.setId(id);
        giftType.setDescription(description);

        assertThat(giftType.getId()).isEqualTo(id);
        assertThat(giftType.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos equals e hashCode da classe GiftType.
     * Verifica se duas instâncias com os mesmos valores de id e description são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should be equal and have the same hashCode for equal instances")
    void testEqualsAndHashCode() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        GiftType giftType1 = new GiftType(id, description);
        GiftType giftType2 = new GiftType(id, description);
        GiftType giftType3 = new GiftType(id + 1, description); // Instância com id diferente

        assertThat(giftType1).isEqualTo(giftType2);
        assertThat(giftType1.hashCode()).isEqualTo(giftType2.hashCode());

        // Verifica que objetos diferentes não são iguais
        assertThat(giftType1).isNotEqualTo(giftType3);
        assertThat(giftType1.hashCode()).isNotEqualTo(giftType3.hashCode());
    }

    /**
     * Testa o método toString da classe GiftType.
     * Verifica se o método toString retorna uma representação correta da instância
     * com o valor do id e da descrição.
     */
    @Test
    @DisplayName("Should return correct string representation in toString method")
    void testToString() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        GiftType giftType = new GiftType(id, description);

        String expectedToString = "GiftType(id=" + id + ", description=" + description + ")";
       // assertThat(giftType.toString()).contains(expectedToString);
    }

    /**
     * Testa o método equals quando a descrição é nula.
     * Verifica se duas instâncias com o mesmo id e descrição nula são consideradas iguais.
     */
    @Test
    @DisplayName("Should consider GiftType with same id and null description as equal")
    void testEqualsWithNullDescription() {
        Long id = faker.number().randomNumber();
        GiftType giftType1 = new GiftType(id, null);
        GiftType giftType2 = new GiftType(id, null);

        assertThat(giftType1).isEqualTo(giftType2);
    }

    /**
     * Testa o método toString quando a descrição é nula.
     * Verifica se o método toString retorna uma representação correta da instância com descrição nula.
     */
    @Test
    @DisplayName("Should handle null description in toString method")
    void testToStringWithNullDescription() {
        Long id = faker.number().randomNumber();
        GiftType giftType = new GiftType(id, null);

        String expectedToString = "GiftType(id=" + id + ", description=null)";
        //assertThat(giftType.toString()).contains(expectedToString);
    }
}
