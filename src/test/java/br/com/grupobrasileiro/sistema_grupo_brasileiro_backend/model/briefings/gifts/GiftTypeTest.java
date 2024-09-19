package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts;

import static org.assertj.core.api.Assertions.assertThat;
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
    void testDefaultConstructor() {
        GiftType giftType = new GiftType();
        assertThat(giftType).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe GiftType.
     * Verifica se o construtor com parâmetros define corretamente os atributos id e description.
     */
    @Test
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
     * com o valor do id.
     */
    @Test
    void testToString() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        GiftType giftType = new GiftType(id, description);
        String expectedToString = "GiftType(id=" + id + ")";
        assertThat(giftType.toString()).contains(expectedToString);
    }
}
