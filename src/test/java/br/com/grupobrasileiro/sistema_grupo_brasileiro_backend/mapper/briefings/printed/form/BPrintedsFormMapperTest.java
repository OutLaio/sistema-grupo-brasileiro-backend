package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.printed.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.form.PrintedForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.printed.form.BPrintedsFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.BPrinted;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintedType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds.PrintedTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds.PrintingTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class BPrintedsFormMapperTest {

    @Mock
    private PrintedTypeRepository printedTypeRepository;

    @Mock
    private PrintingTypeRepository printingTypeRepository;

    @InjectMocks
    private BPrintedsFormMapper bPrintedsFormMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMapSuccessfully() {
        // Arrange
        PrintedForm form = new PrintedForm(1L, 2L, "A4", 2, 20);
        // Simular o comportamento dos repositórios
        when(printedTypeRepository.findById(1L)).thenReturn(Optional.of(new PrintedType(1L, null)));
        when(printingTypeRepository.findById(2L)).thenReturn(Optional.of(new PrintingType(2L, null)));

        // Act
        BPrinted result = bPrintedsFormMapper.map(form);

        // Assert
        assertEquals(form.idPrintedType(), result.getPrintedType().getId());
        assertEquals(form.idPrintingType(), result.getPrintingType().getId());
        assertEquals(form.paperType(), result.getPaperType());
        assertEquals(form.folds(), result.getFolds());
        assertEquals(form.pages(), result.getPages());
    }

    @Test
    public void testMapWithNullInput() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            bPrintedsFormMapper.map(null);
        });
    }

    @Test
    public void testMapWhenPrintedTypeNotFound() {
        // Arrange
        PrintedForm form = new PrintedForm(1L, null, "A4", 2, 20);
        // Simular o comportamento do repositório
        when(printedTypeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            bPrintedsFormMapper.map(form);
        });
        assertEquals("Printed Type not found with id: 1", thrown.getMessage());
    }

    @Test
    public void testMapWhenPrintingTypeNotFound() {
        // Arrange
        PrintedForm form = new PrintedForm(1L, 2L, "A4", 2, 20);
        when(printedTypeRepository.findById(1L)).thenReturn(Optional.of(new PrintedType()));
        when(printingTypeRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            bPrintedsFormMapper.map(form);
        });
        assertEquals("Printing Type not found with id: 2", thrown.getMessage());
    }
}
