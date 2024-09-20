package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.companiesBrienfing.form;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
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
        // Initializes Mockito mocks
        MockitoAnnotations.openMocks(this);

        // Initializes Java Faker for generating fake data
        faker = new Faker();

        // Creates a fake Company object with only id and name
        company = new Company(faker.number().randomNumber(), faker.company().name());

        // Creates a fake CompaniesBriefingsForm object
        companiesBriefingsForm = new CompaniesBriefingsForm(company.getId());
    }

    @Test
    @DisplayName("Should map CompaniesBriefingsForm to CompaniesBriefing successfully")
    void shouldMapCompaniesBriefingsFormToCompaniesBriefing() {
        // Mocks the repository response to return the fake Company
        when(companyRepository.findById(company.getId())).thenReturn(Optional.of(company));

        // Calls the method to be tested
        CompaniesBriefing result = companiesBriefingFormMapper.map(companiesBriefingsForm);

        // Asserts the result
        assertNotNull(result);
        assertEquals(company.getId(), result.getCompany().getId());
        assertEquals(company.getName(), result.getCompany().getName());

        // Verifies that the repository's findById method was called correctly
        verify(companyRepository).findById(company.getId());
    }
}
