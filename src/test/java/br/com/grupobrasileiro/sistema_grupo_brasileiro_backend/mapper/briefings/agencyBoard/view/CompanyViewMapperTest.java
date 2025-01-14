package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.CompanyView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view.CompanyViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;

/**
 * Testa a classe CompanyViewMapper.
 * Verifica se o mapeamento lida com Company nulo e campos nulos corretamente.
 */
public class CompanyViewMapperTest {

    @InjectMocks
    private CompanyViewMapper companyViewMapper;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    /**
     * Testa o mapeamento de Company para CompanyView.
     * Verifica se um Company é corretamente mapeado para um CompanyView.
     */
    @Test
    @DisplayName("Should map Company to CompanyView correctly")
    void mapCompany() {
        // Dados de teste usando Faker
        Company company = new Company();
        company.setId(faker.number().randomNumber());
        company.setName(faker.company().name());

        // Mapeamento
        CompanyView result = companyViewMapper.map(company);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(company.getId());
        assertThat(result.name()).isEqualTo(company.getName());
    }

    /**
     * Testa que o método map lança uma exceção ao receber Company nulo.
     * Verifica se o método lida corretamente com entradas nulas.
     */
    @Test
    @DisplayName("Should throw exception when mapping null Company")
    void throwExceptionForNullCompany() {
        // Verifica se o método lança uma exceção quando o Company é nulo
        assertThrows(NullPointerException.class, () -> {
            companyViewMapper.map(null);
        });
    }

    /**
     * Testa o mapeamento de Company com campos nulos para CompanyView.
     * Verifica se o método lida corretamente com campos nulos no Company.
     */
    @Test
    @DisplayName("Should map Company with null fields to CompanyView with null fields")
    void mapCompanyWithNullFields() {
        Company company = new Company();
        company.setId(null);
        company.setName(null);

        // Mapeamento
        CompanyView result = companyViewMapper.map(company);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isNull();
        assertThat(result.name()).isNull();
    }

    /**
     * Testa o mapeamento de Company com ID nulo e nome não nulo.
     * Verifica se o método lida corretamente com ID nulo.
     */
    @Test
    @DisplayName("Should map Company with null ID and non-null name")
    void mapCompanyWithNullId() {
        Company company = new Company();
        company.setId(null);
        company.setName(faker.company().name());

        // Mapeamento
        CompanyView result = companyViewMapper.map(company);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isNull();
        assertThat(result.name()).isEqualTo(company.getName());
    }

    /**
     * Testa o mapeamento de Company com ID não nulo e nome nulo.
     * Verifica se o método lida corretamente com nome nulo.
     */
    @Test
    @DisplayName("Should map Company with non-null ID and null name")
    void mapCompanyWithNullName() {
        Company company = new Company();
        company.setId(faker.number().randomNumber());
        company.setName(null);

        // Mapeamento
        CompanyView result = companyViewMapper.map(company);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(company.getId());
        assertThat(result.name()).isNull();
    }

    /**
     * Testa o mapeamento de Company com ID válido e nome vazio.
     * Verifica se o método lida corretamente com nome vazio.
     */
    @Test
    @DisplayName("Should map Company with valid ID and empty name")
    void mapCompanyWithEmptyName() {
        Company company = new Company();
        company.setId(faker.number().randomNumber());
        company.setName("");

        // Mapeamento
        CompanyView result = companyViewMapper.map(company);

        // Verificação dos resultados
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(company.getId());
        assertThat(result.name()).isEqualTo(""); // Verifica se o nome vazio é mapeado corretamente
    }
}
