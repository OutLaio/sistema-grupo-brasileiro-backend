package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class PrintingTypeRepositoryTest {

    @Mock
    private PrintingTypeRepository printingTypeRepository;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    @DisplayName("Should save and find PrintingType correctly")
    void testSaveAndFindPrintingType() {
        // Arrange
        PrintingType printingType = new PrintingType();
        printingType.setDescription(faker.lorem().word());
        when(printingTypeRepository.save(any(PrintingType.class))).thenReturn(printingType);
        when(printingTypeRepository.findById(anyLong())).thenReturn(Optional.of(printingType));

        // Act
        PrintingType savedType = printingTypeRepository.save(printingType);
        Optional<PrintingType> foundType = printingTypeRepository.findById(savedType.getId());

        // Assert
        assertThat(foundType).isPresent();
        assertThat(foundType.get().getDescription()).isEqualTo(printingType.getDescription());
    }

    @Test
    @DisplayName("Should return empty when finding non-existing PrintingType")
    void testFindNonExistingPrintingType() {
        // Act
        Optional<PrintingType> foundType = printingTypeRepository.findById(999L); // ID que não existe

        // Assert
        assertThat(foundType).isNotPresent();
    }

    @Test
    @DisplayName("Should update an existing PrintingType correctly")
    void testUpdatePrintingType() {
        // Arrange
        PrintingType printingType = new PrintingType();
        printingType.setDescription(faker.lorem().word());

        when(printingTypeRepository.save(any(PrintingType.class))).thenReturn(printingType);
        when(printingTypeRepository.findById(anyLong())).thenReturn(Optional.of(printingType));

        PrintingType savedType = printingTypeRepository.save(printingType);
        savedType.setDescription("Updated Description");

        // Act
        PrintingType updatedType = printingTypeRepository.save(savedType);
        Optional<PrintingType> foundType = printingTypeRepository.findById(updatedType.getId());

        // Assert
        assertThat(foundType).isPresent();
        assertThat(foundType.get().getDescription()).isEqualTo("Updated Description");
    }

    @Test
    @DisplayName("Should delete an existing PrintingType")
    void testDeletePrintingType() {
        // Arrange
        PrintingType printingType = new PrintingType();
        printingType.setDescription(faker.lorem().word());

        when(printingTypeRepository.save(any(PrintingType.class))).thenReturn(printingType);
        when(printingTypeRepository.findById(anyLong())).thenReturn(Optional.of(printingType));

        PrintingType savedType = printingTypeRepository.save(printingType);
        Long id = savedType.getId();

        // Act
        printingTypeRepository.delete(savedType);
        when(printingTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<PrintingType> foundType = printingTypeRepository.findById(id);

        // Assert
        assertThat(foundType).isNotPresent();
    }

    @Test
    @DisplayName("Should not save a PrintingType with null description")
    void testSavePrintingTypeWithNullDescription() {
        // Arrange
        PrintingType printingType = new PrintingType();
        printingType.setDescription(null);

        // Act & Assert
        assertThatThrownBy(() -> printingTypeRepository.save(printingType))
            .isInstanceOf(Exception.class); // Ajuste a exceção conforme necessário
    }
}
