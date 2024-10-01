package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

    @InjectMocks
    private BAgencyBoardDetailedViewMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveMapearBAgencyBoardComTodosComponentesPresentesParaBAgencyBoardRegisterView() {
        // Dados de teste
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        Briefing briefing = new Briefing();
        Project project = new Project();

        // Dados simulados para os mappers
        BAgencyBoardView bAgencyBoardView = new BAgencyBoardView(
                1L,
                new AgencyBoardTypeView(1L, "Tipo1"),
                new BoardTypeView(1L, "Tipo de Placa"),
                Set.of(new RouteView(
                        1L,
                        new CompanyView(1L, "Empresa1"),
                        List.of(new CityView(1L, "Cidade1")),
                        "Tipo1"
                )),
                Set.of(new OtherRouteView(
                        1L,
                        "Empresa1",
                        "Cidade1",
                        "Tipo1"
                )),
                "Localização1",
                "Observação1"
        );

        ProjectView projectView = new ProjectView(
                1L,
                "Projeto1",
                "Ativo",
                new EmployeeSimpleView(1L, "Cliente1", 100L),
                new EmployeeSimpleView(2L, "Colaborador1", 200L)
        );

        BriefingView briefingView = new BriefingView(
                1L,
                new BriefingTypeView(1L, "Tipo de Briefing"),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                "Descrição detalhada"
        );

        // Configurando o comportamento dos mocks
        when(bAgencyBoardViewMapper.map(any(BAgencyBoard.class))).thenReturn(bAgencyBoardView);
        when(projectViewMapper.map(any(Project.class))).thenReturn(projectView);
        when(briefingViewMapper.map(any(Briefing.class))).thenReturn(briefingView);

        // Associando o briefing ao projeto
        briefing.setProject(project);
        bAgencyBoard.setBriefing(briefing);

        // Mapeamento
        BAgencyBoardDetailedView result = mapper.map(bAgencyBoard);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.bAgencyBoardView()).isEqualTo(bAgencyBoardView);
        assertThat(result.projectView()).isEqualTo(projectView);
        assertThat(result.briefingView()).isEqualTo(briefingView);
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
    void deveMapearBAgencyBoardNuloParaBAgencyBoardRegisterViewNulo() {
        // Dados de teste: instanciando o objeto BAgencyBoard como null
        BAgencyBoard bAgencyBoard = null;

        // Mapeamento: chamando o método map com o valor nulo
        BAgencyBoardDetailedView result = mapper.map(bAgencyBoard);

        // Verificação dos resultados: result deve ser nulo
        assertThat(result).isNull();

        // Verifique se nenhum método dos mocks foi chamado
        verify(bAgencyBoardViewMapper, times(0)).map(any());
        verify(projectViewMapper, times(0)).map(any());
        verify(briefingViewMapper, times(0)).map(any());
    }


    @Test
    void deveMapearBAgencyBoardComBriefingNuloEProjectNaoNuloParaBAgencyBoardRegisterView() {
        // Dados de teste
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        Briefing briefing = new Briefing();
        Project project = new Project();

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

        ProjectView projectView = new ProjectView(
                1L,
                "Projeto1",
                "Ativo",
                new EmployeeSimpleView(1L, "Cliente1", 100L),
                new EmployeeSimpleView(2L, "Colaborador1", 200L)
        );

        // Configurando o comportamento dos mocks
        when(bAgencyBoardViewMapper.map(any(BAgencyBoard.class))).thenReturn(bAgencyBoardView);
        when(projectViewMapper.map(any(Project.class))).thenReturn(projectView);

        // Associando o briefing nulo ao projeto
        briefing.setProject(project);
        bAgencyBoard.setBriefing(null);

        // Mapeamento
        BAgencyBoardDetailedView result = mapper.map(bAgencyBoard);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.bAgencyBoardView()).isEqualTo(bAgencyBoardView);
        assertThat(result.projectView()).isEqualTo(projectView);
        assertThat(result.briefingView()).isNull();
    }
}
