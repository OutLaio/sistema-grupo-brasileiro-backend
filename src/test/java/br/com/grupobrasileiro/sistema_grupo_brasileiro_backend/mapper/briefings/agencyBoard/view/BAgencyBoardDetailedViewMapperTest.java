
package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.AgencyBoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.OtherRouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.RouteView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.BAgencyBoardViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.OtherRoute;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Route;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    void testMapComBAgencyBoardCompleto() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        Briefing briefing = new Briefing();
        bAgencyBoard.setBriefing(briefing);

        BAgencyBoardView bAgencyBoardView = new BAgencyBoardView(1L, null, null, null, null, "Localização", "Observações");
        BriefingTypeView briefingTypeView = new BriefingTypeView(1L, "Tipo de Briefing");
        BriefingView briefingView = new BriefingView(1L, briefingTypeView, LocalDate.now(), LocalDate.now().plusDays(7), null, "Descrição detalhada");
        EmployeeSimpleView client = new EmployeeSimpleView(1L, "Cliente", null);
        EmployeeSimpleView collaborator = new EmployeeSimpleView(2L, "Colaborador", null);
        ProjectView projectView = new ProjectView(1L, "Título do Projeto", "Em Andamento", client, collaborator);

        when(bAgencyBoardViewMapper.map(bAgencyBoard)).thenReturn(bAgencyBoardView);
        when(briefingViewMapper.map(briefing)).thenReturn(briefingView);
        when(projectViewMapper.map(any())).thenReturn(projectView);

        // Act
        BAgencyBoardDetailedView resultado = bAgencyBoardDetailedViewMapper.map(bAgencyBoard);

        // Assert
        assertNotNull(resultado);
        assertEquals(bAgencyBoardView, resultado.bAgencyBoardView());
        assertEquals(projectView, resultado.projectView());
        assertEquals(briefingView, resultado.briefingView());

        verify(bAgencyBoardViewMapper).map(bAgencyBoard);
        verify(briefingViewMapper).map(briefing);
        verify(projectViewMapper).map(any());
    }

    @Test
    void testMapWithNullProjectAndUpdatedBriefingView() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        Briefing briefing = new Briefing();
        bAgencyBoard.setBriefing(briefing);

        BAgencyBoardView bAgencyBoardView = new BAgencyBoardView(1L, null, null, null, null, "Localização", "Observações");
        BriefingTypeView briefingTypeView = new BriefingTypeView(1L, "Tipo de Briefing");
        BriefingView briefingView = new BriefingView(1L, briefingTypeView, LocalDate.now(), LocalDate.now().plusDays(7), null, "Descrição detalhada");

        when(bAgencyBoardViewMapper.map(bAgencyBoard)).thenReturn(bAgencyBoardView);
        when(briefingViewMapper.map(briefing)).thenReturn(briefingView);

        // Act
        BAgencyBoardDetailedView resultado = bAgencyBoardDetailedViewMapper.map(bAgencyBoard);

        // Assert
        assertNotNull(resultado);
        assertEquals(bAgencyBoardView, resultado.bAgencyBoardView());
        assertNull(resultado.projectView());
        assertEquals(briefingView, resultado.briefingView());

        // Verificações adicionais para o BriefingView atualizado
        assertNotNull(resultado.briefingView().id());
        assertNotNull(resultado.briefingView().briefingType());
        assertNotNull(resultado.briefingView().startTime());
        assertNotNull(resultado.briefingView().expectedTime());
        assertNull(resultado.briefingView().finishTime());
        assertNotNull(resultado.briefingView().detailedDescription());

        verify(bAgencyBoardViewMapper).map(bAgencyBoard);
        verifyNoInteractions(projectViewMapper);
        verify(briefingViewMapper).map(briefing);
    }

    @Test
    void testMapComTodosOsCamposPreenchidos() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        BoardType boardType = new BoardType();
        Briefing briefing = new Briefing();
        Set<Route> routes = new HashSet<>();
        Set<OtherRoute> otherRoutes = new HashSet<>();
        
        bAgencyBoard.setId(1L);
        bAgencyBoard.setAgencyBoardType(agencyBoardType);
        bAgencyBoard.setBoardType(boardType);
        bAgencyBoard.setBriefing(briefing);
        bAgencyBoard.setBoardLocation("Localização do quadro");
        bAgencyBoard.setObservations("Observações do quadro");
        bAgencyBoard.setRoutes(routes);
        bAgencyBoard.setOtherRoutes(otherRoutes);
        
        AgencyBoardTypeView agencyBoardTypeView = new AgencyBoardTypeView(1L, "Tipo de quadro de agência");
        BoardTypeView boardTypeView = new BoardTypeView(1L, "Tipo de quadro");
        BAgencyBoardView bAgencyBoardView = new BAgencyBoardView(
            1L,
            agencyBoardTypeView,
            boardTypeView,
            Set.of(),
            Set.of(),
            "Localização do quadro",
            "Observações do quadro"
        );
        ProjectView projectView = new ProjectView(1L, "Título do Projeto", "Em andamento", 
            new EmployeeSimpleView(1L, "Cliente", null), new EmployeeSimpleView(2L, "Colaborador", null));
        BriefingView briefingView = new BriefingView(1L, new BriefingTypeView(1L, "Tipo de Briefing"), 
            LocalDate.now(), LocalDate.now().plusDays(30), null, "Descrição detalhada");
        
        when(bAgencyBoardViewMapper.map(bAgencyBoard)).thenReturn(bAgencyBoardView);
        when(projectViewMapper.map(briefing.getProject())).thenReturn(projectView);
        when(briefingViewMapper.map(briefing)).thenReturn(briefingView);
        
        // Act
        BAgencyBoardDetailedView resultado = bAgencyBoardDetailedViewMapper.map(bAgencyBoard);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(bAgencyBoardView, resultado.bAgencyBoardView());
        assertEquals(projectView, resultado.projectView());
        assertEquals(briefingView, resultado.briefingView());
        
        verify(bAgencyBoardViewMapper).map(bAgencyBoard);
        verify(projectViewMapper).map(briefing.getProject());
        verify(briefingViewMapper).map(briefing);
    }
    
    @Test
    void testMapComCamposNulos() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        
        bAgencyBoard.setId(1L);
        bAgencyBoard.setBoardLocation("Localização do quadro");
        bAgencyBoard.setObservations("Observações do quadro");
        
        BAgencyBoardView bAgencyBoardView = new BAgencyBoardView(
            1L,
            null,
            null,
            Set.of(),
            Set.of(),
            "Localização do quadro",
            "Observações do quadro"
        );
        
        when(bAgencyBoardViewMapper.map(bAgencyBoard)).thenReturn(bAgencyBoardView);
        
        // Act
        BAgencyBoardDetailedView resultado = bAgencyBoardDetailedViewMapper.map(bAgencyBoard);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(bAgencyBoardView, resultado.bAgencyBoardView());
        assertNull(resultado.projectView());
        assertNull(resultado.briefingView());
        
        verify(bAgencyBoardViewMapper).map(bAgencyBoard);
        verifyNoInteractions(projectViewMapper);
        verifyNoInteractions(briefingViewMapper);
    }
}