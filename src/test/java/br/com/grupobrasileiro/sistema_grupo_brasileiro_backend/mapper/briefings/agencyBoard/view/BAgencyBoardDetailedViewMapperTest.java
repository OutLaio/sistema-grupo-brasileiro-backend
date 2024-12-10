package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.AgencyBoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CityView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.OtherRouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.RouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.view.CompaniesBriefingsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.view.MeasurementsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.City;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.OtherRoute;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.RouteCity;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;

class BAgencyBoardDetailedViewMapperTest {

    @InjectMocks
    private BAgencyBoardDetailedViewMapper bAgencyBoardDetailedViewMapper;

    @Mock
    private BAgencyBoardViewMapper bAgencyBoardViewMapper;

    @Mock
    private ProjectViewMapper projectViewMapper;

    @Mock
    private BriefingViewMapper briefingViewMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapWithFullyPopulatedBAgencyBoard() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        Briefing briefing = new Briefing();
        Project project = new Project();
        bAgencyBoard.setBriefing(briefing);
        briefing.setProject(project);

        // Criar as instâncias das classes necessárias
        City city = new City(1L, "City Name");
        Company company = new Company(1L, "Company Name");
        
        // Criar as views necessárias
        CompanyView companyView = new CompanyView(company.getId(), company.getName()); 
        CityView cityView = new CityView(city.getId(), city.getName()); 
        
        // Criar a coleção de CityView
        Set<CityView> cities = new HashSet<>();
        cities.add(cityView);

        // Ajustar os tipos das classes
        BAgencyBoardView bAgencyBoardView = new BAgencyBoardView(
            1L,
            new AgencyBoardTypeView(1L, "Type"),
            new BoardTypeView(1L, "Board Type"),
            new HashSet<RouteView>() {{
                add(new RouteView(1L, companyView, cities, "Route Type")); 
            }},
            new HashSet<OtherRouteView>() {{
                add(new OtherRouteView(1L, "Company", city.getName(), "Other Route Type")); 
            }},
            "Location",
            "Observations"
        );

        // Ajustando BriefingView
        BriefingTypeView briefingTypeView = new BriefingTypeView(1L, "Briefing Type");
        
        // Criar instâncias de MeasurementsView e CompaniesBriefingsView, se necessário
        MeasurementsView measurementsView = null; 
        CompaniesBriefingsView companiesView = null; 

        BriefingView briefingView = new BriefingView(
            1L,
            briefingTypeView,
            LocalDate.now(),
            LocalDate.now().plusDays(30),
            LocalDate.now().plusDays(60), 
            "Detailed Description",
            measurementsView,
            companiesView, 
            null, 
            new HashSet<>()
        );

        // Ajustando a criação do ProjectView com os parâmetros na ordem correta
        ProjectView projectView = new ProjectView(
            1L, // id
            "Project Name", // title
            "Status", // status
            new EmployeeSimpleView(1L, "Client Name", null), // client
            new EmployeeSimpleView(2L, "Collaborator Name", null), // collaborator
            "Briefing Type",
            LocalDate.now()
        );

        when(bAgencyBoardViewMapper.map(bAgencyBoard)).thenReturn(bAgencyBoardView);
        when(briefingViewMapper.map(briefing)).thenReturn(briefingView);
        when(projectViewMapper.map(project)).thenReturn(projectView);

        // Act
        BAgencyBoardDetailedView result = bAgencyBoardDetailedViewMapper.map(bAgencyBoard);

        // Assert
        assertNotNull(result);
        assertEquals(bAgencyBoardView, result.bAgencyBoard());
        assertEquals(briefingView, result.briefing());
        assertEquals(projectView, result.project());

