package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controllers.briefings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings.BAgencyBoardController;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.BAgencyBoardsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.RegisterAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.agencyBoard.BAgencyBoardService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.BriefingService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.ProjectService;

class BAgencyBoardControllerTest {

    @InjectMocks
    private BAgencyBoardController bAgencyBoardController;

    @Mock
    private ProjectService projectService;

    @Mock
    private BriefingService briefingService;

    @Mock
    private BAgencyBoardService bAgencyBoardService;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }
   
    @Test
    @DisplayName("Should register a new agency board successfully")
    void registerAgencyBoardSuccessfully() {
        // Arrange
        // Criando o ProjectForm. O status será automaticamente definido como TO_DO devido ao comportamento do construtor.
        RegisterAgencyBoard registerAgencyBoard = new RegisterAgencyBoard(
            new ProjectForm(faker.number().randomNumber(), faker.company().name(), null), // idClient, title, status = null, que será tratado na classe ProjectForm
            new BriefingForm(
                faker.lorem().sentence(),
                new HashSet<>(),  // Briefing companies or details
                null,  // other fields like startTime
                1L,    // briefingTypeId
                null   // other fields
            ),
            new BAgencyBoardsForm(null, null, faker.lorem().sentence(), faker.lorem().sentence(), null, null) // AgencyBoard form fields
        );

        // Mock dos objetos Project e Briefing
        Project mockProject = new Project();
        mockProject.setId(1L); // Setando ID fictício
        mockProject.setTitle(faker.company().name());
        mockProject.setStatus("TO_DO"); // Status do projeto, que deve ser TO_DO se não for passado outro status

        // Criando objetos mock para EmployeeSimpleView
        EmployeeSimpleView mockClient = new EmployeeSimpleView(1L, faker.name().fullName(), 123L); // Usando um ID, nome e avatar válidos
        EmployeeSimpleView mockCollaborator = new EmployeeSimpleView(2L, faker.name().fullName(), 456L); // Usando um ID, nome e avatar válidos

        Briefing mockBriefing = new Briefing();
        mockBriefing.setId(1L); 
        mockBriefing.setStartTime(null);  
        mockBriefing.setExpectedTime(null);  
        mockBriefing.setFinishTime(null);  
        mockBriefing.setDetailedDescription(faker.lorem().sentence());  

        // Criando e configurando o BriefingType
        BriefingType mockBriefingType = new BriefingType();
        mockBriefingType.setId(1L);
        mockBriefingType.setDescription("Tipo de Briefing Mockado");

        // Associando o tipo de briefing
        mockBriefing.setBriefingType(mockBriefingType);

        // Criando a view esperada
        BAgencyBoardDetailedView mockView = new BAgencyBoardDetailedView(
            new BAgencyBoardView(1L, null, null, null, null, faker.address().fullAddress(), faker.lorem().sentence()),
            
            // Corrigido para passar instâncias válidas de EmployeeSimpleView
            new ProjectView(
                mockProject.getId(),
                mockProject.getTitle(),
                mockProject.getStatus(),
                mockClient,  // Passando um mock de EmployeeSimpleView com id, fullName e avatar válidos
                mockCollaborator,  // Passando um mock de EmployeeSimpleView com id, fullName e avatar válidos
                "Briefing Type Example"  // Passando um valor válido para 'briefingType'
            ),
            
            new BriefingView(
                mockBriefing.getId(),
                new BriefingTypeView(mockBriefing.getBriefingType().getId(), mockBriefing.getBriefingType().getDescription()),
                mockBriefing.getStartTime(),
                mockBriefing.getExpectedTime(),
                mockBriefing.getFinishTime(),
                mockBriefing.getDetailedDescription(),
                null, // MeasurementsView
                null, // CompaniesBriefingsView
                mockBriefing.getOtherCompany(),
                new HashSet<>() // versions
            )
        );

        // Mock de comportamentos dos serviços
        when(projectService.register(any())).thenReturn(mockProject);
        when(briefingService.register(any(), any())).thenReturn(mockBriefing);
        doNothing().when(bAgencyBoardService).register(any(), any());

        // Simulando o builder do URI
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        // Act - Chamada ao controller
        ResponseEntity<?> response = bAgencyBoardController.registerSignpost(registerAgencyBoard, uriBuilder);

        // Assert - Verificação dos resultados
        assertEquals(201, response.getStatusCodeValue()); // Verificando o código de status
        assertEquals(mockView, response.getBody());  // Verificando o corpo da resposta
    }
}