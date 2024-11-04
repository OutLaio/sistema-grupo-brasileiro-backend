package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CityView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.City;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.RouteCity;

public class BAgencyBoardViewMapperTest {
    private final Faker faker = new Faker();

    // Método auxiliar para criar um City
    private City createCity() {
        City city = new City();
        city.setId(faker.number().randomNumber());
        city.setName(faker.address().city());
        return city;
    }

    // Método auxiliar para criar um Company
    private Company createCompany() {
        Company company = new Company();
        company.setId(faker.number().randomNumber());
        company.setName(faker.company().name());
        return company;
    }

    // Método auxiliar para criar uma Route
    private Route createRoute() {
        Route route = new Route();
        route.setId(faker.number().randomNumber());
        route.setCompany(createCompany()); // Associando uma empresa
        route.setType(faker.commerce().department());
        return route;
    }

    // Método auxiliar para criar uma RouteCity
    private RouteCity createRouteCity(Route route, City city) {
        RouteCity routeCity = new RouteCity();
        routeCity.setId(faker.number().randomNumber());
        routeCity.setRoute(route);
        routeCity.setCity(city);
        return routeCity;
    }

    // Teste de mapeamento de Company para CompanyView
    @Test
    @DisplayName("Should map Company to CompanyView correctly")
    void mapCompanyToView() {
        // Arrange
        Company company = createCompany();

        // Act
        CompanyView companyView = new CompanyView(company.getId(), company.getName());

        // Assert
        assertNotNull(companyView);
        assertEquals(company.getId(), companyView.id());
        assertEquals(company.getName(), companyView.name());
    }

    // Teste de mapeamento de City para CityView
    @Test
    @DisplayName("Should map City to CityView correctly")
    void mapCityToView() {
        // Arrange
        City city = createCity();

        // Act
        CityView cityView = new CityView(city.getId(), city.getName());

        // Assert
        assertNotNull(cityView);
        assertEquals(city.getId(), cityView.id());
        assertEquals(city.getName(), cityView.name());
    }

    // Teste de mapeamento de Route para uma representação de visualização
    @Test
    @DisplayName("Should map Route to RouteView correctly")
    void mapRouteToView() {
        // Arrange
        Route route = createRoute();
        City city = createCity();
        RouteCity routeCity = createRouteCity(route, city);
        route.getRouteCities().add(routeCity); 
     
    }


}
