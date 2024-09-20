package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.sinpost.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.SignpostRegisterView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view.BSignpostRegisterViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view.BSignpostViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BSignpostRegisterViewMapperTest {

    @InjectMocks
    private BSignpostRegisterViewMapper bSignpostRegisterViewMapper;

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

    @Test
    @DisplayName("Should map BSignpost to SignpostRegisterView correctly")
    void deveMapearBSignpostParaSignpostRegisterView() {
        // Configuração dos mocks
        when(bSignpost.getBriefing()).thenReturn(briefing);
        when(briefing.getProject()).thenReturn(project);
        when(bSignpostViewMapper.map(bSignpost)).thenReturn(bSignpostView);
        when(projectViewMapper.map(project)).thenReturn(projectView);
        when(briefingViewMapper.map(briefing)).thenReturn(briefingView);

        // Mapeamento
        SignpostRegisterView result = bSignpostRegisterViewMapper.map(bSignpost);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.bSignpostView()).isEqualTo(bSignpostView); 
        assertThat(result.projectView()).isEqualTo(projectView);    
        assertThat(result.briefingView()).isEqualTo(briefingView);  
    }

    @Test
    @DisplayName("Should return null when mapping null BSignpost")
    void deveRetornarNullParaBSignpostNulo() {
        // Mapeamento
        SignpostRegisterView result = bSignpostRegisterViewMapper.map(null);

        // Verificação dos resultados
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("Should map BSignpost with null Briefing to SignpostRegisterView with null BriefingView and ProjectView")
    void deveMapearBSignpostComBriefingNuloParaSignpostRegisterViewComBriefingViewEProjectViewNulo() {
        // Configuração dos mocks
        when(bSignpost.getBriefing()).thenReturn(null);
        when(bSignpostViewMapper.map(bSignpost)).thenReturn(bSignpostView);

        // Mapeamento
        SignpostRegisterView result = bSignpostRegisterViewMapper.map(bSignpost);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.bSignpostView()).isEqualTo(bSignpostView); 
        assertThat(result.projectView()).isNull();                    
        assertThat(result.briefingView()).isNull();                    
    }
}
