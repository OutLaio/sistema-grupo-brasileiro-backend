package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.City;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.CompanyCity;

public class CompanyCityTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe CompanyCity.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    void testDefaultConstructor() {
        CompanyCity companyCity = new CompanyCity();
        assertThat(companyCity).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe CompanyCity.
     * Verifica se o construtor com parâmetros define corretamente os atributos id, city e company.
     */
    @Test
    void testParameterizedConstructor() {
        Long id = faker.number().randomNumber();
        City city = new City(); // Consider using a real instance or mocking it if necessary
        Company company = new Company(); // Consider using a real instance or mocking it if necessary
        CompanyCity companyCity = new CompanyCity(id, city, company);

        assertThat(companyCity.getId()).isEqualTo(id);
        assertThat(companyCity.getCity()).isEqualTo(city);
        assertThat(companyCity.getCompany()).isEqualTo(company);
    }

    /**
     * Testa os métodos setters e getters da classe CompanyCity.
     * Verifica se os métodos setId, setCity e setCompany definem corretamente os atributos
     * e se os métodos getId, getCity e getCompany retornam os valores esperados.
     */
    @Test
    void testSettersAndGetters() {
        CompanyCity companyCity = new CompanyCity();
        Long id = faker.number().randomNumber();
        City city = new City(); // Consider using a real instance or mocking it if necessary
        Company company = new Company(); // Consider using a real instance or mocking it if necessary
        companyCity.setId(id);
        companyCity.setCity(city);
        companyCity.setCompany(company);

        assertThat(companyCity.getId()).isEqualTo(id);
        assertThat(companyCity.getCity()).isEqualTo(city);
        assertThat(companyCity.getCompany()).isEqualTo(company);
    }

    /**
     * Testa os métodos equals e hashCode da classe CompanyCity.
     * Verifica se duas instâncias com os mesmos valores de id, city e company são iguais
     * e se têm o mesmo hashCode.
     */
    @Test
    void testEqualsAndHashCode() {
        Long id = faker.number().randomNumber();
        City city = new City(); // Consider using a real instance or mocking it if necessary
        Company company = new Company(); // Consider using a real instance or mocking it if necessary
        CompanyCity companyCity1 = new CompanyCity(id, city, company);
        CompanyCity companyCity2 = new CompanyCity(id, city, company);

        assertThat(companyCity1).isEqualTo(companyCity2);
        assertThat(companyCity1.hashCode()).isEqualTo(companyCity2.hashCode());
    }

    /**
     * Testa o método toString da classe CompanyCity.
     * Verifica se o método toString retorna uma representação correta da instância
     * com os valores de id, city e company.
     */
    @Test
    void testToString() {
        Long id = faker.number().randomNumber();
        City city = new City(); // Consider using a real instance or mocking it if necessary
        Company company = new Company(); // Consider using a real instance or mocking it if necessary
        CompanyCity companyCity = new CompanyCity(id, city, company);
        String expectedToString = "CompanyCity(id=" + id + ", city=" + city + ", company=" + company + ")";
        assertThat(companyCity.toString()).contains(expectedToString);
    }
}
