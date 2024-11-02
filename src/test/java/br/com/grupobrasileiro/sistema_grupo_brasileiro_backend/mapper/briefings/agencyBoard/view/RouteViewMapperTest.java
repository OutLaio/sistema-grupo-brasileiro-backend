package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CityView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.RouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.City;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;

public class RouteViewMapperTest {

    @InjectMocks
    private RouteViewMapper routeViewMapper;

    @Mock
    private CompanyCityViewMapper companyCityViewMapper;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    /**
     * Testa o mapeamento de Route para RouteView.
     * Verifica se um Route é corretamente mapeado para um RouteView.
     */
    @Test
    @DisplayName("Should map Route to RouteView correctly")
    void mapRouteToView() {
        Route route = new Route();
        route.setId(faker.number().randomNumber());
        route.setType(faker.lorem().word());

        // Criando um City para o teste
        City city = new City(); 
        city.setId(faker.number().randomNumber());
        city.setName(faker.address().city()); 
        
        // Criando um Company para o teste
        Company company = new Company(); 
        company.setId(faker.number().randomNumber());
        company.setName(faker.company().name());

        // Criando um CompanyCity para o teste
        RouteCity companyCity = new RouteCity();
        companyCity.setId(faker.number().randomNumber());
        companyCity.setCity(city);
        companyCity.setCompany(company);

        // Associando o CompanyCity ao Route
        route.setCompanyCity(companyCity);

        // Criando um CompanyCityView para o mapeamento
        CompanyCityView companyCityView = new CompanyCityView(
                companyCity.getId(),
                new CityView(city.getId(), city.getName()), // Supondo que você tenha um construtor adequado
                new CompanyView(company.getId(), company.getName())
        );

        when(companyCityViewMapper.map(any())).thenReturn(companyCityView);

        // Mapeamento
        RouteView result = routeViewMapper.map(route);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(route.getId());
        assertThat(result.companyCityView()).isEqualTo(companyCityView); 
        assertThat(result.type()).isEqualTo(route.getType());
    }


    /**
     * Testa que o método map retorna null ao receber Route nulo.
     * Verifica se o método lida corretamente com entradas nulas.
     */
    @Test
    @DisplayName("Should return null when mapping null Route")
    void returnNullForNullRoute() {
        RouteView result = routeViewMapper.map(null);
        assertThat(result).isNull();
    }

    /**
     * Testa o mapeamento de Route com CompanyCity nulo para RouteView.
     * Verifica se o método lida corretamente com CompanyCity nulo no Route.
     */
    @Test
    @DisplayName("Should map Route with null CompanyCity to RouteView with null CompanyCity")
    void mapRouteWithNullCompanyCity() {
        Route route = new Route();
        route.setId(faker.number().randomNumber());
        route.setType(faker.lorem().word());
        route.setCompanyCity(null); // Certifique-se de que CompanyCity é nulo

        // Mapeamento
        RouteView result = routeViewMapper.map(route);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(route.getId());
        assertThat(result.companyCityView()).isNull(); 
        assertThat(result.type()).isEqualTo(route.getType());
    }

    /**
     * Testa o mapeamento de Route com ID nulo.
     * Verifica se o método lida corretamente com ID nulo no Route.
     */
    @Test
    @DisplayName("Should map Route with null ID")
    void mapRouteWithNullId() {
        Route route = new Route();
        route.setId(null); // ID nulo
        route.setType(faker.lorem().word());

        // Criando um City para o teste
        City city = new City(); // Supondo que você tenha uma classe City
        city.setId(faker.number().randomNumber());
        city.setName(faker.address().city()); 

        // Criando um Company para o teste
        Company company = new Company();
        company.setId(faker.number().randomNumber());
        company.setName(faker.company().name()); 

        // Criando um CompanyCity para o teste
        RouteCity companyCity = new RouteCity();
        companyCity.setId(faker.number().randomNumber());
        companyCity.setCity(city);
        companyCity.setCompany(company);

        // Associando o CompanyCity ao Route
        route.setCompanyCity(companyCity);

        // Criando um CompanyCityView para o mapeamento
        CompanyCityView companyCityView = new CompanyCityView(
                companyCity.getId(),
                new CityView(city.getId(), city.getName()), 
                new CompanyView(company.getId(), company.getName())
        );

        when(companyCityViewMapper.map(any())).thenReturn(companyCityView);

        // Mapeamento
        RouteView result = routeViewMapper.map(route);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isNull(); // Verifica se o ID é nulo
        assertThat(result.companyCityView()).isEqualTo(companyCityView); // Verifica se o CompanyCityView está correto
        assertThat(result.type()).isEqualTo(route.getType());
    }
}