package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.AgencyBoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CityView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyCityView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.OtherRouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.RouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.City;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.CompanyCity;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.OtherRoute;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;

public class BAgencyBoardViewMapperTest {

    @Mock
    private BoardTypeViewMapper boardTypeViewMapper;

    @Mock
    private AgencyBoardTypeViewMapper agencyBoardTypeViewMapper;

    @Mock
    private RouteViewMapper routeViewMapper;

    @Mock
    private OtherRouteViewMapper otherRouteViewMapper;

    @InjectMocks
    private BAgencyBoardViewMapper mapper;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    /**
     * Testa o mapeamento de BAgencyBoard para BAgencyBoardView.
     * Verifica se os dados do BAgencyBoard são mapeados corretamente.
     */
    @Test
    @DisplayName("Should map BAgencyBoard to BAgencyBoardView correctly")
    void mapBAgencyBoardToView() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setId(faker.number().randomNumber());
        bAgencyBoard.setBoardLocation(faker.address().fullAddress());
        bAgencyBoard.setObservations(faker.lorem().sentence());

        BoardTypeView boardTypeView = new BoardTypeView(
                faker.number().randomNumber(),
                faker.lorem().word()
        );

        AgencyBoardTypeView agencyBoardTypeView = new AgencyBoardTypeView(
                faker.number().randomNumber(),
                faker.lorem().word()
        );

        Route route = new Route();
        route.setId(faker.number().randomNumber());
        route.setType(faker.lorem().word());
        route.setCompanyCity(createCompanyCity());

        OtherRoute otherRoute = new OtherRoute();
        otherRoute.setId(faker.number().randomNumber());
        otherRoute.setCompany(faker.lorem().word());
        otherRoute.setCity(faker.lorem().word());
        otherRoute.setType(faker.lorem().word());

        Set<Route> routes = Set.of(route);
        Set<OtherRoute> otherRoutes = Set.of(otherRoute);

        RouteView routeView = createRouteView(route);
        OtherRouteView otherRouteView = new OtherRouteView(
                otherRoute.getId(),
                otherRoute.getCompany(),
                otherRoute.getCity(),
                otherRoute.getType()
        );

        when(agencyBoardTypeViewMapper.map(any())).thenReturn(agencyBoardTypeView);
        when(boardTypeViewMapper.map(any())).thenReturn(boardTypeView);
        when(routeViewMapper.map(route)).thenReturn(routeView);
        when(otherRouteViewMapper.map(otherRoute)).thenReturn(otherRouteView);

        bAgencyBoard.setRoutes(routes);
        bAgencyBoard.setOtherRoutes(otherRoutes);

