package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.printed.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.BPrintedsDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.PrintedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.printed.view.BPrintedsDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.BPrinted;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BPrintedsDetailedViewMapperTest {

    @InjectMocks
    private BPrintedsDetailedViewMapper mapper; 

    @Mock
    private BPrintedsViewMapper bPrintedsViewMapper; 

    @Mock
    private ProjectViewMapper projectViewMapper; 

    @Mock
    private BriefingViewMapper briefingViewMapper; 

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); 
    }

    @Test
    public void testMap() {
        // Arrange
        BPrinted bPrinted = mock(BPrinted.class);
        Briefing briefing = mock(Briefing.class);
        Project project = mock(Project.class);

        when(bPrinted.getBriefing()).thenReturn(briefing);
        when(briefing.getProject()).thenReturn(project);

        PrintedView printedView = new PrintedView(1L, null, null, "Paper Type", 2, 10);
        ProjectView projectView = new ProjectView(1L, "Project Title", "Active", null, null);
        BriefingView briefingView = new BriefingView(1L, null, null, null, null, null, null, null, null, null);

        // Configura os mocks para retornar os valores esperados
        when(bPrintedsViewMapper.map(bPrinted)).thenReturn(printedView);
        when(projectViewMapper.map(project)).thenReturn(projectView);
        when(briefingViewMapper.map(briefing)).thenReturn(briefingView);

        // Act
        BPrintedsDetailedView result = mapper.map(bPrinted);

        // Assert
        assertEquals(printedView, result.printed());
        assertEquals(projectView, result.project());
        assertEquals(briefingView, result.briefing());
    }

    @Test
    public void testMapWithNullBriefing() {
        // Arrange
        BPrinted bPrinted = mock(BPrinted.class);
        when(bPrinted.getBriefing()).thenReturn(null);

        PrintedView printedView = new PrintedView(1L, null, null, "Paper Type", 2, 10);
        when(bPrintedsViewMapper.map(bPrinted)).thenReturn(printedView);

        // Act
        BPrintedsDetailedView result = mapper.map(bPrinted);

        // Assert
        assertEquals(printedView, result.printed());
        assertNull(result.project());
        assertNull(result.briefing());
    }
}
