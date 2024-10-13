package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controllers.briefings;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.BriefingService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.ProjectService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;



class GiftControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProjectService projectService;

    @Mock
    private BriefingService briefingService;

    @Mock
    private BGiftService giftService;

    @InjectMocks
    private GiftController giftController;

    private Faker faker;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(giftController).build();
        this.faker = new Faker();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Teste para verificar se o registro de um presente é bem-sucedido (status 201).
     * Aqui, usamos o Java Faker para gerar dados fictícios para o formulário de presente.
     */
    @Test
    @DisplayName("You must register a new gift successfully - 201 Created")
    void shouldRegisterGiftSuccessfully() throws Exception {
        // Mock dos objetos e serviços
        RegisterGiftForm form = mockRegisterGiftForm();
        Project project = new Project(faker.company().name(), faker.lorem().paragraph());
        Briefing briefing = new Briefing(faker.lorem().sentence(), project);
        BGiftDetailedView giftView = new BGiftDetailedView(new BGiftView(faker.number().randomNumber()));

        // Simulação do comportamento dos serviços
        when(projectService.register(any())).thenReturn(project);
        when(briefingService.register(any(), any(Project.class))).thenReturn(briefing);
        when(giftService.register(any(), any(Briefing.class))).thenReturn(giftView);

        // Simulação de uma requisição POST
        mockMvc.perform(post("/api/v1/bgifts")
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
        RegisterGiftForm form = new RegisterGiftForm(null, null, null); // Formulário com dados inválidos

        mockMvc.perform(post("/api/v1/bgifts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isBadRequest()); // Espera o status 400 Bad Request
    }

    /**
     * Teste para verificar o comportamento quando ocorre um erro no servidor (status 500).
     * Simulamos uma exceção nos serviços de registro.
     */
    @Test
    @DisplayName("Should return 500 Internal Server Error when a server error occurs")
    void shouldReturnServerErrorOnServiceFailure() throws Exception {
        RegisterGiftForm form = mockRegisterGiftForm();

        // Simulação de uma exceção nos serviços
        when(projectService.register(any())).thenThrow(new RuntimeException("Erro no servidor"));

        mockMvc.perform(post("/api/v1/bgifts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isInternalServerError()); // Espera o status 500 Internal Server Error
    }

    /**
     * Função auxiliar para criar um RegisterGiftForm mockado usando o Java Faker.
     */
    private RegisterGiftForm mockRegisterGiftForm() {
        return new RegisterGiftForm(
                faker.lorem().sentence(), // Dados para o ProjectForm
                faker.lorem().paragraph(), // Dados para o BriefingForm
                faker.commerce().productName() // Dados para o GiftForm
        );
    }
}
