package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
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

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

    @Test
    void deveMapearBAgencyBoardParaBAgencyBoardView() {
        // Dados de teste usando Faker
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setId(faker.number().randomNumber());
        bAgencyBoard.setBoardLocation(faker.address().fullAddress());
        bAgencyBoard.setObservations(faker.lorem().sentence());

        // Dados simulados para BoardTypeView e AgencyBoardTypeView
        BoardTypeView boardTypeView = new BoardTypeView(
                faker.number().randomNumber(),
                faker.lorem().word()
        );

        AgencyBoardTypeView agencyBoardTypeView = new AgencyBoardTypeView(
                faker.number().randomNumber(),
                faker.lorem().word()
        );

        // Dados simulados para RouteView e OtherRouteView
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

        // Criando listas simuladas para rotas e outras rotas
        List<Route> routes = List.of(route);
        List<OtherRoute> otherRoutes = List.of(otherRoute);

        // Dados simulados para RouteView e OtherRouteView
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

        // Configurando o comportamento dos mocks
        when(agencyBoardTypeViewMapper.map(any())).thenReturn(agencyBoardTypeView);
        when(boardTypeViewMapper.map(any())).thenReturn(boardTypeView);
        when(routeViewMapper.map(any())).thenReturn(routeView);
       // when(otherRouteViewMapper.map(any())).thenReturn(otherRouteView);

        // Configurando o BAgencyBoard com rotas e outras rotas
        bAgencyBoard.setRoutes(Set.copyOf(routes));
        bAgencyBoard.setOtherRoutes(Set.copyOf(otherRoutes));

        // Mapeamento
        BAgencyBoardView result = mapper.map(bAgencyBoard);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(bAgencyBoard.getId());
        assertThat(result.boardLocation()).isEqualTo(bAgencyBoard.getBoardLocation());
        assertThat(result.observations()).isEqualTo(bAgencyBoard.getObservations());
        assertThat(result.agencyBoardType()).isEqualTo(agencyBoardTypeView);
        assertThat(result.boardType()).isEqualTo(boardTypeView);
        assertThat(result.routes()).containsExactly(routeView);
        assertThat(result.otherRoutes()).containsExactly(otherRouteView);
    }

    @Test
    void deveMapearBAgencyBoardComRotasVaziasParaBAgencyBoardView() {
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

        BAgencyBoardView result = mapper.map(bAgencyBoard);

        assertThat(result).isNotNull();
        assertThat(result.routes()).isEmpty();
        assertThat(result.otherRoutes()).isEmpty();
    }

    @Test
    void deveRetornarNullParaBAgencyBoardNulo() {
        BAgencyBoardView result = mapper.map(null);
        assertThat(result).isNull();
    }

    @Test
    void deveMapearBAgencyBoardComNullNosComponentesParaBAgencyBoardView() {
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

        BAgencyBoardView result = mapper.map(bAgencyBoard);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(bAgencyBoard.getId());
        assertThat(result.boardLocation()).isNull();
        assertThat(result.observations()).isNull();
        assertThat(result.agencyBoardType()).isEqualTo(agencyBoardTypeView);
        assertThat(result.boardType()).isEqualTo(boardTypeView);
    }
}
