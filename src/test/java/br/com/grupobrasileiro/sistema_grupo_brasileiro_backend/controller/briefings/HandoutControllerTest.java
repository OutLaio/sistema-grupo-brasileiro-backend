package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings;


import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller.briefings.HandoutController;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.form.BHandoutForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.form.RegisterHandoutForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.BHandoutDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.BHandoutView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.HandoutTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.form.CompaniesBriefingsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.view.CompaniesBriefingsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.form.MeasurementsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.measurements.view.MeasurementsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.BriefingForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.ProjectForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.VersionView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.EmployeeSimpleView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.enums.ProjectStatusEnum;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.security.TokenService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.bHandout.BHandoutService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.BriefingService;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.project.ProjectService;


@WebMvcTest(HandoutController.class)
@AutoConfigureMockMvc(addFilters = false)
public class HandoutControllerTest {

    @MockBean
    private TokenService tokenService;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private BriefingService briefingService;

    @MockBean
    private BHandoutService bHandoutService;

    @MockBean
    private UserRepository userRepository; 

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    public void testRegisterHandout() throws Exception {
        
        Project project = new Project(); 
        project.setId(1L); 

        Briefing briefing = new Briefing();
        briefing.setId(1L); 

        // Configuração dos mocks
        ProjectForm projectForm = new ProjectForm(1L, "Projeto Exemplo", ProjectStatusEnum.TO_DO);
        MeasurementsForm measurementsForm = new MeasurementsForm(BigDecimal.valueOf(1.75), BigDecimal.valueOf(2.0));
        Set<CompaniesBriefingsForm> companies = new HashSet<>();
        companies.add(new CompaniesBriefingsForm(1L)); 

        BriefingForm briefingForm = new BriefingForm(
            "Descrição detalhada do briefing",
            companies, 
            "Outra empresa",
            1L, 
            measurementsForm
        );

        BHandoutForm handoutForm = new BHandoutForm(1L); 
        RegisterHandoutForm registerHandoutForm = new RegisterHandoutForm(projectForm, briefingForm, handoutForm);

        // Inicialize as instâncias necessárias para BHandoutDetailedView
        HandoutTypeView handoutTypeView = new HandoutTypeView(1L, "Tipo de Comunicado");
        BHandoutView bHandoutView = new BHandoutView(1L, handoutTypeView);

        EmployeeSimpleView client = new EmployeeSimpleView(1L, "Cliente Exemplo", null);
        EmployeeSimpleView collaborator = new EmployeeSimpleView(2L, "Colaborador Exemplo", null);
        ProjectView projectView = new ProjectView(1L, "Projeto Exemplo", "TO_DO", client, collaborator, null, LocalDate.now());

        BriefingTypeView briefingTypeView = new BriefingTypeView(1L, "Tipo de Briefing");
        MeasurementsView measurementsView = new MeasurementsView(BigDecimal.valueOf(1.75), BigDecimal.valueOf(2.0));
        Set<CompanyView> companyViews = new HashSet<>();
        companyViews.add(new CompanyView(1L, "Empresa Exemplo"));
        CompaniesBriefingsView companiesBriefingsView = new CompaniesBriefingsView(companyViews);

        Set<VersionView> versions = new HashSet<>(); 
        BriefingView briefingView = new BriefingView(
            1L,
            briefingTypeView,
            LocalDate.now(),
            LocalDate.now().plusDays(1),
            LocalDate.now().plusDays(2),
            "Descrição detalhada",
            measurementsView,
            companiesBriefingsView,
            "Outras empresas",
            versions 
        );

        // Crie a instância de BHandoutDetailedView
        BHandoutDetailedView bHandoutDetailedView = new BHandoutDetailedView(bHandoutView, projectView, briefingView);
        
        // Configuração dos mocks
        when(projectService.register(any(ProjectForm.class))).thenReturn(project);
        when(briefingService.register(any(BriefingForm.class), any(Project.class))).thenReturn(briefing);
        
        // Se o método register do bHandoutService for void, use doNothing()
        doNothing().when(bHandoutService).register(any(BHandoutForm.class), any(Briefing.class));

        // Executa a requisição e verifica a resposta
        mockMvc.perform(post("/api/v1/handouts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerHandoutForm)))
                .andExpect(status().isCreated()) // Verifica se o status é 201
                .andExpect(header().string("Location", containsString("/api/v1/projects/1"))); // Verifica o cabeçalho "Location" para o projeto

        // Verifica se os serviços foram chamados corretamente
        verify(projectService, times(1)).register(any(ProjectForm.class));
        verify(briefingService, times(1)).register(any(BriefingForm.class), any(Project.class));
        verify(bHandoutService, times(1)).register(any(BHandoutForm.class), any(Briefing.class));
    }
}