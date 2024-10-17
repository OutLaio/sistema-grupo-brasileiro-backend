package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefins.handout.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.BHandoutDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.BHandoutView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.view.BHandoutDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.view.BHandoutViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;

public class BHandoutDetailedViewMapperTest {

    @InjectMocks
    private BHandoutDetailedViewMapper mapper;

    @Mock
    private BHandoutViewMapper bHandoutViewMapper;

    @Mock
    private BriefingViewMapper briefingViewMapper;

    @Mock
    private ProjectViewMapper projectViewMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMap() {
        // Configurando os mocks
        BHandout bHandout = mock(BHandout.class);
        Briefing briefing = mock(Briefing.class);
        Project project = mock(Project.class);
        BHandoutView bHandoutView = mock(BHandoutView.class);
        ProjectView projectView = mock(ProjectView.class);
        BriefingView briefingView = mock(BriefingView.class);

        when(bHandout.getBriefing()).thenReturn(briefing);
        when(briefing.getProject()).thenReturn(project);
        when(bHandoutViewMapper.map(bHandout)).thenReturn(bHandoutView);
        when(projectViewMapper.map(project)).thenReturn(projectView);
        when(briefingViewMapper.map(briefing)).thenReturn(briefingView);

        // Executando o método a ser testado
        BHandoutDetailedView result = mapper.map(bHandout);

        // Verificando os resultados
        assertNotNull(result);
        assertEquals(bHandoutView, result.bHandoutView());
        assertEquals(projectView, result.projectView());
        assertEquals(briefingView, result.briefingView());
    }

    @Test
    public void testMap_NullBHandout() {
        // Executando o método a ser testado com bHandout nulo
        assertThrows(NullPointerException.class, () -> {
            mapper.map(null);
        });
    }
}