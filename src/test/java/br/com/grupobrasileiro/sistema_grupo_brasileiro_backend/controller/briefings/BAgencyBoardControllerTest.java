package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.view.DialogBoxView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.BAgencyBoardsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.RegisterAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.agencyBoard.BAgencyBoardService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.dialogbox.DialogBoxService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.BriefingService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.ProjectService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

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
    void registerSignpostSuccessfully() {
        // Teste para registrar um novo sinalizador com dados válidos
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
        mockProject.setId(faker.number().randomNumber());

        Briefing mockBriefing = new Briefing();

        when(projectService.register(any())).thenReturn(mockProject);
        when(briefingService.register(any(), any())).thenReturn(mockBriefing);
        doNothing().when(bAgencyBoardService).register(any(), any()); // Usando doNothing para métodos void

        DialogBoxView mockDialogBox = new DialogBoxView(null, null, null, null);
        when(dialogBoxService.createMessage(any())).thenReturn(mockDialogBox);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        // Act
        ResponseEntity<?> response = bAgencyBoardController.registerSignpost(registerAgencyBoard, uriBuilder);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(URI.create("/api/v1/projects/" + mockProject.getId()), response.getHeaders().getLocation());
    }
}