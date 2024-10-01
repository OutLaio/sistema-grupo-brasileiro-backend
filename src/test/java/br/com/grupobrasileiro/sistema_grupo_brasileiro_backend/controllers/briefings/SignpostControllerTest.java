package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controllers.briefings;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings.SignpostController;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form.BSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form.RegisterSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostView; // Importar BSignpostView
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.MaterialView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm; // Importar BriefingForm
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm; // Importar ProjectForm
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.Material; // Importar a classe Material
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

import java.net.URI;
import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SignpostControllerTest {

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

    @Test
    @DisplayName("Should register a new signpost successfully")
    void registerSignpostSuccessfully() {
        // Arrange
        // Criando o formulário de cadastro com dados fictícios
        RegisterSignpostForm registerSignpostForm = new RegisterSignpostForm(
                new ProjectForm(faker.number().randomNumber(), faker.company().name(), null),
                new BriefingForm(
                        LocalDate.now().plusDays(10),
                        faker.lorem().sentence(),
                        new HashSet<>(),
                        null,
                        1L,
                        null
                ),
                new BSignpostForm(
                        faker.number().randomNumber(), 
                        faker.address().fullAddress(), 
                        faker.lorem().word()           
                )
        );

        // Mocking the necessary objects
        Project mockProject = new Project();
        Briefing mockBriefing = new Briefing();
        Material mockMaterial = new Material(); 

        // Criando um mock de BSignpost com os parâmetros corretos
        BSignpost mockSignpost = new BSignpost(
                1L, 
                mockMaterial, 
                mockBriefing,
                registerSignpostForm.signpostForm().boardLocation(),
                registerSignpostForm.signpostForm().sector() 
        );

 
     // Criando um mock para MaterialView
        MaterialView mockMaterialView = new MaterialView(
                faker.number().randomNumber(),  
                faker.lorem().sentence()        
        );

        // Criando um mock para BSignpostView
        BSignpostView mockSignpostView = new BSignpostView(
                mockSignpost.getId(),          
                mockMaterialView,               
                registerSignpostForm.signpostForm().boardLocation(),
                registerSignpostForm.signpostForm().sector()
        );




        // Criando um mock para BriefingView
        BriefingView mockBriefingView = new BriefingView(
                mockBriefing.getId(),
                null,
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                LocalDate.now(),
                faker.lorem().sentence()
        );

        
        BSignpostDetailedView mockView = new BSignpostDetailedView(
                mockSignpostView, 
                null, 
                mockBriefingView 
        );

        // Configurando o comportamento dos mocks
        when(projectService.register(any())).thenReturn(mockProject);
        when(briefingService.register(any(), any())).thenReturn(mockBriefing);
        when(signpostService.register(any(), any())).thenReturn(mockView);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        // Act
        ResponseEntity<BSignpostDetailedView> response = signpostController.registerSignpost(registerSignpostForm, uriBuilder);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(mockView, response.getBody());
        URI expectedUri = uriBuilder.path("/api/v1/signposts/{id}").buildAndExpand(mockView.bSignpostView().id()).toUri();
        assertEquals(expectedUri, response.getHeaders().getLocation());
    }
}
