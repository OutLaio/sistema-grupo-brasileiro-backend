package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.bHandout;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.form.BHandoutForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.BHandoutDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.BHandoutView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.HandoutTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.form.BHandoutFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.view.BHandoutDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.HandoutType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.handout.BHandoutRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.handout.HandoutTypeRepository;

public class BHandoutServiceTest {

    @InjectMocks
    private BHandoutService bHandoutService;

    @Mock
    private BHandoutRepository bHandoutRepository;

    @Mock
    private HandoutTypeRepository handoutTypeRepository;

    @Mock
    private BHandoutFormMapper bHandoutFormMapper;

    @Mock
    private BHandoutDetailedViewMapper bHandoutDetailedViewMapper;

    @Mock
    private Briefing briefing;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        // Criando um objeto BHandoutForm para testar
        BHandoutForm bHandoutForm = new BHandoutForm(1L);
        
        // Criando um objeto HandoutType simulado
        HandoutType handoutType = new HandoutType(1L, "Tipo A");
        
        // Criando um objeto BHandout simulado
        BHandout bHandout = new BHandout();
        
        // Criando objetos simulados para BHandoutView, ProjectView e BriefingView
        BHandoutView bHandoutView = new BHandoutView(1L, new HandoutTypeView(1L, "Tipo A"));
        ProjectView projectView = new ProjectView(1L, "Projeto A", "Em andamento", null, null);
        BriefingView briefingView = new BriefingView(1L, null, null, null, null, null, null, null, null);
        
        // Criando um objeto BHandoutDetailedView com os objetos simulados
        BHandoutDetailedView bHandoutDetailedView = new BHandoutDetailedView(bHandoutView, projectView, briefingView);

        // Configurando o comportamento dos mocks
        when(handoutTypeRepository.getReferenceById(1L)).thenReturn(handoutType);
        when(bHandoutFormMapper.map(bHandoutForm)).thenReturn(bHandout);
        when(bHandoutRepository.save(bHandout)).thenReturn(bHandout);
        when(bHandoutDetailedViewMapper.map(bHandout)).thenReturn(bHandoutDetailedView);

        // Chamando o método a ser testado
        BHandoutDetailedView result = bHandoutService.register(bHandoutForm, briefing);

        // Verificando se o resultado não é nulo
        assertNotNull(result, "O resultado não deve ser nulo");
        
        // Verificando se o método save foi chamado
        verify(bHandoutRepository).save(bHandout);
        
        // Verificando se o handoutType e briefing foram configurados corretamente
        assertEquals(handoutType, bHandout.getHandoutType(), "O tipo de handout deve ser o esperado");
        assertEquals(briefing, bHandout.getBriefing(), "O briefing deve ser o esperado");
    }
}