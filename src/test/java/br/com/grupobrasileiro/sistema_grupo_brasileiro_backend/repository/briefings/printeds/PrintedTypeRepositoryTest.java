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
}
