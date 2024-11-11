package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.RouteCityRepository;

public class RouteCityTest {

    @Mock
    private RouteCityRepository routeCityRepository;

    @InjectMocks
    private RouteCity routeCity;

    private City city;
    private Route route;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        city = new City();
        city.setName("Test City");

        route = new Route();
        route.setType("Urban");
    }

    @Test
    void testCreateRouteCity() {
        RouteCity routeCity = new RouteCity();
        routeCity.setCity(city);
        routeCity.setRoute(route);

        when(routeCityRepository.save(any(RouteCity.class))).thenReturn(routeCity);

        RouteCity savedRouteCity = routeCityRepository.save(routeCity);

        assertThat(savedRouteCity).isNotNull();
        assertThat(savedRouteCity.getCity()).isEqualTo(city);
        assertThat(savedRouteCity.getRoute()).isEqualTo(route);
        verify(routeCityRepository, times(1)).save(routeCity);
    }

    @Test
    void testCreateRouteCityWithNullCity() {
        RouteCity routeCity = new RouteCity();
        routeCity.setRoute(route);

        when(routeCityRepository.save(any(RouteCity.class))).thenReturn(routeCity);

        RouteCity savedRouteCity = routeCityRepository.save(routeCity);

        assertThat(savedRouteCity).isNotNull();
        assertThat(savedRouteCity.getCity()).isNull();
        assertThat(savedRouteCity.getRoute()).isEqualTo(route);
        verify(routeCityRepository, times(1)).save(routeCity);
    }
    
    @Test
    void testCreateRouteCityWithNullRoute() {
        RouteCity routeCity = new RouteCity();
        routeCity.setCity(city);

        when(routeCityRepository.save(any(RouteCity.class))).thenReturn(routeCity);

        RouteCity savedRouteCity = routeCityRepository.save(routeCity);

        assertThat(savedRouteCity).isNotNull();
        assertThat(savedRouteCity.getCity()).isEqualTo(city);
        assertThat(savedRouteCity.getRoute()).isNull();
        verify(routeCityRepository, times(1)).save(routeCity);
    }

    @Test
    void testUpdateRouteCity() {
        RouteCity existingRouteCity = new RouteCity();
        existingRouteCity.setCity(city);
        existingRouteCity.setRoute(route);

        when(routeCityRepository.save(existingRouteCity)).thenReturn(existingRouteCity);

        RouteCity updatedRouteCity = routeCityRepository.save(existingRouteCity);

        assertThat(updatedRouteCity).isNotNull();
        assertThat(updatedRouteCity.getCity()).isEqualTo(city);
        assertThat(updatedRouteCity.getRoute()).isEqualTo(route);
        verify(routeCityRepository, times(1)).save(existingRouteCity);
    }

    @Test
    void testDeleteRouteCity() {
        RouteCity routeCityToDelete = new RouteCity();
        routeCityToDelete.setCity(city);
        routeCityToDelete.setRoute(route);

        routeCityRepository.delete(routeCityToDelete);

        verify(routeCityRepository, times(1)).delete(routeCityToDelete);
    }
}