package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controllers.briefings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashSet;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.Response;
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
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.dialogbox.DialogBoxService;
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

    @Mock
    private DialogBoxService dialogBoxService;

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
        RegisterAgencyBoard registerAgencyBoard = new RegisterAgencyBoard(
            new ProjectForm(faker.number().randomNumber(), faker.company().name(), null),
            new BriefingForm(
                faker.lorem().sentence(),
                new HashSet<>(),
                null,
                1L,
                null
            ),
            new BAgencyBoardsForm(null, null, faker.lorem().sentence(), faker.lorem().sentence(), null, null)
        );

        Project mockProject = new Project();
        mockProject.setId(1L);
        mockProject.setTitle(faker.company().name());
        mockProject.setStatus("TO_DO");

        EmployeeSimpleView mockClient = new EmployeeSimpleView(1L, faker.name().fullName(), 123L);
        EmployeeSimpleView mockCollaborator = new EmployeeSimpleView(2L, faker.name().fullName(), 456L);

        Briefing mockBriefing = new Briefing();
        mockBriefing.setId(1L);
        mockBriefing.setStartTime(null);  
        mockBriefing.setExpectedTime(null);  
        mockBriefing.setFinishTime(null);  
        mockBriefing.setDetailedDescription(faker.lorem().sentence());

        BriefingType mockBriefingType = new BriefingType();
        mockBriefingType.setId(1L);
        mockBriefingType.setDescription("Tipo de Briefing Mockado");

        mockBriefing.setBriefingType(mockBriefingType);

        BAgencyBoardDetailedView mockView = new BAgencyBoardDetailedView(
            new BAgencyBoardView(1L, null, null, null, null, faker.address().fullAddress(), faker.lorem().sentence()),
            new ProjectView(
                mockProject.getId(),
                mockProject.getTitle(),
                mockProject.getStatus(),
                mockClient,
                mockCollaborator,
                "Briefing Type Example",
                LocalDate.now()
            ),
            new BriefingView(
                mockBriefing.getId(),
                new BriefingTypeView(mockBriefing.getBriefingType().getId(), mockBriefing.getBriefingType().getDescription()),
                mockBriefing.getStartTime(),
                mockBriefing.getExpectedTime(),
                mockBriefing.getFinishTime(),
                mockBriefing.getDetailedDescription(),
                null,
                null,
                mockBriefing.getOtherCompany(),
                new HashSet<>()
            )
        );

        when(projectService.register(any())).thenReturn(mockProject);
        when(briefingService.register(any(), any())).thenReturn(mockBriefing);
        doNothing().when(bAgencyBoardService).register(any(), any());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        ResponseEntity<?> response = bAgencyBoardController.registerSignpost(registerAgencyBoard, uriBuilder);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(new Response<>("Nova solicitação criada com sucesso!"), response.getBody());
    }
}
