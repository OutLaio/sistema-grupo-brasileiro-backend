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
    @DisplayName("Deve mapear BAgencyBoard para BAgencyBoardView corretamente")
    void deveMapearBAgencyBoardParaBAgencyBoardView() {
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
        // when(otherRouteViewMapper.map(any())).thenReturn(otherRouteView);

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
        // assertThat(result.otherRoutes()).containsExactly(otherRouteView);
    }

    /**
     * Testa o mapeamento de BAgencyBoard com rotas vazias para BAgencyBoardView.
     * Verifica se o resultado contém listas vazias para rotas e outras rotas.
     */
    @Test
    @DisplayName("Deve mapear BAgencyBoard com rotas vazias para BAgencyBoardView")
    void deveMapearBAgencyBoardComRotasVaziasParaBAgencyBoardView() {
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
     * Testa o mapeamento de BAgencyBoard nulo para BAgencyBoardView.
     * Verifica se o resultado é nulo ao tentar mapear um objeto nulo.
     */
    @Test
    @DisplayName("Deve retornar null para BAgencyBoard nulo")
    void deveRetornarNullParaBAgencyBoardNulo() {
        // Act
        BAgencyBoardView result = mapper.map(null);

        // Assert
        assertThat(result).isNull();
    }

    /**
     * Testa o mapeamento de BAgencyBoard com componentes nulos para BAgencyBoardView.
     * Verifica se os campos correspondentes são nulos no resultado.
     */
    @Test
    @DisplayName("Deve mapear BAgencyBoard com null nos componentes para BAgencyBoardView")
    void deveMapearBAgencyBoardComNullNosComponentesParaBAgencyBoardView() {
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
}
