package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

public class CityTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe City.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    void testDefaultConstructor() {
        City city = new City();
        assertThat(city).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe City.
     * Verifica se o construtor com parâmetros define corretamente os atributos id e name.
     */
    @Test
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
    void testToString() {
        Long id = faker.number().randomNumber();
        String name = faker.address().city();
        City city = new City(id, name);
        String expectedToString = "City(id=" + id + ", name=" + name + ")";
        assertThat(city.toString()).contains(expectedToString);
    }
}
