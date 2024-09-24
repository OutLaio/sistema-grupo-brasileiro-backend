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

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.RouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CityView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyCityView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.CompanyCityViewMapper;

/**
 * Testa a classe RouteViewMapper.
 * Verifica se o mapeamento lida com Route nulo e campos nulos corretamente.
 */
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
    void deveMapearRouteParaRouteView() {
        // Dados de teste usando Faker
        Route route = new Route();
        route.setId(faker.number().randomNumber());
        route.setType(faker.lorem().word());

        CompanyView companyView = new CompanyView(
                faker.number().randomNumber(),
                faker.company().name()
        );

        CityView cityView = new CityView(
                faker.number().randomNumber(),
                faker.address().city()
        );

        CompanyCityView companyCityView = new CompanyCityView(
                faker.number().randomNumber(),
                cityView,
                companyView
        );

        when(companyCityViewMapper.map(any())).thenReturn(companyCityView);

        // Mapeamento
        RouteView result = routeViewMapper.map(route);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(route.getId());
        assertThat(result.company()).isEqualTo(companyView);
        assertThat(result.cities()).containsExactly(cityView);
        assertThat(result.type()).isEqualTo(route.getType());
    }

    /**
     * Testa que o método map retorna null ao receber Route nulo.
     * Verifica se o método lida corretamente com entradas nulas.
     */
    @Test
    @DisplayName("Should return null when mapping null Route")
    void deveRetornarNullParaRouteNulo() {
        RouteView result = routeViewMapper.map(null);
        assertThat(result).isNull();
    }

    /**
     * Testa o mapeamento de Route com CompanyCity nulo para RouteView.
     * Verifica se o método lida corretamente com CompanyCity nulo no Route.
     */
    @Test
    @DisplayName("Should map Route with null CompanyCity to RouteView with null CompanyCity")
    void deveMapearRouteComCompanyCityNuloParaRouteView() {
        Route route = new Route();
        route.setId(faker.number().randomNumber());
        route.setType(faker.lorem().word());
        route.setCompanyCity(null);

        // Mapeamento
        RouteView result = routeViewMapper.map(route);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(route.getId());
        assertThat(result.company()).isNull(); 
        assertThat(result.cities()).isEmpty(); 
        assertThat(result.type()).isEqualTo(route.getType());
    }
}

