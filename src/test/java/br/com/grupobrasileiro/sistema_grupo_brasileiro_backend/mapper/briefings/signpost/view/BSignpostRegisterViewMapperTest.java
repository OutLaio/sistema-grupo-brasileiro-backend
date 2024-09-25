package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

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

/**
 * Testa a classe BSignpostRegisterViewMapper.
 * Verifica se o mapeamento de BSignpost para SignpostRegisterView é feito corretamente.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(BSignpostView.class) 
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

    @Mock
    private Briefing briefing;

    @Mock
    private Project project;

    @Mock
    private BSignpostView bSignpostView; 

    @Mock
    private ProjectView projectView;

    @Mock
    private BriefingView briefingView;

    @Before // Mantenha esta anotação do JUnit
    public void setUp() {
        // Inicialização do Mockito
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Testa o mapeamento de um BSignpost para um SignpostRegisterView.
     * Verifica se todas as propriedades são mapeadas corretamente.
     */
    @Test
    @DisplayName("Should map BSignpost to SignpostRegisterView correctly")
    void mapBSignpostToSignpostRegisterView() {
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

    /**
     * Testa que o método map retorna um SignpostRegisterView com briefingView e projectView nulos ao mapear um BSignpost sem briefing.
     */
    @Test
    @DisplayName("Should return SignpostRegisterView with null briefingView and projectView when mapping BSignpost without briefing")
    void returnViewWithNullsForNoBriefing() {
        // Configuração do mock
        when(bSignpost.getBriefing()).thenReturn(null);

        // Mapeamento
        SignpostRegisterView result = bSignpostRegisterViewMapper.map(bSignpost);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.bSignpostView()).isNull(); // Verifica se bSignpostView é nulo
        assertThat(result.projectView()).isNull(); // Verifica se projectView é nulo
        assertThat(result.briefingView()).isNull(); // Verifica se briefingView é nulo
    }

    /**
     * Testa o mapeamento de um BSignpost com briefing nulo.
     * Verifica se o resultado possui null para BriefingView e ProjectView.
     */
    @Test
    @DisplayName("Should map BSignpost with null Briefing to SignpostRegisterView with null BriefingView and ProjectView")
    void mapBSignpostWithNullBriefing() {
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

    /**
     * Testa o mapeamento de um BSignpost com todos os campos nulos.
     * Verifica se todos os campos no resultado também são nulos.
     */
    @Test
    @DisplayName("Should return all null fields when mapping BSignpost with all nulls")
    void mapBSignpostWithAllNulls() {
        when(bSignpost.getBriefing()).thenReturn(null);
        when(bSignpostViewMapper.map(bSignpost)).thenReturn(null);

        // Mapeamento
        SignpostRegisterView result = bSignpostRegisterViewMapper.map(bSignpost);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.bSignpostView()).isNull();
        assertThat(result.projectView()).isNull();
        assertThat(result.briefingView()).isNull();
    }

    /**
     * Testa o mapeamento de um BSignpost com projeto nulo.
     * Verifica se o resultado possui null para ProjectView.
     */
    @Test
    @DisplayName("Should map BSignpost with null Project to SignpostRegisterView with null ProjectView")
    void mapBSignpostWithNullProject() {
        when(bSignpost.getBriefing()).thenReturn(briefing);
        when(briefing.getProject()).thenReturn(null);
        when(bSignpostViewMapper.map(bSignpost)).thenReturn(bSignpostView);
        when(briefingViewMapper.map(briefing)).thenReturn(briefingView);

        // Mapeamento
        SignpostRegisterView result = bSignpostRegisterViewMapper.map(bSignpost);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.bSignpostView()).isEqualTo(bSignpostView);
        assertThat(result.projectView()).isNull();
        assertThat(result.briefingView()).isEqualTo(briefingView);
    }
    
    /**
     * Testa o mapeamento de um BSignpost nulo.
     * Verifica se o resultado é nulo.
     */
    @Test
    @DisplayName("Should return null when mapping null BSignpost")
    void returnNullForNullBSignpost() {
        // Mapeamento
        SignpostRegisterView result = bSignpostRegisterViewMapper.map(null);

        // Verificação dos resultados
        assertThat(result).isNull();
    }

    /**
     * Testa o comportamento ao mapear BSignpost quando BSignpostViewMapper retorna nulo.
     * Verifica se o resultado possui BSignpostView nulo.
     */
    @Test
    @DisplayName("Should map BSignpost and return null BSignpostView when BSignpostViewMapper returns null")
    void returnNullBSignpostViewWhenMapperReturnsNull() {
        // Configuração dos mocks
        when(bSignpost.getBriefing()).thenReturn(briefing);
        when(briefing.getProject()).thenReturn(project);
        when(bSignpostViewMapper.map(bSignpost)).thenReturn(null); // BSignpostViewMapper retorna nulo
        when(projectViewMapper.map(project)).thenReturn(projectView);
        when(briefingViewMapper.map(briefing)).thenReturn(briefingView);

        // Mapeamento
        SignpostRegisterView result = bSignpostRegisterViewMapper.map(bSignpost);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.bSignpostView()).isNull(); // Verifica se bSignpostView é nulo
        assertThat(result.projectView()).isEqualTo(projectView);
        assertThat(result.briefingView()).isEqualTo(briefingView);
    }

    /**
     * Testa o mapeamento de um BSignpost com Briefing nulo.
     * Verifica se o resultado possui null para ProjectView.
     */
    @Test
    @DisplayName("Should map BSignpost with null Briefing to SignpostRegisterView with null ProjectView")
    void mapBSignpostWithNullBriefingReturnsNullProjectView() {
        // Configuração dos mocks
        when(bSignpost.getBriefing()).thenReturn(null);
        when(bSignpostViewMapper.map(bSignpost)).thenReturn(bSignpostView);

        // Mapeamento
        SignpostRegisterView result = bSignpostRegisterViewMapper.map(bSignpost);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.bSignpostView()).isEqualTo(bSignpostView);
        assertThat(result.projectView()).isNull(); // Verifica se projectView é nulo
        assertThat(result.briefingView()).isNull(); // Verifica se briefingView é nulo
    }
}
