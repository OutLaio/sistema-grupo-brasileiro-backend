package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal; // Importação necessária
import java.time.LocalDate;
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

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form.BSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.form.RegisterSignpostForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.BSignpostView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.MaterialView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.view.CompaniesBriefingsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.view.MeasurementsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.BSignpost;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.Material;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.signpost.BSignpostService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.BriefingService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.ProjectService;

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
    @DisplayName("Must register a new sign successfully")
    void registerSignpostSuccessfully() {
        // Arrange
        RegisterSignpostForm registerSignpostForm = new RegisterSignpostForm(
                new ProjectForm(
                        faker.number().randomNumber(),
                        faker.company().name(),
                        null 
                ),
                new BriefingForm(
                        LocalDate.now().plusDays(10),
                        faker.lorem().sentence(),
                        new HashSet<>(), 
                        faker.company().name(), 
                        faker.number().randomNumber(), 
                        null 
                ),
                new BSignpostForm(
                        faker.number().randomNumber(), 
                        faker.address().fullAddress(), 
                        faker.lorem().word()           
                )
        );

        Project mockProject = new Project();
        mockProject.setId(1L); 
        Briefing mockBriefing = new Briefing();
        mockBriefing.setId(1L); 
        Material mockMaterial = new Material(); 

        BSignpost mockSignpost = new BSignpost(
                1L, 
                mockMaterial, 
                mockBriefing,
                registerSignpostForm.signpostForm().boardLocation(),
                registerSignpostForm.signpostForm().sector()
        );

        MaterialView mockMaterialView = new MaterialView(
                faker.number().randomNumber(),  
                faker.lorem().sentence()        
        );
        
        BSignpostView mockSignpostView = new BSignpostView(
                mockSignpost.getId(),          
                mockMaterialView,               
                registerSignpostForm.signpostForm().boardLocation(),
                registerSignpostForm.signpostForm().sector()
        );

        BriefingView mockBriefingView = new BriefingView(
                mockBriefing.getId(),
                new BriefingTypeView(1L, "Tipo de Briefing"), 
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(20),
                faker.lorem().sentence(),
                new MeasurementsView(BigDecimal.valueOf(1.75), BigDecimal.valueOf(2.0)), 
                new CompaniesBriefingsView(new HashSet<>()), 
                "Outras empresas", 
                new HashSet<>() 
        );

        ProjectView mockProjectView = new ProjectView(
                mockProject.getId(),
                faker.company().name(),
                "TO_DO",
                new EmployeeSimpleView(
                    faker.number().randomNumber(), 
                    faker.name().fullName(), 
                    faker.number().randomNumber() 
                ),
                null // collaborator
        );

        BSignpostDetailedView mockView = new BSignpostDetailedView(
                mockSignpostView, 
                mockProjectView, 
                mockBriefingView 
        );

     // Exemplo de ajuste no teste
        when(projectService.register(any())).thenReturn(mockProject);
        when(briefingService.register(any(), any())).thenReturn(mockBriefing);

        // Se o método signpostService.register for void, use doNothing()
        doNothing().when(signpostService).register(any(), any());

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

        // Act
        ResponseEntity<BSignpostDetailedView> response = signpostController.registerSignpost(registerSignpostForm, uriBuilder);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(null, response.getBody()); 

        // Verifique se o cabeçalho Location está presente
        assertNotNull(response.getHeaders().getLocation());

        // Compare apenas o caminho do URI, ignorando a parte do host
        String expectedPath = "/api/v1/projects/" + mockProject.getId();
        String actualPath = response.getHeaders().getLocation().getPath();
        assertEquals(expectedPath, actualPath);
    }
}