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
        // Criando os objetos necessários
        BSignpost bSignpost = new BSignpost();
        Briefing briefing = new Briefing();
        Project project = new Project();
        briefing.setProject(project);
        bSignpost.setBriefing(briefing);

        // Criando as views mockadas para o teste
        BSignpostView bSignpostView = new BSignpostView(1L, new MaterialView(1L, "Material"), "Localização", "Setor");
        BriefingTypeView briefingTypeView = new BriefingTypeView(1L, "Tipo de Briefing");

        // Criando BriefingView com a nova estrutura
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

        // Criando views de clientes e colaboradores
        EmployeeSimpleView client = new EmployeeSimpleView(1L, "Cliente", null);
        EmployeeSimpleView collaborator = new EmployeeSimpleView(2L, "Colaborador", null);

        // Criando o ProjectView com base na estrutura que não pode ser alterada
        ProjectView projectView = new ProjectView(
            1L, // id
            "Título do Projeto", // title
            "Em andamento", // status
            client, // client
            collaborator, // collaborator
            "Tipo A", // briefingType, você pode substituir o valor do tipo de briefing conforme necessário
            LocalDate.now()
        );

        // Configurando o comportamento dos mocks
        when(bSignpostViewMapper.map(bSignpost)).thenReturn(bSignpostView);
        when(briefingViewMapper.map(briefing)).thenReturn(briefingView);
        when(projectViewMapper.map(project)).thenReturn(projectView);

        // Act
        // Mapeando BSignpost para BSignpostDetailedView
        BSignpostDetailedView resultado = bSignpostDetailedViewMapper.map(bSignpost);

        // Assert
        // Verificando o resultado
        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals(bSignpostView, resultado.signpost(), "BSignpostView deve ser igual");

        // Verificando detalhadamente o ProjectView
        assertNotNull(resultado.project(), "ProjectView não deve ser nulo");
        assertEquals(projectView.id(), resultado.project().id(), "ID do ProjectView deve ser igual");
        assertEquals(projectView.title(), resultado.project().title(), "Título do ProjectView deve ser igual");
        assertEquals(projectView.status(), resultado.project().status(), "Status do ProjectView deve ser igual");
        assertEquals(projectView.client(), resultado.project().client(), "Cliente do ProjectView deve ser igual");
        assertEquals(projectView.collaborator(), resultado.project().collaborator(), "Colaborador do ProjectView deve ser igual");
        assertEquals(projectView.briefingType(), resultado.project().briefingType(), "BriefingType do ProjectView deve ser igual");

        // Verificando detalhadamente o BriefingView
        assertNotNull(resultado.briefing(), "BriefingView não deve ser nulo");
        assertEquals(briefingView, resultado.briefing(), "BriefingView deve ser igual");

        // Verificando se os mappers foram chamados corretamente
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