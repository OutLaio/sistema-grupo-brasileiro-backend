package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.sinpost.view;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view.BSignpostDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view.BSignpostViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;

/**
 * Testa a classe BSignpostRegisterViewMapper.
 * Verifica se o mapeamento de BSignpost para SignpostRegisterView é feito corretamente.
 */
public class BSignpostRegisterViewMapperTest {

    @InjectMocks
    private BSignpostDetailedViewMapper bSignpostRegisterViewMapper;

    @Mock
    private BSignpostViewMapper bSignpostViewMapper;

    @Mock
    private ProjectViewMapper projectViewMapper;

    @Mock
    private BriefingViewMapper briefingViewMapper;

    @Mock
    private BSignpost bSignpost;

    @Spy
    private Briefing briefing;

    @Mock
    private Project project;

    @Mock
    private BSignpostView bSignpostView;

    @Mock
    private ProjectView projectView;

    @Mock
    private BriefingView briefingView;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa o mapeamento de um BSignpost para um SignpostRegisterView.
     * Verifica se todas as propriedades são mapeadas corretamente.
     */
    @Test
    @DisplayName("Should map BSignpost to SignpostRegisterView correctly")
    void MapearBSignpostParaSignpostRegisterView() {
        // Configuração dos mocks
        when(bSignpost.getBriefing()).thenReturn(briefing);
        when(briefing.getProject()).thenReturn(project);
        when(bSignpostViewMapper.map(bSignpost)).thenReturn(bSignpostView);
        when(projectViewMapper.map(project)).thenReturn(projectView);
        when(briefingViewMapper.map(briefing)).thenReturn(briefingView);

        // Mapeamento
        BSignpostDetailedView result = bSignpostRegisterViewMapper.map(bSignpost);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.bSignpostView()).isEqualTo(bSignpostView); 
        assertThat(result.projectView()).isEqualTo(projectView);    
        assertThat(result.briefingView()).isEqualTo(briefingView);  
    }

    /**
     * Testa que o método map retorna um SignpostRegisterView com briefingView e projectView nulos ao mapear um BSignpost sem briefing.
     */
    @Test
    @DisplayName("Should return SignpostRegisterView with null briefingView and projectView when mapping BSignpost without briefing")
    void deveRetornarSignpostRegisterViewComBriefingViewENuloQuandoMapearBSignpostSemBriefing() {
        // Configuração do mock
        when(bSignpost.getBriefing()).thenReturn(null);
        
        // Mapeamento
        BSignpostDetailedView result = bSignpostRegisterViewMapper.map(bSignpost);
        
        // Verificação dos resultados
        assertThat(result).isNotNull();
      }



    /**
     * Testa o mapeamento de um BSignpost com briefing nulo.
     * Verifica se o resultado possui null para BriefingView e ProjectView.
     */
    @Test
    @DisplayName("Should map BSignpost with null Briefing to SignpostRegisterView with null BriefingView and ProjectView")
    void MapearBSignpostComBriefingNuloParaSignpostRegisterViewComBriefingViewEProjectViewNulo() {
        // Configuração dos mocks
        when(bSignpost.getBriefing()).thenReturn(null);
        when(bSignpostViewMapper.map(bSignpost)).thenReturn(bSignpostView);

        // Mapeamento
        BSignpostDetailedView result = bSignpostRegisterViewMapper.map(bSignpost);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.bSignpostView()).isEqualTo(bSignpostView); 
        assertThat(result.projectView()).isNull();                    
        assertThat(result.briefingView()).isNull();                    
    }
}
