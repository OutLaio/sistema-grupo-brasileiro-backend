package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

public class RouteTest {

    private final Faker faker = new Faker();

    /**
     * Testa o construtor padrão da classe Route.
     * Verifica se o construtor padrão cria uma instância não nula da classe.
     */
    @Test
    void testDefaultConstructor() {
        Route route = new Route();
        assertThat(route).isNotNull();
    }

    /**
     * Testa o construtor parametrizado da classe Route.
     * Verifica se o construtor com parâmetros define corretamente os atributos id, bAgencyBoard, companyCity e type.
     */
    @Test
    void testParameterizedConstructor() {
        Long id = faker.number().randomNumber();
        BAgencyBoard bAgencyBoard = new BAgencyBoard(); // Considere usar uma instância real ou mockada, se necessário
        CompanyCity companyCity = new CompanyCity(); // Considere usar uma instância real ou mockada, se necessário
        String type = faker.lorem().word();
        Route route = new Route(id, bAgencyBoard, companyCity, type);

        assertThat(route.getId()).isEqualTo(id);
        assertThat(route.getBAgencyBoard()).isEqualTo(bAgencyBoard);
        assertThat(route.getCompanyCity()).isEqualTo(companyCity);
        assertThat(route.getType()).isEqualTo(type);
    }

    /**
     * Testa os métodos setters e getters da classe Route.
     * Verifica se os métodos setId, setBAgencyBoard, setCompanyCity e setType definem corretamente os atributos
     * e se os métodos getId, getBAgencyBoard, getCompanyCity e getType retornam os valores esperados.
     */
    @Test
    void testSettersAndGetters() {
        Route route = new Route();
        Long id = faker.number().randomNumber();
        BAgencyBoard bAgencyBoard = new BAgencyBoard(); // Considere usar uma instância real ou mockada, se necessário
        CompanyCity companyCity = new CompanyCity(); // Considere usar uma instância real ou mockada, se necessário
        String type = faker.lorem().word();
        route.setId(id);
        route.setBAgencyBoard(bAgencyBoard);
        route.setCompanyCity(companyCity);
        route.setType(type);

        assertThat(route.getId()).isEqualTo(id);
        assertThat(route.getBAgencyBoard()).isEqualTo(bAgencyBoard);
        assertThat(route.getCompanyCity()).isEqualTo(companyCity);
        assertThat(route.getType()).isEqualTo(type);
    }

    /**
     * Testa os métodos equals e hashCode da classe Route.
     * Verifica se duas instâncias com os mesmos valores de id são iguais e se têm o mesmo hashCode.
     */
    @Test
    void testEqualsAndHashCode() {
        Long id = faker.number().randomNumber();
        BAgencyBoard bAgencyBoard = new BAgencyBoard(); // Considere usar uma instância real ou mockada, se necessário
        CompanyCity companyCity = new CompanyCity(); // Considere usar uma instância real ou mockada, se necessário
        String type = faker.lorem().word();
        Route route1 = new Route(id, bAgencyBoard, companyCity, type);
        Route route2 = new Route(id, bAgencyBoard, companyCity, type);

        assertThat(route1).isEqualTo(route2);
        assertThat(route1.hashCode()).isEqualTo(route2.hashCode());
    }

    /**
     * Testa o método toString da classe Route.
     * Verifica se o método toString retorna uma representação correta da instância com o id.
     */
    @Test
    void testToString() {
        Long id = faker.number().randomNumber();
        BAgencyBoard bAgencyBoard = new BAgencyBoard(); // Considere usar uma instância real ou mockada, se necessário
        CompanyCity companyCity = new CompanyCity(); // Considere usar uma instância real ou mockada, se necessário
        String type = faker.lorem().word();
        Route route = new Route(id, bAgencyBoard, companyCity, type);
        String expectedToString = "Route(id=" + id + ")";
        assertThat(route.toString()).contains(expectedToString);
    }
}
