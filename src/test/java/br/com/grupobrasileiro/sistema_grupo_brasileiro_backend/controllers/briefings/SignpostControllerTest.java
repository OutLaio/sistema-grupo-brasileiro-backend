package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controllers.briefings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings.SignpostController;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form.BSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form.RegisterSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.signpost.BSignpostService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.dialogbox.DialogBoxService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.BriefingService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.ProjectService;

class SignpostControllerTest {

    @InjectMocks
    private SignpostController signpostController;

    @Mock
    private ProjectService projectService;

    @Mock
    private BriefingService briefingService;

    @Mock
    private BSignpostService signpostService;
    
    @Mock
    private DialogBoxService dialogBoxService;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    @DisplayName("Should register a new signpost successfully")
    void registerSignpostSuccessfully() {
        // Arrange: Configura os dados de entrada e os retornos dos serviços para o registro bem-sucedido
        Project mockProject = new Project();
        mockProject.setId(1L);

        Briefing mockBriefing = new Briefing();
        mockBriefing.setId(1L);

        RegisterSignpostForm registerSignpostForm = new RegisterSignpostForm(
                new ProjectForm(faker.number().randomNumber(), faker.company().name(), null),
                new BriefingForm(
                        faker.lorem().sentence(),
                        new HashSet<>(),
                        faker.company().name(),
                        faker.number().randomNumber(),
                        null
                ),
                new BSignpostForm(faker.number().randomNumber(), faker.address().fullAddress(), faker.lorem().word())
        );

        when(projectService.register(any(ProjectForm.class))).thenReturn(mockProject);
        when(briefingService.register(any(BriefingForm.class), any(Project.class))).thenReturn(mockBriefing);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        // Act: Executa o método de registro
        ResponseEntity<?> response = signpostController.registerSignpost(registerSignpostForm, uriBuilder);

        // Assert: Verifica o status e a localização do recurso
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertEquals(URI.create("/api/v1/projects/" + mockProject.getId()), response.getHeaders().getLocation());
    }

}
