package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
class AgencyBoardTypeRepositoryTest {

    @Autowired
    private AgencyBoardTypeRepository agencyBoardTypeRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        agencyBoardTypeRepository.deleteAll();
        entityManager.flush();
    }

    @Test
    void testSaveAgencyBoardType() {
        // Arrange
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setDescription("Test Board Type");

        // Act
        AgencyBoardType savedType = agencyBoardTypeRepository.save(agencyBoardType);
        entityManager.flush();

        // Assert
        assertThat(savedType).isNotNull();
        assertThat(savedType.getId()).isNotNull();
        assertThat(savedType.getDescription()).isEqualTo("Test Board Type");
    }

    @Test
    void testFindAgencyBoardTypeById() {
        // Arrange
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setDescription("Test Board Type");
        agencyBoardType = agencyBoardTypeRepository.save(agencyBoardType);
        entityManager.flush();

        // Act
        Optional<AgencyBoardType> foundType = agencyBoardTypeRepository.findById(agencyBoardType.getId());

        // Assert
        assertThat(foundType).isPresent();
        assertThat(foundType.get().getDescription()).isEqualTo("Test Board Type");
    }

    @Test
    void testFindAllAgencyBoardTypes() {
        // Arrange
        AgencyBoardType type1 = new AgencyBoardType();
        type1.setDescription("Type 1");
        AgencyBoardType type2 = new AgencyBoardType();
        type2.setDescription("Type 2");
        agencyBoardTypeRepository.saveAll(List.of(type1, type2));
        entityManager.flush();

        // Act
        List<AgencyBoardType> allTypes = agencyBoardTypeRepository.findAll();

        // Assert
        assertThat(allTypes).hasSize(2);
        assertThat(allTypes).extracting(AgencyBoardType::getDescription).containsExactlyInAnyOrder("Type 1", "Type 2");
    }

    @Test
    void testDeleteAgencyBoardType() {
        // Arrange
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setDescription("To be deleted");
        agencyBoardType = agencyBoardTypeRepository.save(agencyBoardType);
        entityManager.flush();

        // Act
        agencyBoardTypeRepository.deleteById(agencyBoardType.getId());
        entityManager.flush();

        // Assert
        Optional<AgencyBoardType> deletedType = agencyBoardTypeRepository.findById(agencyBoardType.getId());
        assertThat(deletedType).isEmpty();
    }

    @Test
    void testUpdateAgencyBoardType() {
        // Arrange
        AgencyBoardType agencyBoardType = new AgencyBoardType();
        agencyBoardType.setDescription("Original Description");
        agencyBoardType = agencyBoardTypeRepository.save(agencyBoardType);
        entityManager.flush();

        // Act
        agencyBoardType.setDescription("Updated Description");
        AgencyBoardType updatedType = agencyBoardTypeRepository.save(agencyBoardType);
        entityManager.flush();

        // Assert
        assertThat(updatedType.getDescription()).isEqualTo("Updated Description");
        Optional<AgencyBoardType> foundType = agencyBoardTypeRepository.findById(updatedType.getId());
        assertThat(foundType).isPresent();
        assertThat(foundType.get().getDescription()).isEqualTo("Updated Description");
    }
}