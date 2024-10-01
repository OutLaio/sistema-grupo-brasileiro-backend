package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.City;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.CompanyCity;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CompanyCityRepositoryTest {

    @Autowired
    private CompanyCityRepository companyCityRepository; 

    @Autowired
    private CityRepository cityRepository; 

    @Autowired
    private CompanyRepository companyRepository; 
    
    private CompanyCity companyCity; 

    @BeforeEach
    void setUp() {
        // Limpar dados antes do teste
        companyCityRepository.deleteAll(); 
        cityRepository.deleteAll(); 
        companyRepository.deleteAll(); 

        // Criar e salvar uma cidade de teste
        City city = new City();
        city.setName("Cidade Exemplo"); 
        city = cityRepository.save(city); 

        // Criar e salvar uma empresa de teste
        Company company = new Company();
        company.setName("Empresa Exemplo"); 
        company = companyRepository.save(company); 

        // Criar e salvar a associação entre empresa e cidade
        companyCity = new CompanyCity(); 
        companyCity.setCompany(company); 
        companyCity.setCity(city);
        companyCityRepository.save(companyCity); 
    }

    @Test
    void testCompanyCityAssociation() {
        // Verificar se a associação foi criada corretamente
        assertNotNull(companyCity); // A associação não deve ser nula

    }


    /**
     * Testa a persistência e recuperação de um CompanyCity.
     */
    @Test
    @Rollback(false) 
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
    @Rollback(false) 
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
    @Rollback(false) 
    @DisplayName("Should update a CompanyCity")
    public void testUpdateCompanyCity() {
        // Salva o objeto no repositório
        CompanyCity savedCompanyCity = companyCityRepository.save(companyCity);

        // Criar e salvar uma nova cidade e empresa para a atualização
        City newCity = new City();
        newCity.setName("Ilhéus");
        newCity = cityRepository.save(newCity);

        Company newCompany = new Company();
        newCompany.setName("Empresa B");
        newCompany = companyRepository.save(newCompany); 

        // Atualiza o objeto
        savedCompanyCity.setCity(newCity);
        savedCompanyCity.setCompany(newCompany);

        // Salva a atualização
        CompanyCity updatedCompanyCity = companyCityRepository.save(savedCompanyCity);

        // Verifica se a atualização foi aplicada
        assertThat(updatedCompanyCity.getCity().getName()).isEqualTo("Itabuna");
        assertThat(updatedCompanyCity.getCompany().getName()).isEqualTo("Empresa B");
    }

    /**
     * Testa a busca de todas as instâncias de CompanyCity.
     */
    @Test
    @Rollback(false) 
    @DisplayName("Should find all CompanyCities")
    public void testFindAllCompanyCities() {
        // Salva a primeira CompanyCity
        City city1 = new City();
        city1.setName("Ilhéus");
        city1 = cityRepository.save(city1); 

        Company company1 = new Company();
        company1.setName("Empresa A");
        company1 = companyRepository.save(company1); 

        CompanyCity companyCity1 = new CompanyCity();
        companyCity1.setCity(city1);
        companyCity1.setCompany(company1);
        companyCityRepository.save(companyCity1); 

        // Salva a segunda CompanyCity
        City city2 = new City();
        city2.setName("Itabuna");
        city2 = cityRepository.save(city2); 

        Company company2 = new Company();
        company2.setName("Empresa B");
        company2 = companyRepository.save(company2); 

        CompanyCity companyCity2 = new CompanyCity();
        companyCity2.setCity(city2);
        companyCity2.setCompany(company2);
        companyCityRepository.save(companyCity2); 

        // Recupera todos os CompanyCities
        List<CompanyCity> companyCities = companyCityRepository.findAll();

        // Adiciona log para verificar quantos registros foram salvos
        System.out.println("Número de CompanyCities salvos: " + companyCities.size());

        // Verifica se a lista não está vazia
        assertThat(companyCities).isNotEmpty();
        assertThat(companyCities).hasSize(2); // Verifica se o total é 2
    }


}
