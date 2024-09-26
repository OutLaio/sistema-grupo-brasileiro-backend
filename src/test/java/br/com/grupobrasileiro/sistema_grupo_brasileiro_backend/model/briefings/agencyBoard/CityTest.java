package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

public class CityTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe City.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create a non-null instance with default constructor")
    void testDefaultConstructor() {
        City city = new City();
        assertThat(city).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe City.
     * Verifica se o construtor com parâmetros define corretamente os atributos id e name.
     */
    @Test
    @DisplayName("Should correctly set id and name with parameterized constructor")
    void testParameterizedConstructor() {
        Long id = faker.number().randomNumber();
        String name = faker.address().city();
        City city = new City(id, name);
        assertThat(city.getId()).isEqualTo(id);
        assertThat(city.getName()).isEqualTo(name);
    }

    /**
     * Testa os métodos setters e getters da classe City.
     * Verifica se os métodos setId e setName definem corretamente os atributos
     * e se os métodos getId e getName retornam os valores esperados.
     */
    @Test
    @DisplayName("Should correctly set and get id and name")
    void testSettersAndGetters() {
        City city = new City();
        Long id = faker.number().randomNumber();
        String name = faker.address().city();
        city.setId(id);
        city.setName(name);

        assertThat(city.getId()).isEqualTo(id);
        assertThat(city.getName()).isEqualTo(name);
    }

    /**
     * Testa os métodos equals e hashCode da classe City.
     * Verifica se duas instâncias com os mesmos valores de id e name são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should be equal and have the same hashCode for equal instances")
    void testEqualsAndHashCode() {
        Long id = faker.number().randomNumber();
        String name = faker.address().city();
        City city1 = new City(id, name);
        City city2 = new City(id, name);

        assertThat(city1).isEqualTo(city2);
        assertThat(city1.hashCode()).isEqualTo(city2.hashCode());
    }

    /**
     * Testa o método toString da classe City.
     * Verifica se o método toString retorna uma representação correta da instância
     * com os valores de id e name.
     */
    @Test
    @DisplayName("Should return correct string representation in toString method")
    void testToString() {
        Long id = faker.number().randomNumber();
        String name = faker.address().city();
        City city = new City(id, name);
        String expectedToString = "City(id=" + id + ", name=" + name + ")";
        assertThat(city.toString()).contains(expectedToString);
    }

    /**
     * Testa o comportamento do método equals quando o nome é nulo.
     * Verifica se duas instâncias com o mesmo id e nome nulo são consideradas iguais.
     */
    @Test
    @DisplayName("Should consider Cities with the same id and null name as equal")
    void testEqualsWithNullName() {
        Long id = faker.number().randomNumber();
        City city1 = new City(id, null);
        City city2 = new City(id, null);

        assertThat(city1).isEqualTo(city2);
    }

    /**
     * Testa o comportamento do método toString quando o nome é nulo.
     * Verifica se o método toString retorna uma representação correta da instância com nome nulo.
     */
    @Test
    @DisplayName("Should handle null name in toString method")
    void testToStringWithNullName() {
        Long id = faker.number().randomNumber();
        City city = new City(id, null);
        String expectedToString = "City(id=" + id + ", name=null)";
        assertThat(city.toString()).isEqualTo(expectedToString);
    }
}
