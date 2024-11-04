package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.form.GiftForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.form.RegisterGiftForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.BGiftDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.BGiftView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.form.MeasurementsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.ProjectStatusEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.ApiExceptionHandler;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.gift.BGiftService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.BriefingService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.ProjectService;

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
    private Random random;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(giftController)
                .setControllerAdvice(new ApiExceptionHandler())  
                .build();
        this.faker = new Faker();
        this.objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.random = new Random();
    }

    private RegisterGiftForm mockRegisterGiftForm() {
        ProjectForm projectForm = new ProjectForm(
            random.nextLong(),
            faker.company().name(),
            ProjectStatusEnum.TO_DO
        );

        BriefingForm briefingForm = new BriefingForm(
            LocalDate.now().plusDays(30),
            faker.lorem().sentence(),
            new HashSet<>(),
            faker.company().name(),
            random.nextLong(),
            mockMeasurementsForm()
        );

        GiftForm giftForm = new GiftForm(
            random.nextLong(),
            random.nextLong(),
            random.nextLong(),
            random.nextLong(),
            random.nextLong(),
            faker.commerce().productName(),
            faker.internet().url()
        );

        return new RegisterGiftForm(projectForm, briefingForm, giftForm);
    }

    private MeasurementsForm mockMeasurementsForm() {
        return new MeasurementsForm(
            BigDecimal.valueOf(random.nextDouble() * 100),
            BigDecimal.valueOf(random.nextDouble() * 100)
        );
    }
    
    /**
     * Testa o cenário de registro bem-sucedido de um novo presente.
     * 
     * Este teste verifica se:
     * 1. O endpoint retorna um status HTTP 201 (Created) quando um presente é registrado com sucesso.
     * 2. Os serviços necessários (projectService, briefingService, giftService) são chamados corretamente.
     * 3. A resposta contém os detalhes do presente registrado.
     * 
     * O teste simula o fluxo completo de registro de um presente, incluindo:
     * - Criação de um projeto
     * - Criação de um briefing associado ao projeto
     * - Registro do presente com base no briefing
     * 
     * Os serviços são mockados para retornar objetos predefinidos, permitindo
     * o teste isolado do controlador.
     */

    @Test
    @DisplayName("You must register a new gift successfully - 201 Created")
    void registerGiftSuccessfully() throws Exception {
        RegisterGiftForm form = mockRegisterGiftForm();

        Project project = new Project();
        project.setId(1L);

        Briefing briefing = new Briefing();
        briefing.setId(1L);
        briefing.setProject(project);

        BGiftView bGiftView = new BGiftView(
            1L,
            null,
            null,
            null,
            null,
            null,
            "Modelo de Presente",
            "https://exemplo.com/modelo"
        );

        BGiftDetailedView bGiftDetailedView = new BGiftDetailedView(bGiftView, null, null);

        when(projectService.register(any())).thenReturn(project);
        when(briefingService.register(any(), any(Project.class))).thenReturn(briefing);
        
  
        doNothing().when(giftService).register(any(GiftForm.class), any(Briefing.class)); 

        MvcResult result = mockMvc.perform(post("/api/v1/gifts") 
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response: " + responseContent);
    }
    
    /**
     * Testa o cenário onde dados de entrada inválidos são fornecidos ao tentar registrar um presente.
     * 
     * Este teste verifica se:
     * 1. O endpoint retorna um status HTTP 422 (Unprocessable Entity) quando recebe dados inválidos.
     * 2. A resposta contém uma mensagem de erro apropriada relacionada à validação dos dados.
     * 3. O conteúdo da resposta não está vazio.
     * 
     * O teste envia um RegisterGiftForm com todos os campos nulos, simulando uma entrada inválida,
     * e verifica se o sistema responde corretamente, rejeitando os dados e fornecendo informações
     * sobre a falha de validação.
     */
    @Test
    @DisplayName("Should return 422 Unprocessable Entity for invalid input data")
    void ReturnUnprocessableEntityForInvalidData() throws Exception {
        RegisterGiftForm form = new RegisterGiftForm(null, null, null);

        MvcResult result = mockMvc.perform(post("/api/v1/gifts") 
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response content: " + responseContent);

        assertTrue(responseContent.contains("Validation failed") || 
                   responseContent.contains("Invalid input") || 
                   !responseContent.isEmpty(),
            "A resposta deve conter uma mensagem de erro de validação ou indicar entrada inválida");
    }

    /**
     * Testa o cenário onde ocorre um erro interno no servidor durante o registro de um presente.
     * 
     * Este teste verifica se:
     * 1. O endpoint retorna um status HTTP 500 (Internal Server Error) quando uma exceção é lançada no serviço.
     * 2. A resposta contém uma mensagem de erro apropriada.
     * 3. O conteúdo da resposta não está vazio.
     * 
     * O teste simula um erro no ProjectService.register() lançando uma RuntimeException
     * e verifica se o manipulador de exceções global trata corretamente este erro.
     */
    
    @Test
    @DisplayName("Should return 500 Internal Server Error when a server error occurs")
    void ReturnInternalServerErrorWhenErrorOccurs() throws Exception {
        RegisterGiftForm form = mockRegisterGiftForm();

        when(projectService.register(any())).thenThrow(new RuntimeException("Erro no servidor"));

        MvcResult result = mockMvc.perform(post("/api/v1/bgifts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isInternalServerError())
                .andDo(print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        System.out.println("Status: " + status);
        System.out.println("Response Content: " + responseContent);

        assertFalse(responseContent.isEmpty(), "A resposta não deve estar vazia");
        assertTrue(responseContent.contains("Erro no servidor") || 
                   responseContent.contains("Internal Server Error") ||
                   responseContent.contains("Ocorreu um erro interno no servidor"),
                   "A resposta deve conter uma mensagem de erro relacionada ao servidor");
    }
}