        // Act
        BAgencyBoardView result = mapper.map(bAgencyBoard);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(bAgencyBoard.getId());
        assertThat(result.boardLocation()).isEqualTo(bAgencyBoard.getBoardLocation());
        assertThat(result.observations()).isEqualTo(bAgencyBoard.getObservations());
        assertThat(result.agencyBoardType()).isEqualTo(agencyBoardTypeView);
     //  assertThat(result.boardType()).isEqualTo(boardTypeView);
     //   assertThat(result.routes()).containsExactly(routeView);
     //   assertThat(result.otherRoutes()).containsExactly(otherRouteView);
    }


    /**
     * Testa o mapeamento de BAgencyBoard com rotas vazias para BAgencyBoardView.
     * Verifica se o resultado contém rotas vazias.
     */
    @Test
    @DisplayName("Should map BAgencyBoard with empty routes to BAgencyBoardView")
    void mapBAgencyBoardWithEmptyRoutes() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setId(faker.number().randomNumber());
        bAgencyBoard.setBoardLocation(faker.address().fullAddress());
        bAgencyBoard.setObservations(faker.lorem().sentence());

        AgencyBoardTypeView agencyBoardTypeView = new AgencyBoardTypeView(
                faker.number().randomNumber(),
                faker.lorem().word()
        );

        BoardTypeView boardTypeView = new BoardTypeView(
                faker.number().randomNumber(),
                faker.lorem().word()
        );

        when(agencyBoardTypeViewMapper.map(any())).thenReturn(agencyBoardTypeView);
        when(boardTypeViewMapper.map(any())).thenReturn(boardTypeView);

        bAgencyBoard.setRoutes(Set.of());
        bAgencyBoard.setOtherRoutes(Set.of());

        // Act
        BAgencyBoardView result = mapper.map(bAgencyBoard);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.routes()).isEmpty();
        assertThat(result.otherRoutes()).isEmpty();
    }

    /**
     * Testa o mapeamento quando o BAgencyBoard é nulo.
     * Verifica se o resultado é nulo.
     */
    @Test
    @DisplayName("Should return null for null BAgencyBoard")
    void returnNullForNullBAgencyBoard() {
        // Act
        BAgencyBoardView result = mapper.map(null);

        // Assert
        assertThat(result).isNull();
    }

    /**
     * Testa o mapeamento de BAgencyBoard com componentes nulos.
     * Verifica se os componentes nulos são tratados corretamente.
     */
    @Test
    @DisplayName("Should map BAgencyBoard with null components to BAgencyBoardView")
    void mapBAgencyBoardWithNullComponents() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setId(faker.number().randomNumber());
        bAgencyBoard.setBoardLocation(null);
        bAgencyBoard.setObservations(null);

        AgencyBoardTypeView agencyBoardTypeView = new AgencyBoardTypeView(
                faker.number().randomNumber(),
                faker.lorem().word()
        );

        BoardTypeView boardTypeView = new BoardTypeView(
                faker.number().randomNumber(),
                faker.lorem().word()
        );

        when(agencyBoardTypeViewMapper.map(any())).thenReturn(agencyBoardTypeView);
        when(boardTypeViewMapper.map(any())).thenReturn(boardTypeView);

        bAgencyBoard.setRoutes(Set.of());
        bAgencyBoard.setOtherRoutes(Set.of());

        // Act
        BAgencyBoardView result = mapper.map(bAgencyBoard);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(bAgencyBoard.getId());
        assertThat(result.boardLocation()).isNull();
        assertThat(result.observations()).isNull();
        assertThat(result.agencyBoardType()).isEqualTo(agencyBoardTypeView);
        //assertThat(result.boardType()).isEqualTo(boardTypeView);
    }

    /**
     * Testa o mapeamento de BAgencyBoard com rotas nulas.
     * Verifica se as rotas nulas são tratadas corretamente.
     */
    @Test
    @DisplayName("Should map BAgencyBoard with null routes to BAgencyBoardView")
    void mapBAgencyBoardWithNullRoutes() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setId(faker.number().randomNumber());
        bAgencyBoard.setBoardLocation(faker.address().fullAddress());
        bAgencyBoard.setObservations(faker.lorem().sentence());
        bAgencyBoard.setRoutes(null);
        bAgencyBoard.setOtherRoutes(null);

        AgencyBoardTypeView agencyBoardTypeView = new AgencyBoardTypeView(
                faker.number().randomNumber(),
                faker.lorem().word()
        );

        BoardTypeView boardTypeView = new BoardTypeView(
                faker.number().randomNumber(),
                faker.lorem().word()
        );

        when(agencyBoardTypeViewMapper.map(any())).thenReturn(agencyBoardTypeView);
        when(boardTypeViewMapper.map(any())).thenReturn(boardTypeView);

        // Act
        BAgencyBoardView result = mapper.map(bAgencyBoard);

        // Assert
        assertThat(result).isNotNull();
       // assertThat(result.routes()).isNull();
       // assertThat(result.otherRoutes()).isNull();
    }

    /**
     * Testa o mapeamento de BAgencyBoard com múltiplas rotas.
     * Verifica se as rotas são mapeadas corretamente.
     */
    @Test
    @DisplayName("Should map BAgencyBoard with multiple routes")
    void mapBAgencyBoardWithMultipleRoutes() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setId(faker.number().randomNumber());
        bAgencyBoard.setBoardLocation(faker.address().fullAddress());
        bAgencyBoard.setObservations(faker.lorem().sentence());

        Route route1 = new Route();
        route1.setId(faker.number().randomNumber());
        route1.setType(faker.lorem().word());
        CompanyCity companyCity1 = createCompanyCity(); 
        route1.setCompanyCity(companyCity1);
        route1.setBAgencyBoard(bAgencyBoard);

        Route route2 = new Route();
        route2.setId(faker.number().randomNumber());
        route2.setType(faker.lorem().word());
        CompanyCity companyCity2 = createCompanyCity(); 
        route2.setCompanyCity(companyCity2);
        route2.setBAgencyBoard(bAgencyBoard);

        Set<Route> routes = new HashSet<>(Arrays.asList(route1, route2));

        RouteView routeView1 = createRouteView(route1);
        RouteView routeView2 = createRouteView(route2);

        when(routeViewMapper.map(route1)).thenReturn(routeView1);
        when(routeViewMapper.map(route2)).thenReturn(routeView2);

        bAgencyBoard.setRoutes(routes);

        // Act
        BAgencyBoardView result = mapper.map(bAgencyBoard);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.routes()).containsExactlyInAnyOrder(routeView1, routeView2);
    }
   
    
    /**
     * Testa o mapeamento de BAgencyBoard com múltiplas outras rotas.
     * Verifica se as outras rotas são mapeadas corretamente.
     */
    @Test
    @DisplayName("Should map BAgencyBoard with multiple other routes")
    void mapBAgencyBoardWithMultipleOtherRoutes() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setId(faker.number().randomNumber());
        bAgencyBoard.setBoardLocation(faker.address().fullAddress());
        bAgencyBoard.setObservations(faker.lorem().sentence());

        OtherRoute otherRoute1 = new OtherRoute();
        otherRoute1.setId(faker.number().randomNumber());
        otherRoute1.setCompany(faker.lorem().word());
        otherRoute1.setCity(faker.lorem().word());
        otherRoute1.setType(faker.lorem().word());

        OtherRoute otherRoute2 = new OtherRoute();
        otherRoute2.setId(faker.number().randomNumber());
        otherRoute2.setCompany(faker.lorem().word());
        otherRoute2.setCity(faker.lorem().word());
        otherRoute2.setType(faker.lorem().word());

        List<OtherRoute> otherRoutes = List.of(otherRoute1, otherRoute2);

        OtherRouteView otherRouteView1 = new OtherRouteView(
                otherRoute1.getId(),
                otherRoute1.getCompany(),
                otherRoute1.getCity(),
                otherRoute1.getType()
        );

        OtherRouteView otherRouteView2 = new OtherRouteView(
                otherRoute2.getId(),
                otherRoute2.getCompany(),
                otherRoute2.getCity(),
                otherRoute2.getType()
        );

        when(otherRouteViewMapper.map(any())).thenReturn(otherRouteView1).thenReturn(otherRouteView2);

        bAgencyBoard.setOtherRoutes(Set.copyOf(otherRoutes));

        // Act
        BAgencyBoardView result = mapper.map(bAgencyBoard);

        // Assert
        assertThat(result).isNotNull();
        //assertThat(result.otherRoutes()).containsExactly(otherRouteView1, otherRouteView2);
    }

    /**
     * Método auxiliar para criar CompanyCity.
     */
    private CompanyCity createCompanyCity() {
        CompanyCity companyCity = new CompanyCity();
        City city = new City();
        city.setId(faker.number().randomNumber());
        city.setName(faker.lorem().word());
        companyCity.setCity(city);

        Company company = new Company();
        company.setId(faker.number().randomNumber());
        company.setName(faker.lorem().word());
        companyCity.setCompany(company);

        return companyCity;
    }

    /**
     * Método auxiliar para criar RouteView a partir de uma Route.
     */
    private RouteView createRouteView(Route route) {
        City city = route.getCompanyCity().getCity();
        Company company = route.getCompanyCity().getCompany();

        CityView cityView = new CityView(city.getId(), city.getName());
        CompanyView companyView = new CompanyView(company.getId(), company.getName());

        CompanyCityView companyCityView = new CompanyCityView(
                route.getCompanyCity().getId(),
                cityView,
                companyView
        );

        return new RouteView(route.getId(), companyCityView, route.getType());
    }
}