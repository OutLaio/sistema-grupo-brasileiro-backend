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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.AgencyBoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardRegisterView;
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

public class BAgencyBoardRegisterViewMapperTest {

    @Mock
    private BAgencyBoardViewMapper bAgencyBoardViewMapper;

    @Mock
    private ProjectViewMapper projectViewMapper;

    @Mock
    private BriefingViewMapper briefingViewMapper;

    @InjectMocks
    private BAgencyBoardRegisterViewMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa o mapeamento de um BAgencyBoard completo para BAgencyBoardRegisterView.
     * Verifica se todos os componentes são mapeados corretamente.
     */
    @Test
    @DisplayName("Should map complete BAgencyBoard to BAgencyBoardRegisterView")
    void testCompleteBAgencyBoard() {
        // Dados de teste
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        Briefing briefing = new Briefing();
        Project project = new Project();

        // Dados mockados para os mapeadores
        BAgencyBoardView bAgencyBoardView = new BAgencyBoardView(
                1L,
                new AgencyBoardTypeView(1L, "Type1"),
                new BoardTypeView(1L, "Board Type"),
                Set.of(new RouteView(
                        1L,
                        new CompanyView(1L, "Company1"),
                        List.of(new CityView(1L, "City1")),
                        "Type1"
                )),
                Set.of(new OtherRouteView(
                        1L,
                        "Company1",
                        "City1",
                        "Type1"
                )),
                "Location1",
                "Observation1"
        );

        ProjectView projectView = new ProjectView(
                1L,
                "Project1",
                "Active",
                new EmployeeSimpleView(1L, "Client1", 100L),
                new EmployeeSimpleView(2L, "Collaborator1", 200L)
        );

        BriefingView briefingView = new BriefingView(
                1L,
                new BriefingTypeView(1L, "Briefing Type"),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                "Detailed description"
        );

        // Configurando o comportamento do mock
        when(bAgencyBoardViewMapper.map(any(BAgencyBoard.class))).thenReturn(bAgencyBoardView);
        when(projectViewMapper.map(any(Project.class))).thenReturn(projectView);
        when(briefingViewMapper.map(any(Briefing.class))).thenReturn(briefingView);

        // Associando o briefing ao projeto
        briefing.setProject(project);
        bAgencyBoard.setBriefing(briefing);

        // Mapeamento
        BAgencyBoardRegisterView result = mapper.map(bAgencyBoard);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.bAgencyBoardView()).isEqualTo(bAgencyBoardView);
        assertThat(result.projectView()).isEqualTo(projectView);
        assertThat(result.briefingView()).isEqualTo(briefingView);
    }

    /**
     * Testa o mapeamento de um BAgencyBoard com componentes nulos para BAgencyBoardRegisterView.
     * Verifica se os componentes nulos são tratados corretamente.
     */
    @Test
    @DisplayName("Should map BAgencyBoard with null components to BAgencyBoardRegisterView")
    void testBAgencyBoardWithNull() {
        // Dados de teste
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        Briefing briefing = new Briefing();

        // Dados mockados para os mapeadores
        BAgencyBoardView bAgencyBoardView = new BAgencyBoardView(
                1L,
                new AgencyBoardTypeView(1L, "Type1"),
                new BoardTypeView(1L, "Board Type"),
                Set.of(),
                Set.of(),
                null,
                null
        );

        // Configurando o comportamento do mock
        when(bAgencyBoardViewMapper.map(any(BAgencyBoard.class))).thenReturn(bAgencyBoardView);

        // Associando o briefing nulo ao projeto
        briefing.setProject(null);
        bAgencyBoard.setBriefing(briefing);

        // Mapeamento
        BAgencyBoardRegisterView result = mapper.map(bAgencyBoard);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.bAgencyBoardView()).isEqualTo(bAgencyBoardView);
        assertThat(result.projectView()).isNull();
        assertThat(result.briefingView()).isNull();
    }

    /**
     * Testa o mapeamento de um BAgencyBoard vazio para BAgencyBoardRegisterView.
     * Verifica se o resultado é não nulo e que os componentes são nulos.
     */
    @Test
    @DisplayName("Should map empty BAgencyBoard to BAgencyBoardRegisterView with null components")
    void testEmptyBAgencyBoard() {
        // Criar um BAgencyBoard vazio
        BAgencyBoard bAgencyBoard = new BAgencyBoard();

        // Simulando o comportamento do mock para o mapeamento de BAgencyBoardView
        BAgencyBoardView emptyBAgencyBoardView = new BAgencyBoardView(
            null, // ou outro valor apropriado se necessário
            null,
            null,
            Set.of(),
            Set.of(),
            null,
            null
        );

        // Configurando o comportamento do mock
        when(bAgencyBoardViewMapper.map(any(BAgencyBoard.class))).thenReturn(emptyBAgencyBoardView);

        // Mapeamento: chamando o método map com o objeto vazio
        BAgencyBoardRegisterView result = mapper.map(bAgencyBoard);

        // Verificação dos resultados: o resultado não deve ser nulo
        assertThat(result).isNotNull();

        // Verificar se os componentes estão corretamente configurados
        assertThat(result.bAgencyBoardView()).isNotNull(); // Deveria retornar um BAgencyBoardView válido
        assertThat(result.projectView()).isNull(); // Projeto deve ser nulo
        assertThat(result.briefingView()).isNull(); // Briefing deve ser nulo

        // Verifique se os métodos dos mocks foram chamados
        verify(bAgencyBoardViewMapper, times(1)).map(bAgencyBoard);
        verify(projectViewMapper, times(0)).map(any());
        verify(briefingViewMapper, times(0)).map(any());
    }


    /**
     * Testa o mapeamento de um BAgencyBoard com Briefing nulo e Project não nulo para BAgencyBoardRegisterView.
     * Verifica se o mapeamento é realizado corretamente.
     */
    @Test
    @DisplayName("Should map BAgencyBoard with null Briefing and non-null Project to BAgencyBoardRegisterView")
    void testBAgencyBoardWithNullBriefing() {
        // Dados de teste
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        Briefing briefing = new Briefing();
        Project project = new Project();

        // Dados mockados para os mapeadores
        BAgencyBoardView bAgencyBoardView = new BAgencyBoardView(
                1L,
                new AgencyBoardTypeView(1L, "Type1"),
                new BoardTypeView(1L, "Board Type"),
                Set.of(),
                Set.of(),
                null,
                null
        );

        ProjectView projectView = new ProjectView(
                1L,
                "Project1",
                "Active",
                new EmployeeSimpleView(1L, "Client1", 100L),
                new EmployeeSimpleView(2L, "Collaborator1", 200L)
        );

        // Configurando o comportamento do mock
        when(bAgencyBoardViewMapper.map(any(BAgencyBoard.class))).thenReturn(bAgencyBoardView);
        when(projectViewMapper.map(any(Project.class))).thenReturn(projectView);
        when(briefingViewMapper.map(any(Briefing.class))).thenReturn(null);

        // Associando o briefing nulo ao projeto
        briefing.setProject(project);
        bAgencyBoard.setBriefing(briefing);

        // Mapeamento
        BAgencyBoardRegisterView result = mapper.map(bAgencyBoard);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.bAgencyBoardView()).isEqualTo(bAgencyBoardView);
        assertThat(result.projectView()).isEqualTo(projectView);
        assertThat(result.briefingView()).isNull();
    }

    /**
     * Testa o mapeamento de um BAgencyBoard com um Project nulo.
     * Verifica se o resultado é tratado corretamente.
     */
    @Test
    @DisplayName("Should map BAgencyBoard with null Project to BAgencyBoardRegisterView")
    void testBAgencyBoardWithNullProject() {
        // Dados de teste
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        Briefing briefing = new Briefing();

        // Dados mockados para os mapeadores
        BAgencyBoardView bAgencyBoardView = new BAgencyBoardView(
                1L,
                new AgencyBoardTypeView(1L, "Type1"),
                new BoardTypeView(1L, "Board Type"),
                Set.of(),
                Set.of(),
                null,
                null
        );

        // Configurando o comportamento do mock
        when(bAgencyBoardViewMapper.map(any(BAgencyBoard.class))).thenReturn(bAgencyBoardView);
        when(projectViewMapper.map(any(Project.class))).thenReturn(null);
        when(briefingViewMapper.map(any(Briefing.class))).thenReturn(null);

        // Associando o briefing ao projeto
        briefing.setProject(null);
        bAgencyBoard.setBriefing(briefing);

        // Mapeamento
        BAgencyBoardRegisterView result = mapper.map(bAgencyBoard);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.bAgencyBoardView()).isEqualTo(bAgencyBoardView);
        assertThat(result.projectView()).isNull();
        assertThat(result.briefingView()).isNull();
    }
}
