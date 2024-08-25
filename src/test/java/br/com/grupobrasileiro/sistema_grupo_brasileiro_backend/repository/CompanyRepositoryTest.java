package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.CompanyRepository;

@DataJpaTest
@ActiveProfiles("test") // Use a profile for testing if needed
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void testFindByName() {
        // Create a new Company entity
        Company company = new Company();
        company.setName("Test Company");

        // Save the entity
        companyRepository.save(company);

        // Fetch the company by name
        Company foundCompany = companyRepository.findByName("Test Company");

        // Assertions
        assertThat(foundCompany).isNotNull();
        assertThat(foundCompany.getName()).isEqualTo("Test Company");
    }

    @Test
    public void testFindByNameNotFound() {
        // Fetch the company by a name that does not exist
        Company foundCompany = companyRepository.findByName("Nonexistent Company");

        // Assertions
        assertThat(foundCompany).isNull();
    }
}
