package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.companies;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.companies.CompaniesBriefing;

@DataJpaTest
public class CompaniesBriefingRepositoryTest {

    @Autowired
    private CompaniesBriefingRepository companiesBriefingRepository;

    private CompaniesBriefing companiesBriefing;
    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
        // Criando um objeto CompaniesBriefing fictício
        companiesBriefing = new CompaniesBriefing();
        companiesBriefing.setCompanyName(faker.company().name());
        companiesBriefing.setBriefingDetails(faker.lorem().paragraph());
    }

    /**
     * Testa a persistência e recuperação de um CompaniesBriefing.
     * Verifica se o objeto é salvo e pode ser recuperado corretamente.
     */
    @Test
    @Rollback(false) // Coloque false se você não quer que o banco de dados reverta após o teste
    @DisplayName("Should save and find CompaniesBriefing correctly")
    void testSaveAndFindCompaniesBriefing() {
        // Act
        CompaniesBriefing savedBriefing = companiesBriefingRepository.save(companiesBriefing);
        
        // Assert
        Optional<CompaniesBriefing> foundBriefing = companiesBriefingRepository.findById(savedBriefing.getId());
        assertThat(foundBriefing).isPresent();
        assertThat(foundBriefing.get().getCompanyName()).isEqualTo(companiesBriefing.getCompanyName());
    }

    /**
     * Testa a exclusão de um CompaniesBriefing.
     * Verifica se o objeto é excluído corretamente do repositório.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should delete CompaniesBriefing correctly")
    void testDeleteCompaniesBriefing() {
        // Act
        CompaniesBriefing savedBriefing = companiesBriefingRepository.save(companiesBriefing);
        companiesBriefingRepository.delete(savedBriefing);

        // Assert
        Optional<CompaniesBriefing> foundBriefing = companiesBriefingRepository.findById(savedBriefing.getId());
        assertThat(foundBriefing).isNotPresent();
    }
}
