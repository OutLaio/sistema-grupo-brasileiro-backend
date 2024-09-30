package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.companiesBriefing.form;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.companiesBriefing.form.CompaniesBriefingsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.companies.CompaniesBriefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.CompanyRepository;

/**
 * Tests the CompaniesBriefingFormMapper class.
 * Verifies the mapping from CompaniesBriefingsForm to CompaniesBriefing.
 */
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
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
        company = new Company(faker.number().randomNumber(), faker.company().name());
        companiesBriefingsForm = new CompaniesBriefingsForm(company.getId());
    }

    @Test
    @DisplayName("Should map CompaniesBriefingsForm to CompaniesBriefing successfully")
    void shouldMapCompaniesBriefingsFormToCompaniesBriefing() {
        when(companyRepository.findById(company.getId())).thenReturn(Optional.of(company));

        CompaniesBriefing result = companiesBriefingFormMapper.map(companiesBriefingsForm);

        assertNotNull(result);
        assertEquals(company.getId(), result.getCompany().getId());
        assertEquals(company.getName(), result.getCompany().getName());

        verify(companyRepository).findById(company.getId());
    }

    @Test
    @DisplayName("Should throw exception when company is not found")
    void shouldThrowExceptionWhenCompanyNotFound() {
        when(companyRepository.findById(company.getId())).thenReturn(Optional.empty());

        // Expecting an exception (you might want to define what exception your mapper throws)
        assertThrows(RuntimeException.class, () -> companiesBriefingFormMapper.map(companiesBriefingsForm));
    }

    @Test
    @DisplayName("Should handle CompaniesBriefingsForm with null companyId")
    void shouldHandleCompaniesBriefingsFormWithNullCompanyId() {
        companiesBriefingsForm = new CompaniesBriefingsForm(null); // Setting companyId to null

        // Calling the method and expecting a null result
        CompaniesBriefing result = companiesBriefingFormMapper.map(companiesBriefingsForm);

        assertNotNull(result);
        assertNull(result.getCompany()); // Assuming your mapper handles null companyId appropriately
    }

    @Test
    @DisplayName("Should map CompaniesBriefingsForm with different companyId")
    void shouldMapCompaniesBriefingsFormWithDifferentCompanyId() {
        // Create a different fake company
        Company anotherCompany = new Company(faker.number().randomNumber(), faker.company().name());
        when(companyRepository.findById(anotherCompany.getId())).thenReturn(Optional.of(anotherCompany));

        CompaniesBriefingsForm differentForm = new CompaniesBriefingsForm(anotherCompany.getId());

        CompaniesBriefing result = companiesBriefingFormMapper.map(differentForm);

        assertNotNull(result);
        assertEquals(anotherCompany.getId(), result.getCompany().getId());
        assertEquals(anotherCompany.getName(), result.getCompany().getName());

        verify(companyRepository).findById(anotherCompany.getId());
    }
}
