package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.bHandout;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.form.BHandoutForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.BHandoutDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.BHandoutView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.HandoutTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.form.BHandoutFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.view.BHandoutDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.HandoutType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
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
        
        // Criando objetos simulados para BHandoutView e ProjectView
        BHandoutView bHandoutView = new BHandoutView(1L, new HandoutTypeView(1L, "Tipo A"));
        
        // Ajustando a criação de ProjectView com os novos parâmetros
        ProjectView projectView = new ProjectView(
            1L, // id
            "Projeto A", // title
            "Em andamento", // status
            new EmployeeSimpleView(2L, "Cliente A", null), // client
            new EmployeeSimpleView(1L, "Colaborador A", null), // collaborator
            "Tipo de Briefing" // briefingType
        );
        
        // Criando um objeto Employee simulado para o colaborador e cliente
        Employee collaborator = new Employee(); // Ajuste conforme a estrutura da classe Employee
        collaborator.setId(1L);
        collaborator.setName("Colaborador A"); // Supondo que exista um método setName

        Employee client = new Employee(); // Ajuste conforme a estrutura da classe Employee
        client.setId(2L);
        client.setName("Cliente A"); // Supondo que exista um método setName

        // Criando um objeto Project simulado
        Project project = new Project();
        project.setId(1L);
        project.setCollaborator(collaborator);
        project.setClient(client);
        project.setTitle("Projeto A");
        project.setStatus("Em andamento");
        project.setDisabled(false);

        // Criando um objeto BriefingType simulado
        BriefingType briefingType = new BriefingType(1L, "Tipo de Briefing"); // Usando o construtor definido

        // Criando um objeto Briefing simulado e usando métodos set
        Briefing briefing = new Briefing(); // Usando o construtor padrão
        briefing.setId(1L);
        briefing.setProject(project);
        briefing.setBriefingType(briefingType);
        briefing.setStartTime(LocalDate.now());
        briefing.setExpectedTime(LocalDate.now().plusDays(7));
        briefing.setFinishTime(null); // ou uma data válida, se necessário
        briefing.setDetailedDescription("Descrição detalhada do briefing");
        briefing.setOtherCompany("Outra empresa");

        // Criando um objeto BHandoutDetailedView com os objetos simulados
        BHandoutDetailedView bHandoutDetailedView = new BHandoutDetailedView(
            bHandoutView, 
            projectView, 
            new BriefingView(
                1L, // id
                new BriefingTypeView(1L, "Tipo de Briefing"), // briefingType
                LocalDate.now(), // startTime
                LocalDate.now().plusDays(7), // expectedTime
                null, // finishTime
                "Descrição detalhada do briefing", // detailedDescription
                null, // measurements
                null, // companies
                "Outra empresa", // otherCompanies
                null // versions
            )
        );

        // Configurando o comportamento dos mocks
        when(handoutTypeRepository.getReferenceById(1L)).thenReturn(handoutType);
        when(bHandoutFormMapper.map(bHandoutForm)).thenReturn(bHandout);
        when(bHandoutRepository.save(bHandout)).thenReturn(bHandout);
        when(bHandoutDetailedViewMapper.map(bHandout)).thenReturn(bHandoutDetailedView);

        // Chamando o método a ser testado
        bHandoutService.register(bHandoutForm, briefing); // Mantido para usar briefing

        // Verificando se o método save foi chamado
        verify(bHandoutRepository).save(bHandout);
        
           }
}