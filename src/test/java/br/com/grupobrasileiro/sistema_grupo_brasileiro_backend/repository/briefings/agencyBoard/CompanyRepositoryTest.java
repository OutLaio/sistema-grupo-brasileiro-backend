package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) 
public class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    private Company company;

    @BeforeEach
    public void setUp() {
        // Configuração do objeto Company antes de cada teste
        company = new Company();
        company.setName("Default Company");
    }

    /**
     * Testa a inserção e busca de uma empresa.
     */
    @Test
    @Rollback(false) // Ajuste para true se não quiser persistir no banco de dados
    @DisplayName("Should insert and find a company")
    public void testInsertAndFindCompany() {
        // Salvar a empresa no repositório
        Company savedCompany = companyRepository.save(company);

        // Recuperar a empresa pelo ID
        Optional<Company> foundCompany = companyRepository.findById(savedCompany.getId());

        // Verificar se a empresa foi encontrada
        assertThat(foundCompany).isPresent();
        assertThat(foundCompany.get().getName()).isEqualTo("Default Company");
    }

    /**
     * Testa o salvamento de uma empresa.
     */
    @Test
    @Rollback(false) // Ajuste para true se não quiser persistir no banco de dados
    @DisplayName("Should save a company successfully")
    public void testSaveCompany() {
        company.setName("Test Company");

        Company savedCompany = companyRepository.save(company);
        assertThat(savedCompany).isNotNull();
        assertThat(savedCompany.getId()).isGreaterThan(0);
        assertThat(savedCompany.getName()).isEqualTo("Test Company");
    }

    /**
     * Testa a exclusão de uma empresa.
     */
    @Test
    @Rollback(false) // Ajuste para true se não quiser persistir no banco de dados
    @DisplayName("Should delete a company")
    public void testDeleteCompany() {
        // Salvar a empresa primeiro
        Company savedCompany = companyRepository.save(company);

        // Excluir a empresa
        companyRepository.delete(savedCompany);

        // Verificar se a empresa foi excluída
        Optional<Company> foundCompany = companyRepository.findById(savedCompany.getId());
        assertThat(foundCompany).isNotPresent();
    }

    /**
     * Testa a atualização de uma empresa.
     */
    @Test
    @Rollback(false) // Ajuste para true se não quiser persistir no banco de dados
    @DisplayName("Should update a company")
    public void testUpdateCompany() {
        // Salvar a empresa primeiro
        Company savedCompany = companyRepository.save(company);

        // Atualizar o nome da empresa
        savedCompany.setName("Updated Company");
        Company updatedCompany = companyRepository.save(savedCompany);

        // Verificar se o nome foi atualizado corretamente
        assertThat(updatedCompany.getName()).isEqualTo("Updated Company");
    }

    /**
     * Testa a busca de todas as empresas.
     */
    @Test
    @Rollback(false) // Ajuste para true se não quiser persistir no banco de dados
    @DisplayName("Should find all companies")
    public void testFindAllCompanies() {
        // Salvar múltiplas empresas
        companyRepository.save(company);
        Company anotherCompany = new Company();
        anotherCompany.setName("Another Company");
        companyRepository.save(anotherCompany);

        // Recuperar todas as empresas
        Iterable<Company> companies = companyRepository.findAll();

        // Verificar se a lista não está vazia
        assertThat(companies).isNotEmpty(); // Deve conter elementos
        assertThat(companies).hasSize(2); // Deve ter dois elementos
    }
}
