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
 * Testa a classe CompaniesBriefingFormMapper.
 * Verifica o mapeamento de CompaniesBriefingsForm para CompaniesBriefing.
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

    /**
     * Testa o mapeamento bem-sucedido de CompaniesBriefingsForm para CompaniesBriefing.
     * Verifica se a empresa correta é recuperada e mapeada.
     */
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

    /**
     * Testa o cenário em que uma empresa não é encontrada no repositório.
     * Espera-se que uma RuntimeException seja lançada.
     */
    @Test
    @DisplayName("Should throw exception when company is not found")
    void throwExceptionWhenNotFound() {
        when(companyRepository.findById(company.getId())).thenReturn(Optional.empty());

        // Esperando uma exceção ao tentar mapear um CompaniesBriefingsForm com uma empresa inexistente
        assertThrows(RuntimeException.class, () -> companiesBriefingFormMapper.map(companiesBriefingsForm));
    }

    /**
     * Testa o manuseio de CompaniesBriefingsForm quando o companyId é nulo.
     * Espera-se que o CompaniesBriefing mapeado contenha uma empresa nula.
     */
    @Test
    @DisplayName("Should throw exception when handling CompaniesBriefingsForm with null companyId")
    void handleNullCompanyId() {
        companiesBriefingsForm = new CompaniesBriefingsForm(null); // Definindo companyId como nulo

        // Esperando que uma exceção seja lançada ao tentar mapear um form com companyId nulo
        assertThrows(IllegalArgumentException.class, () -> {
            companiesBriefingFormMapper.map(companiesBriefingsForm);
        });
    }


    /**
     * Testa o mapeamento quando um companyId diferente é fornecido no CompaniesBriefingsForm.
     * Verifica se a empresa correta é recuperada e mapeada.
     */
    @Test
    @DisplayName("Should map CompaniesBriefingsForm with different companyId")
    void mapWithDifferentId() {
        // Cria uma empresa falsa diferente
        Company anotherCompany = new Company(faker.number().randomNumber(), faker.company().name());
        when(companyRepository.findById(anotherCompany.getId())).thenReturn(Optional.of(anotherCompany));

        CompaniesBriefingsForm differentForm = new CompaniesBriefingsForm(anotherCompany.getId());

        CompaniesBriefing result = companiesBriefingFormMapper.map(differentForm);

        assertNotNull(result);
        assertEquals(anotherCompany.getId(), result.getCompany().getId());
        assertEquals(anotherCompany.getName(), result.getCompany().getName());

        verify(companyRepository).findById(anotherCompany.getId());
    }

    /**
     * Testa o mapeamento com um companyId inválido ou inexistente.
     * Espera-se que uma RuntimeException seja lançada quando a empresa não é encontrada.
     */
    @Test
    @DisplayName("Should throw exception for invalid companyId")
    void throwExceptionForInvalidId() {
        Long invalidCompanyId = 999L; // Supõe-se que este ID não exista
        companiesBriefingsForm = new CompaniesBriefingsForm(invalidCompanyId);
        
        when(companyRepository.findById(invalidCompanyId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> companiesBriefingFormMapper.map(companiesBriefingsForm));
    }

    
}
