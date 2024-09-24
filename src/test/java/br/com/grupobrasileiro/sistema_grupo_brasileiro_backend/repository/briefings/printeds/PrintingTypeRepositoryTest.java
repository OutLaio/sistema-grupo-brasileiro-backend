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

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;

@DataJpaTest
public class PrintingTypeRepositoryTest {

    @Autowired
    private PrintingTypeRepository printingTypeRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    @Test
    @Rollback(false)
    @DisplayName("Should save and find PrintingType correctly")
    void testSaveAndFindPrintingType() {
        PrintingType printingType = new PrintingType();
        printingType.setDescription(faker.lorem().word());

        PrintingType savedType = printingTypeRepository.save(printingType);
        Optional<PrintingType> foundType = printingTypeRepository.findById(savedType.getId());

        assertThat(foundType).isPresent();
        assertThat(foundType.get().getDescription()).isEqualTo(printingType.getDescription());
    }

    @Test
    @Rollback(false)
    @DisplayName("Should return empty when finding non-existing PrintingType")
    void testFindNonExistingPrintingType() {
        Optional<PrintingType> foundType = printingTypeRepository.findById(999L); // ID que não existe
        assertThat(foundType).isNotPresent();
    }

    @Test
    @Rollback(false)
    @DisplayName("Should update an existing PrintingType correctly")
    void testUpdatePrintingType() {
        PrintingType printingType = new PrintingType();
        printingType.setDescription(faker.lorem().word());

        PrintingType savedType = printingTypeRepository.save(printingType);
        savedType.setDescription("Updated Description");

        PrintingType updatedType = printingTypeRepository.save(savedType);
        Optional<PrintingType> foundType = printingTypeRepository.findById(updatedType.getId());

        assertThat(foundType).isPresent();
        assertThat(foundType.get().getDescription()).isEqualTo("Updated Description");
    }

    @Test
    @Rollback(false)
    @DisplayName("Should delete an existing PrintingType")
    void testDeletePrintingType() {
        PrintingType printingType = new PrintingType();
        printingType.setDescription(faker.lorem().word());

        PrintingType savedType = printingTypeRepository.save(printingType);
        Long id = savedType.getId();

        printingTypeRepository.delete(savedType);
        Optional<PrintingType> foundType = printingTypeRepository.findById(id);

        assertThat(foundType).isNotPresent();
    }

    @Test
    @DisplayName("Should not save a PrintingType with null description")
    void testSavePrintingTypeWithNullDescription() {
        PrintingType printingType = new PrintingType();
        printingType.setDescription(null);

        PrintingType savedType = printingTypeRepository.save(printingType);
        Optional<PrintingType> foundType = printingTypeRepository.findById(savedType.getId());

        assertThat(foundType).isPresent();
        assertThat(foundType.get().getDescription()).isNull();
    }
}
