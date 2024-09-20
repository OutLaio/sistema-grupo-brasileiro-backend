//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.CompanyForm;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.CompanyView;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.CompanyAlreadyExistsException;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.CompanyFormMapper;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.CompanyViewMapper;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Company;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.CompanyRepository;
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.CompanyService;
//
//@SpringBootTest
//public class CompanyServiceTest {
//
//    @Mock
//    private CompanyRepository companyRepository;
//
//    @Mock
//    private CompanyFormMapper companyFormMapper;
//
//    @Mock
//    private CompanyViewMapper companyViewMapper;
//
//    @InjectMocks
//    private CompanyService companyService;
//
//    private Company company;
//    private CompanyForm companyForm;
//    private CompanyView companyView;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        // Initialize entities and forms
//        company = new Company();
//        company.setId(1L);
//        company.setName("COMPANY");
//
//        companyForm = new CompanyForm(
//            "Company"
//        );
//
//        companyView = new CompanyView(
//            1L,
//            "COMPANY"
//        );
//    }
//
//    @Test
//    public void testCompanyAll() {
//        Page<Company> companyPage = new PageImpl<>(List.of(company));
//        when(companyRepository.findAll(any(PageRequest.class))).thenReturn(companyPage);
//        when(companyViewMapper.map(any(Company.class))).thenReturn(companyView);
//
//        Page<CompanyView> result = companyService.companyAll(PageRequest.of(0, 10));
//
//        assertThat(result.getContent()).containsExactly(companyView);
//        verify(companyRepository, times(1)).findAll(any(PageRequest.class));
//        verify(companyViewMapper, times(1)).map(company);
//    }
//
//    @Test
//    public void testSave() {
//        when(companyFormMapper.map(any(CompanyForm.class))).thenReturn(company);
//        when(companyRepository.findByName(any(String.class))).thenReturn(null);
//        when(companyRepository.save(any(Company.class))).thenReturn(company);
//        when(companyViewMapper.map(any(Company.class))).thenReturn(companyView);
//
//        CompanyView result = companyService.save(companyForm);
//
//        assertThat(result).isEqualTo(companyView);
//        verify(companyFormMapper, times(1)).map(companyForm);
//        verify(companyRepository, times(1)).findByName("COMPANY");
//        verify(companyRepository, times(1)).save(company);
//        verify(companyViewMapper, times(1)).map(company);
//    }
//
//    @Test
//    public void testSaveCompanyAlreadyExists() {
//        when(companyFormMapper.map(any(CompanyForm.class))).thenReturn(company);
//        when(companyRepository.findByName(any(String.class))).thenReturn(company);
//
//        try {
//            companyService.save(companyForm);
//        } catch (CompanyAlreadyExistsException e) {
//            assertThat(e.getMessage()).isEqualTo("Uma empresa com o nome Company já existe.");
//        }
//
//        verify(companyFormMapper, times(1)).map(companyForm);
//        verify(companyRepository, times(1)).findByName("COMPANY");
//        verify(companyRepository, never()).save(any(Company.class));
//        verify(companyViewMapper, never()).map(any(Company.class));
//    }
//}
