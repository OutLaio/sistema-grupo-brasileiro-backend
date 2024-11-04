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

 // ... código existente ...
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
        // Criando BriefingView com a nova estrutura
        BriefingView briefingView = new BriefingView(
            null, // id
            null, // briefingType
            null, // startTime
            null, // expectedTime
            null, // finishTime
            null, // detailedDescription
            null, // measurements
            null, // companies
            null, // otherCompanies
            null  // versions
        );

        when(bGiftViewMapper.map(bGift)).thenReturn(bGiftView);
        when(projectViewMapper.map(project)).thenReturn(projectView);
        when(briefingViewMapper.map(briefing)).thenReturn(briefingView);

        // Mapeando BGift para BGiftDetailedView
        BGiftDetailedView result = bGiftDetailedViewMapper.map(bGift);

        assertEquals(bGiftView, result.bGift(), "O BGiftView deve ser igual ao esperado");
        assertEquals(projectView, result.project(), "O ProjectView deve ser igual ao esperado");
        assertEquals(briefingView, result.briefing(), "O BriefingView deve ser igual ao esperado");
    }
}