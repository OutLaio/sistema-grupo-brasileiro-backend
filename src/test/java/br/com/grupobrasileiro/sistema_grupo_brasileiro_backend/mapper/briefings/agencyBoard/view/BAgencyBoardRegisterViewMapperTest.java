package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

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
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

public class BAgencyBoardRegisterViewMapperTest {

    @Mock
    private BAgencyBoardViewMapper bAgencyBoardViewMapper;

    @Mock
    private ProjectViewMapper projectViewMapper;

    @Mock
    private BriefingViewMapper briefingViewMapper;

    @Mock
    private BAgencyBoardDetailedViewMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldMapBAgencyBoardWithNullBriefingAndNonNullProjectToBAgencyBoardRegisterView1() {
        // Test data
        BAgencyBoard bAgencyBoard = new BAgencyBoard();

        // Simulated data for mappers
        BAgencyBoardView bAgencyBoardView = new BAgencyBoardView(
                1L,
                new AgencyBoardTypeView(1L, "Type1"),
                new BoardTypeView(1L, "Board Type"),
                Set.of(),
                Set.of(),
                null,
                null
        );

        // Mock behavior setup
        when(bAgencyBoardViewMapper.map(any(BAgencyBoard.class))).thenReturn(bAgencyBoardView);

        // Setting null briefing to bAgencyBoard
        bAgencyBoard.setBriefing(null);

        // Mapping
        BAgencyBoardDetailedView result = mapper.map(bAgencyBoard);

        // Result verification
        assertThat(result).isNotNull();
        assertThat(result.bAgencyBoardView()).isEqualTo(bAgencyBoardView);
        assertThat(result.projectView()).isNull();
        assertThat(result.briefingView()).isNull();

        // Verify that only bAgencyBoardViewMapper was called
        verify(bAgencyBoardViewMapper, times(1)).map(any());
        verify(projectViewMapper, never()).map(any());
        verify(briefingViewMapper, never()).map(any());
    }

    @Test
    void deveMapearBAgencyBoardComComponentesNulosParaBAgencyBoardRegisterView() {
        // Dados de teste
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        Briefing briefing = new Briefing();

        // Dados simulados para os mappers
        BAgencyBoardView bAgencyBoardView = new BAgencyBoardView(
                1L,
                new AgencyBoardTypeView(1L, "Tipo1"),
                new BoardTypeView(1L, "Tipo de Placa"),
                Set.of(),
                Set.of(),
                null,
                null
        );

        // Configurando o comportamento dos mocks
        when(bAgencyBoardViewMapper.map(any(BAgencyBoard.class))).thenReturn(bAgencyBoardView);

        // Associando o briefing nulo ao projeto
        briefing.setProject(null);
        bAgencyBoard.setBriefing(briefing);

        // Mapeamento
        BAgencyBoardDetailedView result = mapper.map(bAgencyBoard);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.bAgencyBoardView()).isEqualTo(bAgencyBoardView);
        assertThat(result.projectView()).isNull();
        assertThat(result.briefingView()).isNull();
    }

    @Test
    void shouldMapNullBAgencyBoardToNullBAgencyBoardRegisterView() {
        // Configurando o mock para retornar null quando o mapper receber um BAgencyBoard nulo
        when(mapper.map(null)).thenReturn(null);

        // Test data: definindo BAgencyBoard como null
        BAgencyBoard bAgencyBoard = null;

        // Mapping: chamando o map com valor nulo
        BAgencyBoardDetailedView result = mapper.map(bAgencyBoard);

        // Verificação do resultado: result deve ser null
        assertThat(result).isNull();

        // Verifique se nenhum método dos outros mappers foi chamado
        verify(bAgencyBoardViewMapper, never()).map(any());
        verify(projectViewMapper, never()).map(any());
        verify(briefingViewMapper, never()).map(any());
    }



    @Test
    void shouldMapBAgencyBoardWithNullBriefingAndNonNullProjectToBAgencyBoardRegisterView() {
        // Dados simulados para os mappers
        BAgencyBoardView bAgencyBoardView = new BAgencyBoardView(
                1L,
                new AgencyBoardTypeView(1L, "Tipo1"),
                new BoardTypeView(1L, "Tipo de Placa"),
                Set.of(),
                Set.of(),
                null,
                null
        );

        // Configurando o comportamento dos mocks
        when(bAgencyBoardViewMapper.map(any(BAgencyBoard.class))).thenReturn(bAgencyBoardView);

        // Dados de teste: criando um BAgencyBoard válido
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        Briefing briefing = new Briefing();
        // Atribuindo um projeto não nulo
        Project project = new Project(); // Suponha que Project é uma classe válida
        briefing.setProject(project); // Atribuindo um projeto
        bAgencyBoard.setBriefing(briefing); // Associando o briefing ao BAgencyBoard

        // Mapeamento
        BAgencyBoardDetailedView result = mapper.map(bAgencyBoard);

        // Result verification
        assertThat(result).isNotNull(); // Esta verificação falhará se result for nulo
        assertThat(result.bAgencyBoardView()).isEqualTo(bAgencyBoardView);
        assertThat(result.projectView()).isNotNull(); // Verificando se o projectView não é nulo
        assertThat(result.briefingView()).isNull(); // O briefingView deve ser nulo, pois setamos briefing como nulo

        // Verificar que apenas bAgencyBoardViewMapper foi chamado
        verify(bAgencyBoardViewMapper, times(1)).map(any());
        verify(projectViewMapper, never()).map(any());
        verify(briefingViewMapper, never()).map(any());
    }
}