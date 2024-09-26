package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

public class CompanyCityTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe CompanyCity.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create a non-null instance with default constructor")
    void testDefaultConstructor() {
        CompanyCity companyCity = new CompanyCity();
        assertThat(companyCity).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe CompanyCity.
     * Verifica se o construtor com parâmetros define corretamente os atributos id, city e company.
     */
    @Test
    @DisplayName("Should correctly set id, city and company with parameterized constructor")
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
    @DisplayName("Should correctly set and get id, city and company")
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
    @DisplayName("Should be equal and have the same hashCode for equal instances")
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
    @DisplayName("Should return correct string representation in toString method")
    void testToString() {
        Long id = faker.number().randomNumber();
        City city = new City(); 
        Company company = new Company(); 
        CompanyCity companyCity = new CompanyCity(id, city, company);
        String expectedToString = "CompanyCity(id=" + id + ", city=" + city + ", company=" + company + ")";
        assertThat(companyCity.toString()).contains(expectedToString);
    }

    /**
     * Testa o comportamento do método equals quando city é nulo.
     * Verifica se duas instâncias com o mesmo id e company nulo são consideradas iguais.
     */
    @Test
    @DisplayName("Should consider CompanyCities with the same id and null city as equal")
    void testEqualsWithNullCity() {
        Long id = faker.number().randomNumber();
        Company company = new Company(); 
        CompanyCity companyCity1 = new CompanyCity(id, null, company);
        CompanyCity companyCity2 = new CompanyCity(id, null, company);

        assertThat(companyCity1).isEqualTo(companyCity2);
    }

    /**
     * Testa o comportamento do método toString quando a cidade é nula.
     * Verifica se o método toString retorna uma representação correta da instância com cidade nula.
     */
    @Test
    @DisplayName("Should handle null city in toString method")
    void testToStringWithNullCity() {
        Long id = faker.number().randomNumber();
        Company company = new Company(); 
        CompanyCity companyCity = new CompanyCity(id, null, company);
        String expectedToString = "CompanyCity(id=" + id + ", city=null, company=" + company + ")";
        assertThat(companyCity.toString()).isEqualTo(expectedToString);
    }
}
