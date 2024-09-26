package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

public class CompanyTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe Company.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create a non-null instance with default constructor")
    void testDefaultConstructor() {
        Company company = new Company();
        assertThat(company).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe Company.
     * Verifica se o construtor com parâmetros define corretamente os atributos id e name.
     */
    @Test
    @DisplayName("Should correctly set id and name with parameterized constructor")
    void testParameterizedConstructor() {
        Long id = faker.number().randomNumber();
        String name = faker.company().name();
        Company company = new Company(id, name);
        assertThat(company.getId()).isEqualTo(id);
        assertThat(company.getName()).isEqualTo(name);
    }

    /**
     * Testa os métodos setters e getters da classe Company.
     * Verifica se os métodos setId e setName definem corretamente os atributos
     * e se os métodos getId e getName retornam os valores esperados.
     */
    @Test
    @DisplayName("Should correctly set and get id and name")
    void testSettersAndGetters() {
        Company company = new Company();
        Long id = faker.number().randomNumber();
        String name = faker.company().name();
        company.setId(id);
        company.setName(name);

        assertThat(company.getId()).isEqualTo(id);
        assertThat(company.getName()).isEqualTo(name);
    }

    /**
     * Testa os métodos equals e hashCode da classe Company.
     * Verifica se duas instâncias com os mesmos valores de id e name são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should be equal and have the same hashCode for equal instances")
    void testEqualsAndHashCode() {
        Long id = faker.number().randomNumber();
        String name = faker.company().name();
        Company company1 = new Company(id, name);
        Company company2 = new Company(id, name);

        assertThat(company1).isEqualTo(company2);
        assertThat(company1.hashCode()).isEqualTo(company2.hashCode());
    }

    /**
     * Testa o método toString da classe Company.
     * Verifica se o método toString retorna uma representação correta da instância
     * com os valores de id e name.
     */
    @Test
    @DisplayName("Should return correct string representation in toString method")
    void testToString() {
        Long id = faker.number().randomNumber();
        String name = faker.company().name();
        Company company = new Company(id, name);
        String expectedToString = "Company(id=" + id + ", name=" + name + ")";
        assertThat(company.toString()).contains(expectedToString);
    }

    /**
     * Testa o comportamento do método equals quando o nome é nulo.
     * Verifica se duas instâncias com o mesmo id e nome nulo são consideradas iguais.
     */
    @Test
    @DisplayName("Should consider Companies with the same id and null name as equal")
    void testEqualsWithNullName() {
        Long id = faker.number().randomNumber();
        Company company1 = new Company(id, null);
        Company company2 = new Company(id, null);

        assertThat(company1).isEqualTo(company2);
    }

    /**
     * Testa o comportamento do método toString quando o nome é nulo.
     * Verifica se o método toString retorna uma representação correta da instância com nome nulo.
     */
    @Test
    @DisplayName("Should handle null name in toString method")
    void testToStringWithNullName() {
        Long id = faker.number().randomNumber();
        Company company = new Company(id, null);
        String expectedToString = "Company(id=" + id + ", name=null)";
        assertThat(company.toString()).isEqualTo(expectedToString);
    }
}
