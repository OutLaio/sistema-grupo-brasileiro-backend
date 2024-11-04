package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

public class RouteTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe Route.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    @DisplayName("Should create a non-null instance with default constructor")
    void testDefaultConstructor() {
        Route route = new Route();
        assertThat(route).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe Route.
     * Verifica se o construtor com parâmetros define corretamente os atributos id, bAgencyBoard, company e type.
     */
    @Test
    @DisplayName("Should correctly set all attributes with parameterized constructor")
    void testParameterizedConstructor() {
        Long id = faker.number().randomNumber();
        BAgencyBoard bAgencyBoard = new BAgencyBoard(); 
        Company company = new Company(); // Corrigido para usar Company em vez de CompanyCity
        String type = faker.lorem().word();
        Route route = new Route(id, bAgencyBoard, new HashSet<>(), company, type); // Ajustado para incluir routeCities

        assertThat(route.getId()).isEqualTo(id);
        assertThat(route.getBAgencyBoard()).isEqualTo(bAgencyBoard);
        assertThat(route.getCompany()).isEqualTo(company); // Corrigido para usar getCompany
        assertThat(route.getType()).isEqualTo(type);
    }

    /**
     * Testa os métodos setters e getters da classe Route.
     * Verifica se os métodos setId, setBAgencyBoard, setCompany e setType definem corretamente os atributos
     * e se os métodos getId, getBAgencyBoard, getCompany e getType retornam os valores esperados.
     */
    @Test
    @DisplayName("Should correctly set and get all attributes")
    void testSettersAndGetters() {
        Route route = new Route();
        Long id = faker.number().randomNumber();
        BAgencyBoard bAgencyBoard = new BAgencyBoard(); 
        Company company = new Company(); // Corrigido para usar Company em vez de CompanyCity
        String type = faker.lorem().word();
        route.setId(id);
        route.setBAgencyBoard(bAgencyBoard);
        route.setCompany(company); // Corrigido para usar setCompany
        route.setType(type);

        assertThat(route.getId()).isEqualTo(id);
        assertThat(route.getBAgencyBoard()).isEqualTo(bAgencyBoard);
        assertThat(route.getCompany()).isEqualTo(company); // Corrigido para usar getCompany
        assertThat(route.getType()).isEqualTo(type);
    }

    /**
     * Testa os métodos equals e hashCode da classe Route.
     * Verifica se duas instâncias com os mesmos valores de id, bAgencyBoard e company são iguais e se têm o mesmo hashCode.
     */
    @Test
    @DisplayName("Should be equal and have the same hashCode for equal instances")
    void testEqualsAndHashCode() {
        Long id = faker.number().randomNumber();
        BAgencyBoard bAgencyBoard = new BAgencyBoard(); 
        Company company = new Company(); // Corrigido para usar Company em vez de CompanyCity
        String type = faker.lorem().word();
        Route route1 = new Route(id, bAgencyBoard, new HashSet<>(), company, type);
        Route route2 = new Route(id, bAgencyBoard, new HashSet<>(), company, type);

        assertThat(route1).isEqualTo(route2);
        assertThat(route1.hashCode()).isEqualTo(route2.hashCode());
    }

    /**
     * Testa o método toString da classe Route.
     * Verifica se o método toString retorna uma representação correta da instância com o id.
     */
    @Test
    @DisplayName("Should return correct string representation in toString method")
    void testToString() {
        Long id = faker.number().randomNumber();
        BAgencyBoard bAgencyBoard = new BAgencyBoard(); 
        Company company = new Company(); // Corrigido para usar Company em vez de CompanyCity
        String type = faker.lorem().word();
        Route route = new Route(id, bAgencyBoard, new HashSet<>(), company, type);
        String expectedToString = "Route(id=" + id + ", type=" + type + ", company=" + company + ")"; // Ajustado para incluir company
        assertThat(route.toString()).contains(expectedToString);
    }

    /**
     * Testa o comportamento do método equals quando bAgencyBoard é nulo.
     * Verifica se duas instâncias com o mesmo id e bAgencyBoard nulo são consideradas iguais.
     */
    @Test
    @DisplayName("Should consider Routes with the same id and null bAgencyBoard as equal")
    void testEqualsWithNullBAgencyBoard() {
        Long id = faker.number().randomNumber();
        Company company = new Company(); // Corrigido para usar Company em vez de CompanyCity
        String type = faker.lorem().word();
        Route route1 = new Route(id, null, new HashSet<>(), company, type);
        Route route2 = new Route(id, null, new HashSet<>(), company, type);

        assertThat(route1).isEqualTo(route2);
    }

    /**
     * Testa o método toString quando company é nulo.
     * Verifica se o método toString retorna uma representação correta da instância com company nulo.
     */
    @Test
    @DisplayName("Should handle null company in toString method")
    void testToStringWithNullCompany() {
        Long id = faker.number().randomNumber();
        BAgencyBoard bAgencyBoard = new BAgencyBoard(); 
        String type = faker.lorem().word();
        Route route = new Route(id, bAgencyBoard, new HashSet<>(), null, type);
        String expectedToString = "Route(id=" + id + ", type=" + type + ", company=null)"; // Ajustado para incluir company
        assertThat(route.toString()).isEqualTo(expectedToString);
    }
}