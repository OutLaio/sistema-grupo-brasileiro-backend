package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.City;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.CompanyCity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CompanyCityRepositoryTest {

    @Autowired
    private CompanyCityRepository companyCityRepository;

    /**
     * Testa a persistência e recuperação de um CompanyCity.
     */
    @Test
    public void testSaveAndFindCompanyCity() {
        // Criação de um novo objeto CompanyCity
        CompanyCity companyCity = new CompanyCity();
        companyCity.setCity(new City(1L, "São Paulo")); // Adicione um mock ou um objeto real de City
        companyCity.setCompany(new Company(1L, "Empresa A")); // Adicione um mock ou um objeto real de Company

        // Salva o objeto no repositório
        CompanyCity savedCompanyCity = companyCityRepository.save(companyCity);

        // Recupera o objeto pelo ID
        Optional<CompanyCity> foundCompanyCity = companyCityRepository.findById(savedCompanyCity.getId());

        // Verifica se o objeto encontrado é o mesmo que o salvo
        assertThat(foundCompanyCity).isPresent();
        assertThat(foundCompanyCity.get()).isEqualTo(savedCompanyCity);
    }

    /**
     * Testa a exclusão de um CompanyCity.
     */
    @Test
    public void testDeleteCompanyCity() {
        // Criação de um novo objeto CompanyCity
        CompanyCity companyCity = new CompanyCity();
        companyCity.setCity(new City(1L, "São Paulo")); 
        companyCity.setCompany(new Company(1L, "Empresa A"));

        // Salva o objeto no repositório
        CompanyCity savedCompanyCity = companyCityRepository.save(companyCity);

        // Exclui o objeto
        companyCityRepository.delete(savedCompanyCity);

        // Verifica se o objeto foi excluído
        Optional<CompanyCity> foundCompanyCity = companyCityRepository.findById(savedCompanyCity.getId());
        assertThat(foundCompanyCity).isNotPresent();
    }
}
