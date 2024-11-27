package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings.PrintedController;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.form.PrintedForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.form.RegisterPrintedForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.view.DialogBoxView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.printed.PrintedService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.dialogbox.DialogBoxService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.BriefingService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.ProjectService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import org.springframework.boot.test.mock.mockito.MockBean;

public class PrintedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PrintedController printedController;

    @Mock
    private ProjectService projectService;

    @Mock
    private BriefingService briefingService;

    @Mock
    private PrintedService printedService;

    @MockBean
    private DialogBoxService dialogBoxService;  

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(printedController).build();
        
        // Mock do comportamento esperado do PrintedService
        doNothing().when(printedService).register(any(PrintedForm.class), any(Briefing.class));
        
        // Mock do comportamento esperado do DialogBoxService (usando mock)
        when(dialogBoxService.createMessage(any())).thenReturn((DialogBoxView) mock(Object.class));  // Substituindo DialogBox por um mock simples
    }

    @Test
    @DisplayName("Should return 400 for invalid input data")
    void registerPrintedInvalidInput() throws Exception {
        // Arrange: Create an invalid RegisterPrintedForm JSON with null values
        String json = "{\"projectForm\": null, \"briefing\": null, \"printed\": null}";

        // Act & Assert: Perform a POST request and expect a 400 Bad Request response
        mockMvc.perform(post("/api/v1/printed")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should register a printed successfully")
    void registerPrintedSuccessfully() throws Exception {
        // Arrange: Prepare the input data and mocks
        ProjectForm projectForm = new ProjectForm(1L, "Test Project", null);
        BriefingForm briefingForm = new BriefingForm(
            "Test description",
            new HashSet<>(),
            null,
            1L,
            null
        );
        PrintedForm printedForm = new PrintedForm(1L, null, "A4", 2, 4);
        
        RegisterPrintedForm registerPrintedForm = new RegisterPrintedForm(projectForm, briefingForm, printedForm);
        
        Project mockProject = mock(Project.class);
        Briefing mockBriefing = mock(Briefing.class);
        
        when(projectService.register(any())).thenReturn(mockProject);
        when(briefingService.register(any(), any())).thenReturn(mockBriefing);
        when(mockProject.getId()).thenReturn(1L); // Definindo um ID para o projeto

        // Act: Perform the mock HTTP request
        mockMvc.perform(post("/api/v1/printed")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"projectForm\": {\"id\":1, \"name\":\"Test Project\"}, \"briefing\": {\"description\":\"Test description\"}, \"printed\": {\"id\":1, \"size\":\"A4\", \"quantity\":2}}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/projects/1"));
    }
}

