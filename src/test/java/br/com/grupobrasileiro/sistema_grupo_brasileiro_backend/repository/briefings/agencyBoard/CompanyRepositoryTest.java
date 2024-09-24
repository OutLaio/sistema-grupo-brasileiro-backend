package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    /**
     * Testa a persistência e recuperação de uma Company.
     */
    @Test
    public void testSaveAndFindCompany() {
        // Criação de um novo objeto Company
        Company company = new Company();
        company.setName("Empresa A");

        // Salva o objeto no repositório
        Company savedCompany = companyRepository.save(company);

        // Recupera o objeto pelo ID
        Optional<Company> foundCompany = companyRepository.findById(savedCompany.getId());

        // Verifica se o objeto encontrado é o mesmo que o salvo
        assertThat(foundCompany).isPresent();
        assertThat(foundCompany.get()).isEqualTo(savedCompany);
    }

    /**
     * Testa a exclusão de uma Company.
     */
    @Test
    public void testDeleteCompany() {
        // Criação de um novo objeto Company
        Company company = new Company();
        company.setName("Empresa A");

        // Salva o objeto no repositório
        Company savedCompany = companyRepository.save(company);

        // Exclui o objeto
        companyRepository.delete(savedCompany);

        // Verifica se o objeto foi excluído
        Optional<Company> foundCompany = companyRepository.findById(savedCompany.getId());
        assertThat(foundCompany).isNotPresent();
    }
}
