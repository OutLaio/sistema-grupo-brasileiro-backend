package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift;

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

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.PrintingShirtType;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional  
public class PrintingShirtTypeRepositoryTest {

    @Autowired
    private PrintingShirtTypeRepository printingShirtTypeRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa a persistência e recuperação do PrintingShirtType no repositório.
     * Verifica se o objeto é salvo e pode ser recuperado corretamente.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should save and find PrintingShirtType correctly")
    void testSaveAndFindPrintingShirtType() {
        // Arrange
        PrintingShirtType shirtType = new PrintingShirtType();
        shirtType.setDescription(faker.lorem().word()); 

        // Act
        PrintingShirtType savedType = printingShirtTypeRepository.save(shirtType);

        // Assert
        Optional<PrintingShirtType> foundType = printingShirtTypeRepository.findById(savedType.getId());
        assertThat(foundType).isPresent();
        assertThat(foundType.get().getDescription()).isEqualTo(shirtType.getDescription());
    }

    /**
     * Testa a atualização de um PrintingShirtType.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should update a PrintingShirtType")
    void testUpdatePrintingShirtType() {
        // Arrange
        PrintingShirtType shirtType = new PrintingShirtType();
        shirtType.setDescription(faker.lorem().word());
        PrintingShirtType savedType = printingShirtTypeRepository.save(shirtType);

        // Act - Atualiza a descrição do tipo de impressão da camiseta
        savedType.setDescription("Descrição Atualizada");
        PrintingShirtType updatedType = printingShirtTypeRepository.save(savedType);

        // Assert
        assertThat(updatedType.getDescription()).isEqualTo("Descrição Atualizada");
    }

    /**
     * Testa a exclusão de um PrintingShirtType.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should delete a PrintingShirtType")
    void testDeletePrintingShirtType() {
        // Arrange
        PrintingShirtType shirtType = new PrintingShirtType();
        shirtType.setDescription(faker.lorem().word());
        PrintingShirtType savedType = printingShirtTypeRepository.save(shirtType);

        // Act
        printingShirtTypeRepository.delete(savedType);
        Optional<PrintingShirtType> foundType = printingShirtTypeRepository.findById(savedType.getId());

        // Assert
        assertThat(foundType).isNotPresent();
    }

    /**
     * Testa a recuperação de todos os PrintingShirtTypes.
     */
    @Test
    @DisplayName("Should retrieve all PrintingShirtTypes")
    void testFindAllPrintingShirtTypes() {
        // Arrange
        PrintingShirtType shirtType1 = new PrintingShirtType();
        shirtType1.setDescription(faker.lorem().word());
        PrintingShirtType shirtType2 = new PrintingShirtType();
        shirtType2.setDescription(faker.lorem().word());
        printingShirtTypeRepository.save(shirtType1);
        printingShirtTypeRepository.save(shirtType2);

        // Act
        Iterable<PrintingShirtType> allShirtTypes = printingShirtTypeRepository.findAll();

        // Assert
        assertThat(allShirtTypes).hasSize(2);
    }
}
