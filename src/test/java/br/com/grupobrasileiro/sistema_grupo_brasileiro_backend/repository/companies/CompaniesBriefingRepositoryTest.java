package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.companies;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.Company;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.companies.CompaniesBriefing;

@DataJpaTest
public class CompaniesBriefingRepositoryTest {

    @Autowired
    private CompaniesBriefingRepository companiesBriefingRepository;

    private Faker faker;
    private Company testCompany;
    private Briefing testBriefing;
    private CompaniesBriefing companiesBriefing;

    @BeforeEach
    void setUp() {
        faker = new Faker();

        // Criando um objeto Company fictício
        testCompany = new Company();
        testCompany.setName(faker.company().name()); // Ajuste conforme os métodos disponíveis na classe Company

        // Criando um objeto Briefing fictício
        testBriefing = new Briefing();
        testBriefing.setDetailedDescription(faker.lorem().paragraph()); // Ajuste conforme os métodos disponíveis na classe Briefing
        testBriefing.setStartTime(LocalDateTime.now());
        testBriefing.setExpectedTime(LocalDateTime.now().plusDays(7));

        // Criando o objeto CompaniesBriefing
        companiesBriefing = new CompaniesBriefing();
        companiesBriefing.setCompany(testCompany);
        companiesBriefing.setBriefing(testBriefing);
    }

    /**
     * Testa a persistência e recuperação de um CompaniesBriefing.
     * Verifica se o objeto é salvo e pode ser recuperado corretamente.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should save and find CompaniesBriefing correctly")
    void testSaveAndFindCompaniesBriefing() {
        // Act
        CompaniesBriefing savedBriefing = companiesBriefingRepository.save(companiesBriefing);
        
        // Assert
        Optional<CompaniesBriefing> foundBriefing = companiesBriefingRepository.findById(savedBriefing.getId());
        assertThat(foundBriefing).isPresent();
        assertThat(foundBriefing.get().getCompany()).isEqualTo(companiesBriefing.getCompany());
        assertThat(foundBriefing.get().getBriefing()).isEqualTo(companiesBriefing.getBriefing());
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
