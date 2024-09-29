package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controllers.briefings;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings.BAgencyBoardController;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.BAgencyBoardsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.RegisterAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BAgencyBoardView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.agencyBoard.BAgencyBoardService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.BriefingService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.ProjectService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    @SuppressWarnings("deprecation")
    @DisplayName("Should register a new agency board successfully")
    void shouldRegisterAgencyBoardSuccessfully() {
        // Arrange
        RegisterAgencyBoard registerAgencyBoard = new RegisterAgencyBoard(
                new ProjectForm(faker.number().randomNumber(), faker.company().name(), null), // idClient, title, status
                new BriefingForm(LocalDateTime.now().plusDays(10), faker.lorem().sentence(), new HashSet<>(), null, 1L, null), // expectedDate, detailedDescription, companies, otherCompany, idBriefingType, measurement
                new BAgencyBoardsForm(null, null, faker.lorem().sentence(), faker.lorem().sentence(), null, null) // Preencha conforme necessário
        );

        Project mockProject = new Project(); // Crie e preencha o mock conforme necessário
        Briefing mockBriefing = new Briefing(); // Crie e preencha o mock conforme necessário
        BAgencyBoardDetailedView mockView = new BAgencyBoardDetailedView(
                new BAgencyBoardView(1L, null, null, null, null, faker.address().fullAddress(), faker.lorem().sentence()), 
                new ProjectView(mockProject.getId(), mockProject.getTitle(), mockProject.getStatus(), null, null),
                new BriefingView(mockBriefing.getId(), null, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), faker.lorem().sentence())
        );

        when(projectService.register(any())).thenReturn(mockProject);
        when(briefingService.register(any(), any())).thenReturn(mockBriefing);
        when(bAgencyBoardService.register(any(), any())).thenReturn(mockView);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        // Act
        ResponseEntity<BAgencyBoardDetailedView> response = bAgencyBoardController.registerSignpost(registerAgencyBoard, uriBuilder);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(mockView, response.getBody());
    }
}
