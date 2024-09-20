//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.controller;
//
//
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Collections;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//public class CompanyControllerTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private CompanyService companyService;
//
//    @InjectMocks
//    private CompanyController companyController;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();
//    }
//
//    @Test
//    void testSaveCompany() throws Exception {
//        // Instância de CompanyForm
//        CompanyForm companyForm = new CompanyForm("Test Company");
//
//        // Instância de CompanyView com os campos esperados
//        CompanyView companyView = new CompanyView(1L, "Test Company");
//
//        // Mock do serviço para retornar o CompanyView esperado
//        when(companyService.save(any(CompanyForm.class))).thenReturn(companyView);
//
//        // Testando o endpoint para salvar a empresa
//        mockMvc.perform(post("/api/v1/companies/new")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(companyForm)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("Test Company"));
//    }
//
//    @Test
//    void testCompanyAll() throws Exception {
//        // Criação de um Page com uma lista de CompanyView
//        CompanyView companyView = new CompanyView(1L, "Test Company");
//        Page<CompanyView> page = new PageImpl<>(Collections.singletonList(companyView));
//
//        // Mock do serviço para retornar o Page esperado
//        when(companyService.companyAll(any(PageRequest.class))).thenReturn(page);
//
//        // Testando o endpoint para listar todas as empresas
//        mockMvc.perform(get("/api/v1/companies/all")
//                .param("page", "0")
//                .param("size", "10")
//                .param("direction", "ASC")
//                .param("orderBy", "name"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].id").value(1L))
//                .andExpect(jsonPath("$.content[0].name").value("Test Company"));
//    }
//
//    @Test
//    void testGetCompanyById() throws Exception {
//        Long id = 1L;
//        CompanyView companyView = new CompanyView(id, "Test Company");
//
//        // Mock do serviço para retornar o CompanyView esperado
//        when(companyService.getCompanyById(eq(id))).thenReturn(companyView);
//
//        // Testando o endpoint para buscar a empresa por ID
//        mockMvc.perform(get("/api/v1/companies/{id}", id))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("Test Company"));
//    }
//
//    @Test
//    void testUpdateCompany() throws Exception {
//        Long id = 1L;
//        CompanyForm companyForm = new CompanyForm("Updated Company Name");
//        CompanyView updatedCompanyView = new CompanyView(id, "Updated Company Name");
//
//        // Mock do serviço para retornar o CompanyView atualizado
//        when(companyService.updateCompany(eq(id), any(CompanyForm.class))).thenReturn(updatedCompanyView);
//
//        // Testando o endpoint para atualizar a empresa
//        mockMvc.perform(put("/api/v1/companies/{id}", id)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(companyForm)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("Updated Company Name"));
//    }
//}
