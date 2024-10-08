package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PrintingTypeRepositoryTest {

    @Autowired
    private PrintingTypeRepository printingTypeRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa a persistência e recuperação de um PrintingType.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should save and find PrintingType correctly")
    void testSaveAndFindPrintingType() {
        // Arrange
        PrintingType printingType = new PrintingType();
        printingType.setDescription(faker.lorem().word());

        // Act
        PrintingType savedType = printingTypeRepository.save(printingType);
        Optional<PrintingType> foundType = printingTypeRepository.findById(savedType.getId());

        // Assert
        assertThat(foundType).isPresent();
        assertThat(foundType.get().getDescription()).isEqualTo(printingType.getDescription());
    }

    /**
     * Testa a busca de um PrintingType que não existe.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should return empty when finding non-existing PrintingType")
    void testFindNonExistingPrintingType() {
        // Act
        Optional<PrintingType> foundType = printingTypeRepository.findById(999L); // ID que não existe

        // Assert
        assertThat(foundType).isNotPresent();
    }

    /**
     * Testa a atualização de um PrintingType existente.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should update an existing PrintingType correctly")
    void testUpdatePrintingType() {
        // Arrange
        PrintingType printingType = new PrintingType();
        printingType.setDescription(faker.lorem().word());

        PrintingType savedType = printingTypeRepository.save(printingType);
        savedType.setDescription("Updated Description");

        // Act
        PrintingType updatedType = printingTypeRepository.save(savedType);
        Optional<PrintingType> foundType = printingTypeRepository.findById(updatedType.getId());

        // Assert
        assertThat(foundType).isPresent();
        assertThat(foundType.get().getDescription()).isEqualTo("Updated Description");
    }

    /**
     * Testa a exclusão de um PrintingType existente.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should delete an existing PrintingType")
    void testDeletePrintingType() {
        // Arrange
        PrintingType printingType = new PrintingType();
        printingType.setDescription(faker.lorem().word());

        PrintingType savedType = printingTypeRepository.save(printingType);
        Long id = savedType.getId();

        // Act
        printingTypeRepository.delete(savedType);
        Optional<PrintingType> foundType = printingTypeRepository.findById(id);

        // Assert
        assertThat(foundType).isNotPresent();
    }

    /**
     * Testa a tentativa de salvar um PrintingType com descrição nula.
     */
    @Test
    @DisplayName("Should not save a PrintingType with null description")
    void testSavePrintingTypeWithNullDescription() {
        // Arrange
        PrintingType printingType = new PrintingType();
        printingType.setDescription(null);

}
}