        verify(bAgencyBoardViewMapper).map(bAgencyBoard);
        verify(briefingViewMapper).map(briefing);
        verify(projectViewMapper).map(project);
    }

    @Test
    void testMapWithAllFieldsPopulated() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setId(1L);
        Briefing briefing = new Briefing();
        Project project = new Project();
        briefing.setProject(project);
        bAgencyBoard.setBriefing(briefing);

        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setId(1L);
        agencyBoardType.setDescription("Agency Board Type");
        bAgencyBoard.setAgencyBoardType(agencyBoardType);

        BoardType boardType = new BoardType();
        boardType.setId(1L);
        bAgencyBoard.setBoardType(boardType);

        // Criando instâncias de City e Company
        City city = new City(1L, "City Name");
        Company company = new Company(1L, "Company Name");

        // Criar a coleção de RouteCity, se necessário
        Set<RouteCity> routeCities = new HashSet<>(); 

        // Criar a instância de Route
        Route route = new Route();
        route.setId(1L);
        route.setType("Route Type");
        route.setCompany(company); 
        route.setBAgencyBoard(bAgencyBoard); 
        route.setRouteCities(routeCities); 

        HashSet<Route> routes = new HashSet<>();
        routes.add(route);
        bAgencyBoard.setRoutes(routes);

        OtherRoute otherRoute = new OtherRoute();
        otherRoute.setId(1L);
        otherRoute.setCompany(company.getName());
        otherRoute.setType("Other Route Type");

        HashSet<OtherRoute> otherRoutes = new HashSet<>();
        otherRoutes.add(otherRoute);
        bAgencyBoard.setOtherRoutes(otherRoutes);

        bAgencyBoard.setBoardLocation("Board Location");
        bAgencyBoard.setObservations("Observations");

        BAgencyBoardView bAgencyBoardView = new BAgencyBoardView(
            1L,
            new AgencyBoardTypeView(1L, "Agency Board Type"),
            new BoardTypeView(1L, "Board Type"),
            new HashSet<RouteView>() {{
                // Criando a RouteView
                Set<CityView> cities = new HashSet<>();
                cities.add(new CityView(city.getId(), city.getName())); 
                add(new RouteView(1L, new CompanyView(company.getId(), company.getName()), cities, "Route Type"));
            }},
            new HashSet<OtherRouteView>() {{
                add(new OtherRouteView(1L, company.getName(), city.getName(), "Other Route Type")); 
            }},
            "Board Location",
            "Observations"
        );

        // Ajustando BriefingView
        BriefingTypeView briefingTypeView = new BriefingTypeView(1L, "Briefing Type");

        MeasurementsView measurementsView = null; 
        CompaniesBriefingsView companiesView = null; 

        BriefingView briefingView = new BriefingView(
            1L,
            briefingTypeView,
            LocalDate.now(),
            LocalDate.now().plusDays(30),
            LocalDate.now().plusDays(60), 
            "Detailed Description",
            measurementsView,
            companiesView, 
            null, 
            new HashSet<>()
        );

        // Ajustando a criação do ProjectView com os parâmetros na ordem correta
        ProjectView projectView = new ProjectView(
            1L, // id
            "Project Name", // title
            "Status", // status
            new EmployeeSimpleView(1L, "Client Name", null), // client
            new EmployeeSimpleView(2L, "Collaborator Name", null), // collaborator
            "Briefing Type",
            LocalDate.now()
        );

        when(bAgencyBoardViewMapper.map(bAgencyBoard)).thenReturn(bAgencyBoardView);
        when(briefingViewMapper.map(briefing)).thenReturn(briefingView);
        when(projectViewMapper.map(project)).thenReturn(projectView);

        // Act
        BAgencyBoardDetailedView result = bAgencyBoardDetailedViewMapper.map(bAgencyBoard);

        // Assert
        assertNotNull(result);
        assertEquals(bAgencyBoardView, result.bAgencyBoard());
        assertEquals(briefingView, result.briefing());
        assertEquals(projectView, result.project());

        verify(bAgencyBoardViewMapper).map(bAgencyBoard);
        verify(briefingViewMapper).map(briefing);
        verify(projectViewMapper).map(project);
    }
}