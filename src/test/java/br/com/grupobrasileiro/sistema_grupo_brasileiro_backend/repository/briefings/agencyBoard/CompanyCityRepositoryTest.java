package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.City;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.CompanyCity;

@DataJpaTest
public class CompanyCityRepositoryTest {

    @Autowired
    private CompanyCityRepository companyCityRepository;

    private CompanyCity companyCity;

    @BeforeEach
    public void setUp() {
        // Configuração do objeto CompanyCity antes de cada teste
        companyCity = new CompanyCity();
        companyCity.setCity(new City(1L, "São Paulo"));
        companyCity.setCompany(new Company(1L, "Empresa A"));
    }

    /**
     * Testa a persistência e recuperação de um CompanyCity.
     */
    @Test
    @Rollback(false) // Ajuste para true se não quiser persistir no banco de dados
    @DisplayName("Should save and find a CompanyCity")
    public void testSaveAndFindCompanyCity() {
        // Salva o objeto no repositório
        CompanyCity savedCompanyCity = companyCityRepository.save(companyCity);

        // Recupera o objeto pelo ID
        Optional<CompanyCity> foundCompanyCity = companyCityRepository.findById(savedCompanyCity.getId());

        // Verifica se o objeto encontrado é o mesmo que o salvo
        assertThat(foundCompanyCity).isPresent();
        assertThat(foundCompanyCity.get()).isEqualTo(savedCompanyCity);
    }

    /**
     * Testa a busca de um CompanyCity inexistente pelo ID.
     */
    @Test
    @DisplayName("Should return empty for a non-existing CompanyCity")
    public void testFindByIdNotFound() {
        Optional<CompanyCity> foundCompanyCity = companyCityRepository.findById(999L); // ID que não existe
        assertThat(foundCompanyCity).isNotPresent(); // Deve ser vazio
    }

    /**
     * Testa a exclusão de um CompanyCity.
     */
    @Test
    @Rollback(false) // Ajuste para true se não quiser persistir no banco de dados
    @DisplayName("Should delete a CompanyCity")
    public void testDeleteCompanyCity() {
        // Salva o objeto no repositório
        CompanyCity savedCompanyCity = companyCityRepository.save(companyCity);

        // Exclui o objeto
        companyCityRepository.delete(savedCompanyCity);

        // Verifica se o objeto foi excluído
        Optional<CompanyCity> foundCompanyCity = companyCityRepository.findById(savedCompanyCity.getId());
        assertThat(foundCompanyCity).isNotPresent();
    }

    /**
     * Testa a atualização de um CompanyCity.
     */
    @Test
    @Rollback(false) // Ajuste para true se não quiser persistir no banco de dados
    @DisplayName("Should update a CompanyCity")
    public void testUpdateCompanyCity() {
        // Salva o objeto no repositório
        CompanyCity savedCompanyCity = companyCityRepository.save(companyCity);

        // Atualiza o objeto
        City newCity = new City();
        newCity.setId(2L);
        newCity.setName("Rio de Janeiro");
        Company newCompany = new Company();
        newCompany.setId(2L);
        newCompany.setName("Empresa B");
        
        savedCompanyCity.setCity(newCity);
        savedCompanyCity.setCompany(newCompany);

        // Salva a atualização
        CompanyCity updatedCompanyCity = companyCityRepository.save(savedCompanyCity);

        // Verifica se a atualização foi aplicada
        assertThat(updatedCompanyCity.getCity().getName()).isEqualTo("Rio de Janeiro");
        assertThat(updatedCompanyCity.getCompany().getName()).isEqualTo("Empresa B");
    }

    /**
     * Testa a busca de todas as instâncias de CompanyCity.
     */
    @Test
    @Rollback(false) // Ajuste para true se não quiser persistir no banco de dados
    @DisplayName("Should find all CompanyCities")
    public void testFindAllCompanyCities() {
        // Salva múltiplos CompanyCity
        companyCityRepository.save(companyCity);
        CompanyCity anotherCompanyCity = new CompanyCity();
        anotherCompanyCity.setCity(new City(2L, "Rio de Janeiro"));
        anotherCompanyCity.setCompany(new Company(2L, "Empresa B"));
        companyCityRepository.save(anotherCompanyCity);

        // Recupera todos os CompanyCities
        List<CompanyCity> companyCities = companyCityRepository.findAll();

        // Verifica se a lista não está vazia
        assertThat(companyCities).isNotEmpty(); // Deve conter elementos
        assertThat(companyCities).hasSize(2); // Deve ter dois elementos
    }
}
