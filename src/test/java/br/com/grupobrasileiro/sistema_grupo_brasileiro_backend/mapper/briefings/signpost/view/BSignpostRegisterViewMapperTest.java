package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.SignpostRegisterView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.BriefingViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view.ProjectViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;

public class BSignpostRegisterViewMapperTest {

    @InjectMocks
    private BSignpostRegisterViewMapper bSignpostRegisterViewMapper;

    @Mock
    private BSignpostViewMapper bSignpostViewMapper;

    @Mock
    private ProjectViewMapper projectViewMapper;

    @Mock
    private BriefingViewMapper briefingViewMapper;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    /**
     * Testa o mapeamento de BSignpost para SignpostRegisterView.
     * Verifica se um BSignpost é corretamente mapeado para um SignpostRegisterView.
     */
    @Test
    @DisplayName("Should map BSignpost to SignpostRegisterView correctly")
    void mapBSignpostToView() {
        BSignpost bSignpost = new BSignpost();
        Briefing briefing = new Briefing();
        Project project = new Project();

        briefing.setProject(project);
        bSignpost.setBriefing(briefing);

        BSignpostView bSignpostView = new BSignpostView(
            faker.number().randomNumber(),
            null,
            faker.address().streetAddress(),
            faker.lorem().word()
        );
        when(bSignpostViewMapper.map(bSignpost)).thenReturn(bSignpostView);

        ProjectView projectView = new ProjectView(
            faker.number().randomNumber(),
            faker.lorem().word(),
            faker.lorem().word(),
            null,
            null
        );
        when(projectViewMapper.map(project)).thenReturn(projectView);

        BriefingView briefingView = new BriefingView(
            faker.number().randomNumber(),
            null,
            faker.date().past(10, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
            faker.date().future(5, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
            faker.date().future(10, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
            faker.lorem().sentence()
        );
        when(briefingViewMapper.map(briefing)).thenReturn(briefingView);

        SignpostRegisterView result = bSignpostRegisterViewMapper.map(bSignpost);

        assertThat(result).isNotNull();
        assertThat(result.bSignpostView()).isEqualTo(bSignpostView);
        assertThat(result.projectView()).isEqualTo(projectView);
        assertThat(result.briefingView()).isEqualTo(briefingView);
    }

     /**
     * Testa o mapeamento de BSignpost com Briefing nulo para SignpostRegisterView.
     * Verifica se o método lida corretamente com Briefing nulo no BSignpost.
     */
    @Test
    @DisplayName("Should map BSignpost with null Briefing to SignpostRegisterView with null Briefing and Project")
    void mapBSignpostWithNullBriefing() {
        BSignpost bSignpost = new BSignpost();
        bSignpost.setBriefing(null);

        BSignpostView bSignpostView = new BSignpostView(
            faker.number().randomNumber(),
            null,
            faker.address().streetAddress(),
            faker.lorem().word()
        );
        when(bSignpostViewMapper.map(bSignpost)).thenReturn(bSignpostView);

        SignpostRegisterView result = bSignpostRegisterViewMapper.map(bSignpost);

        assertThat(result).isNotNull();
        assertThat(result.bSignpostView()).isEqualTo(bSignpostView);
        assertThat(result.projectView()).isNull();
        assertThat(result.briefingView()).isNull();
    }

    /**
     * Testa o mapeamento de BSignpost com Briefing sem Project para SignpostRegisterView.
     * Verifica se o método lida corretamente com Briefing sem Project.
     */
    @Test
    @DisplayName("Should map BSignpost with Briefing without Project to SignpostRegisterView with null ProjectView")
    void mapBSignpostWithBriefingWithoutProject() {
        BSignpost bSignpost = new BSignpost();
        Briefing briefing = new Briefing();
        briefing.setProject(null);
        bSignpost.setBriefing(briefing);

        BSignpostView bSignpostView = new BSignpostView(
            faker.number().randomNumber(),
            null,
            faker.address().streetAddress(),
            faker.lorem().word()
        );
        when(bSignpostViewMapper.map(bSignpost)).thenReturn(bSignpostView);
        when(briefingViewMapper.map(briefing)).thenReturn(new BriefingView(
            faker.number().randomNumber(),
            null,
            faker.date().past(10, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
            faker.date().future(5, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
            faker.date().future(10, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
            faker.lorem().sentence()
        ));

        SignpostRegisterView result = bSignpostRegisterViewMapper.map(bSignpost);

        assertThat(result).isNotNull();
        assertThat(result.bSignpostView()).isEqualTo(bSignpostView);
        assertThat(result.projectView()).isNull();
        assertThat(result.briefingView()).isNotNull();
    }
}
