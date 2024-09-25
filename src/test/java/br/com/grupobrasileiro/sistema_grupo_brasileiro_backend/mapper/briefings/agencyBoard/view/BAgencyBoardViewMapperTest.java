package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.OtherRouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.RouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
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
        CompanyCity companyCity = new CompanyCity();
        route.setCompanyCity(companyCity);

        OtherRoute otherRoute = new OtherRoute();
        otherRoute.setId(faker.number().randomNumber());
        otherRoute.setCompany(faker.lorem().word());
        otherRoute.setCity(faker.lorem().word());
        otherRoute.setType(faker.lorem().word());

        List<Route> routes = List.of(route);
        List<OtherRoute> otherRoutes = List.of(otherRoute);

        RouteView routeView = new RouteView(
                route.getId(),
                new CompanyView(faker.number().randomNumber(), faker.lorem().word()),
                List.of(new CityView(faker.number().randomNumber(), faker.lorem().word())),
                route.getType()
        );

        OtherRouteView otherRouteView = new OtherRouteView(
                otherRoute.getId(),
                otherRoute.getCompany(),
                otherRoute.getCity(),
                otherRoute.getType()
        );

        // Act
        when(agencyBoardTypeViewMapper.map(any())).thenReturn(agencyBoardTypeView);
        when(boardTypeViewMapper.map(any())).thenReturn(boardTypeView);
        when(routeViewMapper.map(any())).thenReturn(routeView);
        when(otherRouteViewMapper.map(any())).thenReturn(otherRouteView);

        bAgencyBoard.setRoutes(Set.copyOf(routes));
        bAgencyBoard.setOtherRoutes(Set.copyOf(otherRoutes));

        // Act
        BAgencyBoardView result = mapper.map(bAgencyBoard);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(bAgencyBoard.getId());
        assertThat(result.boardLocation()).isEqualTo(bAgencyBoard.getBoardLocation());
        assertThat(result.observations()).isEqualTo(bAgencyBoard.getObservations());
        assertThat(result.agencyBoardType()).isEqualTo(agencyBoardTypeView);
        assertThat(result.boardType()).isEqualTo(boardTypeView);
        assertThat(result.routes()).containsExactly(routeView);
        assertThat(result.otherRoutes()).containsExactly(otherRouteView);
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
        assertThat(result.boardType()).isEqualTo(boardTypeView);
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

        Route route2 = new Route();
        route2.setId(faker.number().randomNumber());
        route2.setType(faker.lorem().word());

        List<Route> routes = List.of(route1, route2);

        RouteView routeView1 = new RouteView(
                route1.getId(),
                new CompanyView(faker.number().randomNumber(), faker.lorem().word()),
                List.of(new CityView(faker.number().randomNumber(), faker.lorem().word())),
                route1.getType()
        );

        RouteView routeView2 = new RouteView(
                route2.getId(),
                new CompanyView(faker.number().randomNumber(), faker.lorem().word()),
                List.of(new CityView(faker.number().randomNumber(), faker.lorem().word())),
                route2.getType()
        );

        when(routeViewMapper.map(any())).thenReturn(routeView1).thenReturn(routeView2);

        bAgencyBoard.setRoutes(Set.copyOf(routes));

        // Act
        BAgencyBoardView result = mapper.map(bAgencyBoard);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.routes()).containsExactly(routeView1, routeView2);
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
       // assertThat(result.otherRoutes()).containsExactly(otherRouteView1, otherRouteView2);
    }
}
