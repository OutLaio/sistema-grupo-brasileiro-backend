package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintedType;

@DataJpaTest
public class PrintedTypeRepositoryTest {

    @Autowired
    private PrintedTypeRepository printedTypeRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa a persistência e recuperação de um PrintedType.
     * Verifica se o objeto é salvo e pode ser recuperado corretamente.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should save and find PrintedType correctly")
    void testSaveAndFindPrintedType() {
        // Arrange
        PrintedType printedType = new PrintedType();
        printedType.setDescription(faker.lorem().sentence());

        // Act
        PrintedType savedType = printedTypeRepository.save(printedType);

        // Assert
        Optional<PrintedType> foundType = printedTypeRepository.findById(savedType.getId());
        assertThat(foundType).isPresent();
        assertThat(foundType.get().getDescription()).isEqualTo(printedType.getDescription());
    }

    /**
     * Testa a atualização de um PrintedType.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should update a PrintedType")
    void testUpdatePrintedType() {
        // Arrange
        PrintedType printedType = new PrintedType();
        printedType.setDescription(faker.lorem().sentence());
        PrintedType savedType = printedTypeRepository.save(printedType);

        // Act - Atualiza a descrição
        savedType.setDescription("Nova descrição do tipo impresso");
        PrintedType updatedType = printedTypeRepository.save(savedType);

        // Assert
        assertThat(updatedType.getDescription()).isEqualTo("Nova descrição do tipo impresso");
    }

    /**
     * Testa a exclusão de um PrintedType.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should delete a PrintedType")
    void testDeletePrintedType() {
        // Arrange
        PrintedType printedType = new PrintedType();
        printedType.setDescription(faker.lorem().sentence());
        PrintedType savedType = printedTypeRepository.save(printedType);

        // Act
        printedTypeRepository.delete(savedType);
        Optional<PrintedType> foundType = printedTypeRepository.findById(savedType.getId());

        // Assert
        assertThat(foundType).isNotPresent();
    }

    /**
     * Testa a recuperação de todos os PrintedType.
     */
    @Test
    @DisplayName("Should retrieve all PrintedTypes")
    void testFindAllPrintedTypes() {
        // Arrange
        PrintedType type1 = new PrintedType();
        type1.setDescription(faker.lorem().sentence());
        PrintedType type2 = new PrintedType();
        type2.setDescription(faker.lorem().sentence());
        printedTypeRepository.save(type1);
        printedTypeRepository.save(type2);

        // Act
        Iterable<PrintedType> allTypes = printedTypeRepository.findAll();

        // Assert
        assertThat(allTypes).hasSize(2);
    }
}
