package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controllers.briefings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.BriefingService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.ProjectService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.bHandout.BHandoutService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.BHandoutDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.form.RegisterHandoutForm;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class HandoutControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProjectService projectService;

    @Mock
    private BriefingService briefingService;

    @Mock
    private BHandoutService bHandoutService;

    @InjectMocks
    private HandoutController handoutController;

    private Faker faker;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(handoutController).build();
        this.faker = new Faker();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Teste para verificar se o registro de um handout é bem-sucedido (status 201).
     */
    @Test
    @DisplayName("Must register a new handout successfully - 201 Created")
    void shouldRegisterHandoutSuccessfully() throws Exception {
        // Mock dos objetos e serviços
        RegisterHandoutForm form = mockRegisterHandoutForm();
        Project project = new Project(faker.company().name(), faker.lorem().paragraph());
        Briefing briefing = new Briefing(faker.lorem().sentence(), project);
        BHandoutDetailedView handoutView = new BHandoutDetailedView(new BHandout(faker.number().randomNumber()));

        // Simulação do comportamento dos serviços
        when(projectService.register(any())).thenReturn(project);
        when(briefingService.register(any(), any(Project.class))).thenReturn(briefing);
        when(bHandoutService.register(any(), any(Briefing.class))).thenReturn(handoutView);

        // Simulação de uma requisição POST
        mockMvc.perform(post("/api/v1/handouts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isCreated()); // Espera o status 201 Created
    }

    /**
     * Teste para verificar o comportamento quando os dados do formulário são inválidos (status 400).
     */
    @Test
    @DisplayName("Should return 400 Bad Request for invalid input data")
    void shouldReturnBadRequestForInvalidData() throws Exception {
        RegisterHandoutForm form = new RegisterHandoutForm(null, null, null); // Formulário com dados inválidos

        mockMvc.perform(post("/api/v1/handouts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isBadRequest()); // Espera o status 400 Bad Request
    }

    /**
     * Teste para verificar o comportamento quando ocorre um erro no servidor (status 500).
     */
    @Test
    @DisplayName("Should return 500 Internal Server Error when a server error occurs")
    void shouldReturnServerErrorOnServiceFailure() throws Exception {
        RegisterHandoutForm form = mockRegisterHandoutForm();

        // Simulação de uma exceção nos serviços
        when(projectService.register(any())).thenThrow(new RuntimeException("Erro no servidor"));

        mockMvc.perform(post("/api/v1/handouts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isInternalServerError()); // Espera o status 500 Internal Server Error
    }

    /**
     * Função auxiliar para criar um RegisterHandoutForm mockado usando o Java Faker.
     */
    private RegisterHandoutForm mockRegisterHandoutForm() {
        return new RegisterHandoutForm(
                faker.lorem().sentence(), // Dados para o ProjectForm
                faker.lorem().paragraph(), // Dados para o BriefingForm
                faker.commerce().productName() // Dados para o HandoutForm
        );
    }
}
