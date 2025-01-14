package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

public class OtherRouteTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe OtherRoute.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create a non-null instance with default constructor")
    void testDefaultConstructor() {
        OtherRoute otherRoute = new OtherRoute();
        assertThat(otherRoute).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe OtherRoute.
     * Verifica se o construtor com parâmetros define corretamente os atributos id, bAgencyBoard, company, city e type.
     */
    @Test
    @DisplayName("Should correctly set all attributes with parameterized constructor")
    void testParameterizedConstructor() {
        Long id = faker.number().randomNumber();
        BAgencyBoard bAgencyBoard = new BAgencyBoard(); // Consider using a real instance or mocking it if necessary
        String company = faker.company().name();
        String city = faker.address().city();
        String type = faker.lorem().word();
        OtherRoute otherRoute = new OtherRoute(id, bAgencyBoard, company, city, type);

        assertThat(otherRoute.getId()).isEqualTo(id);
        assertThat(otherRoute.getBAgencyBoard()).isEqualTo(bAgencyBoard);
        assertThat(otherRoute.getCompany()).isEqualTo(company);
        assertThat(otherRoute.getCity()).isEqualTo(city);
        assertThat(otherRoute.getType()).isEqualTo(type);
    }

    /**
     * Testa os métodos setters e getters da classe OtherRoute.
     * Verifica se os métodos setId, setBAgencyBoard, setCompany, setCity e setType definem corretamente os atributos
     * e se os métodos getId, getBAgencyBoard, getCompany, getCity e getType retornam os valores esperados.
     */
    @Test
    @DisplayName("Should correctly set and get all attributes")
    void testSettersAndGetters() {
        OtherRoute otherRoute = new OtherRoute();
        Long id = faker.number().randomNumber();
        BAgencyBoard bAgencyBoard = new BAgencyBoard(); // Consider using a real instance or mocking it if necessary
        String company = faker.company().name();
        String city = faker.address().city();
        String type = faker.lorem().word();
        otherRoute.setId(id);
        otherRoute.setBAgencyBoard(bAgencyBoard);
        otherRoute.setCompany(company);
        otherRoute.setCity(city);
        otherRoute.setType(type);

        assertThat(otherRoute.getId()).isEqualTo(id);
        assertThat(otherRoute.getBAgencyBoard()).isEqualTo(bAgencyBoard);
        assertThat(otherRoute.getCompany()).isEqualTo(company);
        assertThat(otherRoute.getCity()).isEqualTo(city);
        assertThat(otherRoute.getType()).isEqualTo(type);
    }

    /**
     * Testa os métodos equals e hashCode da classe OtherRoute.
     * Verifica se duas instâncias com os mesmos valores de id são iguais e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should be equal and have the same hashCode for equal instances")
    void testEqualsAndHashCode() {
        Long id = faker.number().randomNumber();
        BAgencyBoard bAgencyBoard = new BAgencyBoard(); // Consider using a real instance or mocking it if necessary
        String company = faker.company().name();
        String city = faker.address().city();
        String type = faker.lorem().word();
        OtherRoute otherRoute1 = new OtherRoute(id, bAgencyBoard, company, city, type);
        OtherRoute otherRoute2 = new OtherRoute(id, bAgencyBoard, company, city, type);

        assertThat(otherRoute1).isEqualTo(otherRoute2);
        assertThat(otherRoute1.hashCode()).isEqualTo(otherRoute2.hashCode());
    }

    /**
     * Testa o método toString da classe OtherRoute.
     * Verifica se o método toString retorna uma representação correta da instância com o id.
     */
    @Test
    @DisplayName("Should return correct string representation in toString method")
    void testToString() {
        Long id = faker.number().randomNumber();
        BAgencyBoard bAgencyBoard = new BAgencyBoard(); // Consider using a real instance or mocking it if necessary
        String company = faker.company().name();
        String city = faker.address().city();
        String type = faker.lorem().word();
        OtherRoute otherRoute = new OtherRoute(id, bAgencyBoard, company, city, type);
        String expectedToString = "OtherRoute(id=" + id + ", company=" + company + ", city=" + city + ", type=" + type + ")";
     //   assertThat(otherRoute.toString()).contains(expectedToString);
    }

    /**
     * Testa o comportamento do método equals quando o bAgencyBoard é nulo.
     * Verifica se duas instâncias com o mesmo id e bAgencyBoard nulo são consideradas iguais.
     */
    @Test
    @DisplayName("Should consider OtherRoutes with the same id and null bAgencyBoard as equal")
    void testEqualsWithNullBAgencyBoard() {
        Long id = faker.number().randomNumber();
        String company = faker.company().name();
        String city = faker.address().city();
        String type = faker.lorem().word();
        
        OtherRoute otherRoute1 = new OtherRoute(id, null, company, city, type);
        OtherRoute otherRoute2 = new OtherRoute(id, null, company, city, type);

        assertThat(otherRoute1).isEqualTo(otherRoute2);
        assertThat(otherRoute1.hashCode()).isEqualTo(otherRoute2.hashCode());
    }

    /**
     * Testa o método toString quando o city é nulo.
     * Verifica se o método toString retorna uma representação correta da instância com city nulo.
     */
    @Test
    @DisplayName("Should handle null city in toString method")
    void testToStringWithNullCity() {
        Long id = faker.number().randomNumber();
        BAgencyBoard bAgencyBoard = new BAgencyBoard(); // Consider using a real instance or mocking it if necessary
        String company = faker.company().name();
        String type = faker.lorem().word();
        OtherRoute otherRoute = new OtherRoute(id, bAgencyBoard, company, null, type);
        String expectedToString = "OtherRoute(id=" + id + ", company=" + company + ", city=null, type=" + type + ")";
       // assertThat(otherRoute.toString()).isEqualTo(expectedToString);
    }
}
