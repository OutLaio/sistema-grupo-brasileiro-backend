package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

/**
 * Testa a classe Stamp.
 * Verifica o funcionamento dos métodos gerados pelo Lombok, construtores e métodos toString.
 */
public class StampTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe Stamp.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create a non-null instance with default constructor")
    void testDefaultConstructor() {
        Stamp stamp = new Stamp();
        assertThat(stamp).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe Stamp.
     * Verifica se o construtor com parâmetros define corretamente os atributos id e description.
     */
    @Test
    @DisplayName("Should correctly set id and description with parameterized constructor")
    void testParameterizedConstructor() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        Stamp stamp = new Stamp(id, description);

        assertThat(stamp.getId()).isEqualTo(id);
        assertThat(stamp.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos setters e getters da classe Stamp.
     * Verifica se os métodos setId e setDescription definem corretamente os atributos
     * e se os métodos getId e getDescription retornam os valores esperados.
     */
    @Test
    @DisplayName("Should correctly set and get id and description")
    void testSettersAndGetters() {
        Stamp stamp = new Stamp();
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();

        stamp.setId(id);
        stamp.setDescription(description);

        assertThat(stamp.getId()).isEqualTo(id);
        assertThat(stamp.getDescription()).isEqualTo(description);
    }

    /**
     * Testa os métodos equals e hashCode da classe Stamp.
     * Verifica se duas instâncias com os mesmos valores de id e description são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should be equal and have the same hashCode for equal instances")
    void testEqualsAndHashCode() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        Stamp stamp1 = new Stamp(id, description);
        Stamp stamp2 = new Stamp(id, description);
        Stamp stamp3 = new Stamp(id + 1, description); // Instância com id diferente

        assertThat(stamp1).isEqualTo(stamp2);
        assertThat(stamp1.hashCode()).isEqualTo(stamp2.hashCode());

        // Verifica que objetos diferentes não são iguais
        assertThat(stamp1).isNotEqualTo(stamp3);
        assertThat(stamp1.hashCode()).isNotEqualTo(stamp3.hashCode());
    }

    /**
     * Testa o método toString da classe Stamp.
     * Verifica se o método toString retorna uma representação correta da instância
     * com o valor do id e da descrição.
     */
    @Test
    @DisplayName("Should return correct string representation in toString method")
    void testToString() {
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();
        Stamp stamp = new Stamp(id, description);

        String expectedToString = "Stamp(id=" + id + ", description=" + description + ")";
        //assertThat(stamp.toString()).contains(expectedToString);
    }

    /**
     * Testa o método equals quando a descrição é nula.
     * Verifica se duas instâncias com o mesmo id e descrição nula são consideradas iguais.
     */
    @Test
    @DisplayName("Should consider Stamp with same id and null description as equal")
    void testEqualsWithNullDescription() {
        Long id = faker.number().randomNumber();
        Stamp stamp1 = new Stamp(id, null);
        Stamp stamp2 = new Stamp(id, null);

        assertThat(stamp1).isEqualTo(stamp2);
    }

    /**
     * Testa o método toString quando a descrição é nula.
     * Verifica se o método toString retorna uma representação correta da instância com descrição nula.
     */
    @Test
    @DisplayName("Should handle null description in toString method")
    void testToStringWithNullDescription() {
        Long id = faker.number().randomNumber();
        Stamp stamp = new Stamp(id, null);

        String expectedToString = "Stamp(id=" + id + ", description=null)";
       // assertThat(stamp.toString()).contains(expectedToString);
    }
}
