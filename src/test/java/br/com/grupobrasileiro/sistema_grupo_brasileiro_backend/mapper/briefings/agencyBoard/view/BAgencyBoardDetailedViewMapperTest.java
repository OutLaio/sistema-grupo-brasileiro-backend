
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
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.OtherRoute;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;
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

        CityView cityView = new CityView(1L, "City Name");
        CompanyView companyView = new CompanyView(1L, "Company Name");
        CompanyCityView companyCityView = new CompanyCityView(1L, cityView, companyView);

        BAgencyBoardView bAgencyBoardView = new BAgencyBoardView(1L, 
            new AgencyBoardTypeView(1L, "Type"), 
            new BoardTypeView(1L, "Board Type"),
            Set.of(new RouteView(1L, companyCityView, "Route Type")),
            Set.of(new OtherRouteView(1L, "Company", "City", "Other Route Type")),
            "Location", 
            "Observations");

        BriefingTypeView briefingTypeView = new BriefingTypeView(1L, "Briefing Type");
        BriefingView briefingView = new BriefingView(
            1L, 
            briefingTypeView, 
            LocalDate.now(), 
            LocalDate.now().plusDays(30), 
            null, 
            "Detailed Description",
            null, null, null
        );

        ProjectView projectView = new ProjectView(1L, "Project Name", "Status", 
            new EmployeeSimpleView(1L, "Client Name", null),
            new EmployeeSimpleView(2L, "Collaborator Name", null));

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

        RouteCity companyCity = new RouteCity();
        companyCity.setId(1L);

        Route route = new Route();
        route.setId(1L);
        route.setType("Route Type");
        route.setCompanyCity(companyCity);
        route.setBAgencyBoard(bAgencyBoard);  // Definindo o BAgencyBoard na Route

        Set<Route> routes = new HashSet<>();
        routes.add(route);
        bAgencyBoard.setRoutes(routes);

        OtherRoute otherRoute = new OtherRoute();
        otherRoute.setId(1L);
        otherRoute.setCompany("Company");
        otherRoute.setType("Other Route Type");
        otherRoute.setBAgencyBoard(bAgencyBoard);  // Definindo o BAgencyBoard no OtherRoute

        Set<OtherRoute> otherRoutes = new HashSet<>();
        otherRoutes.add(otherRoute);
        bAgencyBoard.setOtherRoutes(otherRoutes);

        bAgencyBoard.setBoardLocation("Board Location");
        bAgencyBoard.setObservations("Observations");

        CityView cityView = new CityView(1L, "City Name");
        CompanyView companyView = new CompanyView(1L, "Company Name");
        CompanyCityView companyCityView = new CompanyCityView(1L, cityView, companyView);

        BAgencyBoardView bAgencyBoardView = new BAgencyBoardView(
            1L,
            new AgencyBoardTypeView(1L, "Agency Board Type"),
            new BoardTypeView(1L, "Board Type"),
            Set.of(new RouteView(1L, companyCityView, "Route Type")),
            Set.of(new OtherRouteView(1L, "Company", "City", "Other Route Type")),
            "Board Location",
            "Observations"
        );

        BriefingView briefingView = new BriefingView(
            1L,
            new BriefingTypeView(1L, "Briefing Type"),
            LocalDate.now(),
            LocalDate.now().plusDays(30),
            null,
            "Detailed Description",
            null, null, null
        );

        ProjectView projectView = new ProjectView(1L, "Project Name", "Status", 
            new EmployeeSimpleView(1L, "Client Name", null),
            new EmployeeSimpleView(2L, "Collaborator Name", null));

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