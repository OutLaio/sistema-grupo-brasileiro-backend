package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.PrintingShirtType;

@DataJpaTest
@ActiveProfiles("test")  
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
}
