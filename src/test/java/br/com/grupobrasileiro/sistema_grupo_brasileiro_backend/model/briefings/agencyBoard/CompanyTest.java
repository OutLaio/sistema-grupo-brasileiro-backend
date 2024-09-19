package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;

public class CompanyTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe Company.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    void testDefaultConstructor() {
        Company company = new Company();
        assertThat(company).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe Company.
     * Verifica se o construtor com parâmetros define corretamente os atributos id e name.
     */
    @Test
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
    void testToString() {
        Long id = faker.number().randomNumber();
        String name = faker.company().name();
        Company company = new Company(id, name);
        String expectedToString = "Company(id=" + id + ", name=" + name + ")";
        assertThat(company.toString()).contains(expectedToString);
    }
}
