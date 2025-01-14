package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.printed.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.PrintedTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.PrintedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.PrintingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.BPrinted;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintedType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;

public class BPrintedsViewMapperTest {

    @InjectMocks
    private BPrintedsViewMapper mapper; 

    @Mock
    private PrintedTypeViewMapper printedTypeViewMapper; 

    @Mock
    private PrintingTypeViewMapper printingTypeViewMapper; 

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); 
    }

    @Test
    public void testMap() {
        // Arrange
        BPrinted bPrinted = mock(BPrinted.class);
        PrintedType printedType = mock(PrintedType.class);
        PrintingType printingType = mock(PrintingType.class);
        
        when(bPrinted.getId()).thenReturn(1L);
        when(bPrinted.getPrintedType()).thenReturn(printedType);
        when(bPrinted.getPrintingType()).thenReturn(printingType);
        when(bPrinted.getPaperType()).thenReturn("A4");
        when(bPrinted.getFolds()).thenReturn(2);
        when(bPrinted.getPages()).thenReturn(10);

        PrintedTypeView printedTypeView = new PrintedTypeView(1L, "Tipo de Impressão");
        PrintingTypeView printingTypeView = new PrintingTypeView(1L, "Tipo de Papel");

        when(printedTypeViewMapper.map(printedType)).thenReturn(printedTypeView);
        when(printingTypeViewMapper.map(printingType)).thenReturn(printingTypeView);

        // Act
        PrintedView result = mapper.map(bPrinted);

        // Assert
        assertEquals(1L, result.id());
        assertEquals(printedTypeView, result.printedType());
        assertEquals(printingTypeView, result.printingType());
        assertEquals("A4", result.paperType());
        assertEquals(2, result.folds());
        assertEquals(10, result.pages());
    }

    @Test
    public void testMapWithNullTypes() {
        // Arrange
        BPrinted bPrinted = mock(BPrinted.class);
        when(bPrinted.getId()).thenReturn(1L);
        when(bPrinted.getPrintedType()).thenReturn(null);
        when(bPrinted.getPrintingType()).thenReturn(null);
        when(bPrinted.getPaperType()).thenReturn("A4");
        when(bPrinted.getFolds()).thenReturn(2);
        when(bPrinted.getPages()).thenReturn(10);

        // Act
        PrintedView result = mapper.map(bPrinted);

        // Assert
        assertEquals(1L, result.id());
        assertNull(result.printedType()); // Esperamos null para printedType
        assertNull(result.printingType()); // Esperamos null para printingType
        assertEquals("A4", result.paperType());
        assertEquals(2, result.folds());
        assertEquals(10, result.pages());
    }
}
