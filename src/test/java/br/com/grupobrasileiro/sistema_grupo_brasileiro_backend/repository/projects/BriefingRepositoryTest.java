package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project; // Certifique-se de que esta classe está presente
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class BriefingRepositoryTest {

    @Autowired
    private BriefingRepository briefingRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa a criação e recuperação de um briefing.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should create and retrieve a briefing")
    void testCreateAndRetrieveBriefing() {
        // Arrange
        Briefing briefing = createTestBriefing();
        
        // Act
        briefingRepository.save(briefing); // Salva o briefing no banco de dados
        
        // Recupera o briefing recém-criado
        Optional<Briefing> retrievedBriefing = briefingRepository.findById(briefing.getId());

        // Assert
        assertThat(retrievedBriefing).isPresent();
        assertThat(retrievedBriefing.get().getDetailedDescription()).isEqualTo(briefing.getDetailedDescription());
    }

    /**
     * Testa a recuperação de um briefing que não existe.
     */
    @Test
    @DisplayName("Should return empty when retrieving non-existing briefing")
    void testRetrieveNonExistingBriefing() {
        // Act
        Optional<Briefing> retrievedBriefing = briefingRepository.findById(999L); // ID que não existe

        // Assert
        assertThat(retrievedBriefing).isNotPresent();
    }

    /**
     * Testa a exclusão de um briefing.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should delete a briefing")
    void testDeleteBriefing() {
        // Arrange
        Briefing briefing = createTestBriefing();
        briefing = briefingRepository.save(briefing); // Salva o briefing no banco de dados

        // Act
        briefingRepository.delete(briefing); // Exclui o briefing
        Optional<Briefing> deletedBriefing = briefingRepository.findById(briefing.getId()); // Tenta recuperar o briefing excluído

        // Assert
        assertThat(deletedBriefing).isNotPresent();
    }

    /**
     * Testa a atualização de um briefing.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should update a briefing")
    void testUpdateBriefing() {
        // Arrange
        Briefing briefing = createTestBriefing();
        briefing = briefingRepository.save(briefing); // Salva o briefing no banco de dados

        // Act
        String newDescription = faker.lorem().paragraph();
        briefing.setDetailedDescription(newDescription);
        briefingRepository.save(briefing); // Atualiza o briefing

        // Assert
        Optional<Briefing> updatedBriefing = briefingRepository.findById(briefing.getId());
        assertThat(updatedBriefing).isPresent();
        assertThat(updatedBriefing.get().getDetailedDescription()).isEqualTo(newDescription);
    }

    /**
     * Testa a criação de um briefing com dados inválidos.
     */
    @Test
    @DisplayName("Should throw exception when creating briefing with null fields")
    void testCreateBriefingWithNullFields() {
        // Arrange
        Briefing briefing = new Briefing();
        briefing.setProject(null); // Define projeto como null
        briefing.setBriefingType(null); // Define tipo de briefing como null
        briefing.setStartTime(null); // Define hora de início como null
        briefing.setExpectedTime(null); // Define hora esperada como null
        briefing.setDetailedDescription(null); // Define descrição como null

        // Act & Assert
        assertThrows(Exception.class, () -> {
            briefingRepository.save(briefing); // Tenta salvar o briefing
        });
    }

    private Briefing createTestBriefing() {
        Project project = new Project(); // Crie e configure um projeto adequado
        project.setId(1L); // Defina o ID conforme necessário ou salve o projeto antes

        BriefingType briefingType = new BriefingType(); // Crie e configure um tipo de briefing adequado
        briefingType.setId(1L); // Defina o ID conforme necessário ou salve o tipo de briefing antes

        Briefing briefing = new Briefing();
        briefing.setProject(project);
        briefing.setBriefingType(briefingType);
        briefing.setStartTime(LocalDateTime.now());
        briefing.setExpectedTime(LocalDateTime.now().plusDays(5));
        briefing.setDetailedDescription(faker.lorem().paragraph());
        briefing.setOtherCompany(faker.company().name());

        return briefing;
    }
}
