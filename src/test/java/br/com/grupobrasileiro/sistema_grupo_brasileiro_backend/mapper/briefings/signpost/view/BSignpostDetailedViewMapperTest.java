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
        BriefingView briefingView = new BriefingView(1L, briefingTypeView, LocalDate.now(), LocalDate.now().plusDays(7), null, "Descrição detalhada");
        EmployeeSimpleView client = new EmployeeSimpleView(1L, "Cliente", null);
        EmployeeSimpleView collaborator = new EmployeeSimpleView(2L, "Colaborador", null);
        ProjectView projectView = new ProjectView(1L, "Título do Projeto", "Em Andamento", client, collaborator);

        when(bSignpostViewMapper.map(bSignpost)).thenReturn(bSignpostView);
        when(briefingViewMapper.map(briefing)).thenReturn(briefingView);
        when(projectViewMapper.map(project)).thenReturn(projectView);

        // Act
        BSignpostDetailedView resultado = bSignpostDetailedViewMapper.map(bSignpost);

        // Assert
        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals(bSignpostView, resultado.bSignpostView(), "BSignpostView deve ser igual");
        
        // Verificações detalhadas para projectView
        assertNotNull(resultado.projectView(), "ProjectView não deve ser nulo");
        assertEquals(projectView.idProject(), resultado.projectView().idProject(), "ID do ProjectView deve ser igual");
        assertEquals(projectView.title(), resultado.projectView().title(), "Título do ProjectView deve ser igual");
        assertEquals(projectView.status(), resultado.projectView().status(), "Status do ProjectView deve ser igual");
        assertEquals(projectView.client(), resultado.projectView().client(), "Cliente do ProjectView deve ser igual");
        assertEquals(projectView.collaborator(), resultado.projectView().collaborator(), "Colaborador do ProjectView deve ser igual");
        
        // Verificações detalhadas para briefingView
        assertNotNull(resultado.briefingView(), "BriefingView não deve ser nulo");
        
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
        assertNotNull(resultado);
        assertEquals(bSignpostView, resultado.bSignpostView());
        assertNull(resultado.projectView());
        assertNull(resultado.briefingView());

        verify(bSignpostViewMapper).map(bSignpost);
        verifyNoInteractions(projectViewMapper);
        verifyNoInteractions(briefingViewMapper);
    }
}