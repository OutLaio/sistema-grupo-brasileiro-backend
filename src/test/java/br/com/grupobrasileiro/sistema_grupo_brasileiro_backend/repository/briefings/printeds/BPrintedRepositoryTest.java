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

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.BPrinted;


@DataJpaTest
public class BPrintedRepositoryTest {

    @Autowired
    private BPrintedRepository bPrintedRepository; 
    
    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    /**
     * Testa a persistência e recuperação de um BPrinted.
     * Verifica se o objeto é salvo e pode ser recuperado corretamente.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should save and find BPrinted correctly")
    void testSaveAndFindBPrinted() {
        // Arrange
        BPrinted bPrinted = new BPrinted();
        bPrinted.setPaperType(faker.lorem().word()); 

        // Act
        BPrinted savedPrinted = bPrintedRepository.save(bPrinted);

        // Assert
        Optional<BPrinted> foundPrinted = bPrintedRepository.findById(savedPrinted.getId());
        assertThat(foundPrinted).isPresent();
        assertThat(foundPrinted.get().getPaperType()).isEqualTo(bPrinted.getPaperType()); // Verificando paperType
    }
}
