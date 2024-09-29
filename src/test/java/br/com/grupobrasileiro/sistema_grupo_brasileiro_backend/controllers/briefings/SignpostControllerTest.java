package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controllers.briefings;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings.SignpostController;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form.RegisterSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.signpost.BSignpostService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SignpostControllerTest {

    @InjectMocks
    private SignpostController signpostController;

    @Mock
    private ProjectService projectService;

    @Mock
    private BriefingService briefingService;

    @Mock
    private BSignpostService signpostService;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @SuppressWarnings("deprecation")
    @Test
    @DisplayName("Should register a new signpost successfully")
    void shouldRegisterSignpostSuccessfully() {
        // Arrange
        RegisterSignpostForm registerSignpost = new RegisterSignpostForm(
                new ProjectForm(faker.number().randomNumber(), faker.company().name(), null), // idClient, title, status
                new BriefingForm(LocalDateTime.now().plusDays(10), faker.lorem().sentence(), new HashSet<>(), null, 1L, null), // expectedDate, detailedDescription, companies, otherCompany, idBriefingType, measurement
                null // Preencha conforme necessário para o signpostForm
        );

        Project mockProject = new Project(); // Crie e preencha o mock conforme necessário
        Briefing mockBriefing = new Briefing(); // Crie e preencha o mock conforme necessário
        BSignpostDetailedView mockView = new BSignpostDetailedView(
                new BSignpostView(1L, null, faker.lorem().sentence(), faker.lorem().sentence()), // Crie e preencha conforme necessário
                new ProjectView(mockProject.getId(), mockProject.getTitle(), mockProject.getStatus(), null, null),
                new BriefingView(mockBriefing.getId(), null, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), faker.lorem().sentence())
        );

        when(projectService.register(any())).thenReturn(mockProject);
        when(briefingService.register(any(), any())).thenReturn(mockBriefing);
        when(signpostService.register(any(), any())).thenReturn(mockView);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        // Act
        ResponseEntity<BSignpostDetailedView> response = signpostController.registerSignpost(registerSignpost, uriBuilder);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(mockView, response.getBody());
    }

   
    @Test
    @DisplayName("Should call the correct services when registering a signpost")
    void shouldCallServicesWhenRegisteringSignpost() {
        // Arrange
        RegisterSignpostForm registerSignpost = new RegisterSignpostForm(
                new ProjectForm(faker.number().randomNumber(), faker.company().name(), null),
                new BriefingForm(LocalDateTime.now().plusDays(10), faker.lorem().sentence(), new HashSet<>(), null, 1L, null),
                null
        );

        Project mockProject = new Project();
        Briefing mockBriefing = new Briefing();
        BSignpostDetailedView mockView = new BSignpostDetailedView(
                new BSignpostView(1L, null, faker.lorem().sentence(), faker.lorem().sentence()),
                new ProjectView(mockProject.getId(), mockProject.getTitle(), mockProject.getStatus(), null, null),
                new BriefingView(mockBriefing.getId(), null, LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), faker.lorem().sentence())
        );

        when(projectService.register(any())).thenReturn(mockProject);
        when(briefingService.register(any(), any())).thenReturn(mockBriefing);
        when(signpostService.register(any(), any())).thenReturn(mockView);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        // Act
        signpostController.registerSignpost(registerSignpost, uriBuilder);

        // Assert
        verify(projectService).register(any());
        verify(briefingService).register(any(), any());
        verify(signpostService).register(any(), any());
    }


}
