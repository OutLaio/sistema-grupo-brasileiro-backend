package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private PrintingType createPrintingType(String description) {
        PrintingType printingType = new PrintingType();
        printingType.setDescription(description);
        return printingType;
    }

    @Test
    @DisplayName("Should save and find PrintingType correctly")
    void testSaveAndFindPrintingType() {
        // Arrange
        PrintingType printingType = createPrintingType("Test Description");

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
    @DisplayName("Should return empty when PrintingType not found")
    void testFindByIdNotFound() {
        // Arrange
        when(printingTypeRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<PrintingType> foundType = printingTypeRepository.findById(999L); // ID que não existe

        // Assert
        assertThat(foundType).isNotPresent();
    }
}
