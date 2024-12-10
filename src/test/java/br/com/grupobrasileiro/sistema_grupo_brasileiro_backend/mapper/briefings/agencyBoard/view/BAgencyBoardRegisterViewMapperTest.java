package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.AgencyBoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.AgencyBoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.AgencyBoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BoardTypeView;
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
        bAgencyBoard.setBriefing(null);

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

        // Simulated implementation of the map method
        doAnswer(invocation -> {
            BAgencyBoard input = invocation.getArgument(0);
            BAgencyBoardView mappedBAgencyBoardView = bAgencyBoardViewMapper.map(input);
            // Since briefing is null, we do not call projectViewMapper or briefingViewMapper
            return new BAgencyBoardDetailedView(mappedBAgencyBoardView, null, null);
        }).when(mapper).map(any(BAgencyBoard.class));

        // Mapping
        BAgencyBoardDetailedView result = mapper.map(bAgencyBoard);

        // Result verification
        assertThat(result).isNotNull();
        assertThat(result.bAgencyBoard()).isEqualTo(bAgencyBoardView);
        assertThat(result.project()).isNull();
        assertThat(result.briefing()).isNull();

        // Verify that only bAgencyBoardViewMapper was called
        verify(bAgencyBoardViewMapper, times(1)).map(any(BAgencyBoard.class));
        verify(projectViewMapper, never()).map(any());
        verify(briefingViewMapper, never()).map(any());
    }

    @Test
    void mustMapBAgencyBoardWithNullComponentsToBAgencyBoardRegisterView() {
        // Dados de teste
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        Briefing briefing = new Briefing();
        briefing.setProject(null);
        bAgencyBoard.setBriefing(briefing);

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

        // Simulated implementation of the map method
        doAnswer(invocation -> {
            BAgencyBoard input = invocation.getArgument(0);
            BAgencyBoardView mappedBAgencyBoardView = bAgencyBoardViewMapper.map(input);
            // Since the projectForm is null, we do not call projectViewMapper
            return new BAgencyBoardDetailedView(mappedBAgencyBoardView, null, null);
        }).when(mapper).map(any(BAgencyBoard.class));

        // Mapping
        BAgencyBoardDetailedView result = mapper.map(bAgencyBoard);

        // Result verification
        assertThat(result).isNotNull();
        assertThat(result.bAgencyBoard()).isEqualTo(bAgencyBoardView);
        assertThat(result.project()).isNull();
        assertThat(result.briefing()).isNull();

        // Verify that only bAgencyBoardViewMapper was called
        verify(bAgencyBoardViewMapper, times(1)).map(any(BAgencyBoard.class));
        verify(projectViewMapper, never()).map(any());
        verify(briefingViewMapper, never()).map(any());
    }

    @Test
    void shouldMapBAgencyBoardWithBriefingAndProjectToBAgencyBoardRegisterView() {
        // Create a valid BAgencyBoard
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        Briefing briefing = new Briefing();
        Project project = new Project();
        briefing.setProject(project);
        bAgencyBoard.setBriefing(briefing);

        // Simulated data for mappers
        BAgencyBoardView bAgencyBoardView = new BAgencyBoardView(
                1L,
                new AgencyBoardTypeView(1L, "Tipo1"),
                new BoardTypeView(1L, "Tipo de Placa"),
                Set.of(),
                Set.of(),
                null,
                null
        );

        // Creating ProjectView with the correct parameter order
        ProjectView projectView = new ProjectView(
                1L, // id
                "Projeto Teste", // title
                "Em Andamento", // status
                new EmployeeSimpleView(1L, "Cliente Teste", 101L), // client
                new EmployeeSimpleView(2L, "Colaborador Teste", 102L), // collaborator
                "Tipo de Briefing",
                LocalDate.now()
        );

        // Mock behavior setup
        when(bAgencyBoardViewMapper.map(any(BAgencyBoard.class))).thenReturn(bAgencyBoardView);
        when(projectViewMapper.map(any(Project.class))).thenReturn(projectView);

        // Simulated implementation of the map method
        doAnswer(invocation -> {
            BAgencyBoard input = invocation.getArgument(0);
            BAgencyBoardView mappedBAgencyBoardView = bAgencyBoardViewMapper.map(input);
            ProjectView mappedProjectView = projectViewMapper.map(input.getBriefing().getProject());
            return new BAgencyBoardDetailedView(mappedBAgencyBoardView, mappedProjectView, null);
        }).when(mapper).map(any(BAgencyBoard.class));

        // Mapping
        BAgencyBoardDetailedView result = mapper.map(bAgencyBoard);

        // Verification
        assertThat(result).isNotNull();
        assertThat(result.bAgencyBoard()).isEqualTo(bAgencyBoardView);
        assertThat(result.project()).isEqualTo(projectView);
        assertThat(result.briefing()).isNull();

        // Verify that mappers were called correctly
        verify(bAgencyBoardViewMapper, times(1)).map(any(BAgencyBoard.class));
        verify(projectViewMapper, times(1)).map(any(Project.class));
        verify(briefingViewMapper, never()).map(any());
    }
}