package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.companiesBrienfing.form;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.form.CompaniesBriefingsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.companiesBriefing.form.CompaniesBriefingFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.companies.CompaniesBriefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.CompanyRepository;

@ExtendWith(MockitoExtension.class)
class CompaniesBriefingFormMapperTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompaniesBriefingFormMapper companiesBriefingFormMapper;

    private Faker faker;
    private Company company;
    private CompaniesBriefingsForm companiesBriefingsForm;

    @BeforeEach
    void setUp() {
        faker = new Faker();
        company = new Company(faker.number().randomNumber(), faker.company().name());
        companiesBriefingsForm = new CompaniesBriefingsForm(company.getId());
    }

    @Test
    @DisplayName("Should map CompaniesBriefingsForm to CompaniesBriefing successfully")
    void mapToBriefing() {
        when(companyRepository.findById(company.getId())).thenReturn(Optional.of(company));

        CompaniesBriefing result = companiesBriefingFormMapper.map(companiesBriefingsForm);

        assertNotNull(result);
        assertEquals(company.getId(), result.getCompany().getId());
        assertEquals(company.getName(), result.getCompany().getName());

        verify(companyRepository).findById(company.getId());
    }

    @Test
    @DisplayName("Should throw exception when company not found")
    void throwExceptionWhenNotFound() {
        when(companyRepository.findById(company.getId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> companiesBriefingFormMapper.map(companiesBriefingsForm));
    }

    @Test
    @DisplayName("Should throw exception when handling CompaniesBriefingsForm with null companyId")
    void handleNullCompanyId() {
        companiesBriefingsForm = new CompaniesBriefingsForm(null);

        assertThrows(IllegalArgumentException.class, () -> {
            companiesBriefingFormMapper.map(companiesBriefingsForm);
        });
    }

    @Test
    @DisplayName("Must map CompaniesBriefingsForm with different companyId")
    void mapWithDifferentId() {
        Company anotherCompany = new Company(faker.number().randomNumber(), faker.company().name());
        when(companyRepository.findById(anotherCompany.getId())).thenReturn(Optional.of(anotherCompany));

        CompaniesBriefingsForm differentForm = new CompaniesBriefingsForm(anotherCompany.getId());

        CompaniesBriefing result = companiesBriefingFormMapper.map(differentForm);

        assertNotNull(result);
        assertEquals(anotherCompany.getId(), result.getCompany().getId());
        assertEquals(anotherCompany.getName(), result.getCompany().getName());

        verify(companyRepository).findById(anotherCompany.getId());
    }

    @Test
    @DisplayName("Should throw exception for invalid companyId")
    void throwExceptionForInvalidId() {
        Long invalidCompanyId = 999L;
        companiesBriefingsForm = new CompaniesBriefingsForm(invalidCompanyId);
        
        when(companyRepository.findById(invalidCompanyId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> companiesBriefingFormMapper.map(companiesBriefingsForm));
    }
}