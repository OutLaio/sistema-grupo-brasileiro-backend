package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.MaterialView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BSignpostDetailedViewMapperTest {

    @Mock
    private BSignpostViewMapper bSignpostViewMapper;

    @Mock
    private ProjectViewMapper projectViewMapper;

    @Mock
    private BriefingViewMapper briefingViewMapper;

    @InjectMocks
    private BSignpostDetailedViewMapper bSignpostDetailedViewMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapComBSignpostCompleto() {
        // Arrange
        BSignpost bSignpost = new BSignpost();
        Briefing briefing = new Briefing();
        Project project = new Project();
        briefing.setProject(project);
        bSignpost.setBriefing(briefing);

        BSignpostView bSignpostView = new BSignpostView(1L, new MaterialView(1L, "Material"), "Localização", "Setor");
        BriefingTypeView briefingTypeView = new BriefingTypeView(1L, "Tipo de Briefing");
        
        // Atualizando BriefingView para refletir a nova estrutura
        BriefingView briefingView = new BriefingView(
            1L, // id
            briefingTypeView, // briefingType
            LocalDate.now(), // startTime
            LocalDate.now().plusDays(7), // expectedTime
            LocalDate.now().plusDays(14), // finishTime
            "Descrição detalhada", // detailedDescription
            null, // measurements
            null, // companies
            null, // otherCompanies
            null  // versions
        );

        EmployeeSimpleView client = new EmployeeSimpleView(1L, "Cliente", null);
        EmployeeSimpleView collaborator = new EmployeeSimpleView(2L, "Colaborador", null);
        
        // Ajustando a criação do ProjectView para usar a nova estrutura
        ProjectView projectView = new ProjectView(
            1L, // id
            "Título do Projeto", // title
            "Em andamento", // status
            client, // client
            collaborator, // collaborator
            null,
            LocalDate.now()
        );

        when(bSignpostViewMapper.map(bSignpost)).thenReturn(bSignpostView);
        when(briefingViewMapper.map(briefing)).thenReturn(briefingView);
        when(projectViewMapper.map(project)).thenReturn(projectView);

        // Act
        BSignpostDetailedView resultado = bSignpostDetailedViewMapper.map(bSignpost);

        // Assert
        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals(bSignpostView, resultado.signpost(), "BSignpostView deve ser igual");
        
        // Verificações detalhadas para projectView
        assertNotNull(resultado.project(), "ProjectView não deve ser nulo");
        assertEquals(projectView.id(), resultado.project().id(), "ID do ProjectView deve ser igual");
        assertEquals(projectView.title(), resultado.project().title(), "Título do ProjectView deve ser igual");
        assertEquals(projectView.status(), resultado.project().status(), "Status do ProjectView deve ser igual");
        assertEquals(projectView.client(), resultado.project().client(), "Cliente do ProjectView deve ser igual");
        assertEquals(projectView.collaborator(), resultado.project().collaborator(), "Colaborador do ProjectView deve ser igual");
        
        // Verificações detalhadas para briefingView
        assertNotNull(resultado.briefing(), "BriefingView não deve ser nulo");
        assertEquals(briefingView, resultado.briefing(), "BriefingView deve ser igual");
        
        verify(bSignpostViewMapper).map(bSignpost);
        verify(briefingViewMapper).map(briefing);
        verify(projectViewMapper).map(project);
    }
    
    @Test
    void testMapComBSignpostSemBriefing() {
        // Arrange
        BSignpost bSignpost = new BSignpost();
        bSignpost.setBriefing(null);

        BSignpostView bSignpostView = new BSignpostView(1L, new MaterialView(1L, "Material"), "Localização", "Setor");

        when(bSignpostViewMapper.map(bSignpost)).thenReturn(bSignpostView);

        // Act
        BSignpostDetailedView resultado = bSignpostDetailedViewMapper.map(bSignpost);

        // Assert
        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals(bSignpostView, resultado.signpost(), "BSignpostView deve ser igual");
        assertNull(resultado.project(), "ProjectView deve ser nulo");
        assertNull(resultado.briefing(), "BriefingView deve ser nulo");

        verify(bSignpostViewMapper).map(bSignpost);
        verifyNoInteractions(projectViewMapper);
        verifyNoInteractions(briefingViewMapper);
    }
}