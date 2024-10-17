package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.BGiftDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.BGiftView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.BGift;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BGiftDetailedViewMapperTest {

    @Mock
    private BGiftViewMapper bGiftViewMapper;

    @Mock
    private BriefingViewMapper briefingViewMapper;

    @Mock
    private ProjectViewMapper projectViewMapper;

    @InjectMocks
    private BGiftDetailedViewMapper bGiftDetailedViewMapper;

    public BGiftDetailedViewMapperTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMap() {
        BGift bGift = new BGift();
        Briefing briefing = new Briefing();
        Project project = new Project();
        
        bGift.setBriefing(briefing);
        briefing.setProject(project);

        // Atualizando a criação de BGiftView, ProjectView e BriefingView para usar os novos registros
        BGiftView bGiftView = new BGiftView(null, null, null, null, null, null, null, null);
        ProjectView projectView = new ProjectView(null, null, null, null, null);
        BriefingView briefingView = new BriefingView(null, null, null, null, null, null, null, null, null);

        when(bGiftViewMapper.map(bGift)).thenReturn(bGiftView);
        when(projectViewMapper.map(project)).thenReturn(projectView);
        when(briefingViewMapper.map(briefing)).thenReturn(briefingView);

        BGiftDetailedView result = bGiftDetailedViewMapper.map(bGift);

        
        assertEquals(bGiftView, result.bGiftView(), "O BGiftView deve ser igual ao esperado");
        assertEquals(projectView, result.projectView(), "O ProjectView deve ser igual ao esperado");
        assertEquals(briefingView, result.briefingView(), "O BriefingView deve ser igual ao esperado");
    }
}