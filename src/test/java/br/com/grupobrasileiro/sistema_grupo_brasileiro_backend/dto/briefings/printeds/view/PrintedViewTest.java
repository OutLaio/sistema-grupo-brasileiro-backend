package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class PrintedViewTest {

    @Test
    public void testCreatePrintedView() {
        // Arrange
        PrintedTypeView printedType = new PrintedTypeView(1L, "Tipo de Impressão A");
        PrintingTypeView printingType = new PrintingTypeView(1L, "Tipo de Impressão B");
        String paperType = "Papel A";
        Integer folds = 2;
        Integer pages = 10;

        // Act
        PrintedView printedView = new PrintedView(1L, printedType, printingType, paperType, folds, pages);

        // Assert
        assertEquals(1L, printedView.id());
        assertEquals(printedType, printedView.printedType());
        assertEquals(printingType, printedView.printingType());
        assertEquals(paperType, printedView.paperType());
        assertEquals(folds, printedView.folds());
        assertEquals(pages, printedView.pages());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        PrintedTypeView printedType1 = new PrintedTypeView(1L, "Tipo de Impressão A");
        PrintingTypeView printingType1 = new PrintingTypeView(1L, "Tipo de Impressão B");
        PrintedView printedView1 = new PrintedView(1L, printedType1, printingType1, "Papel A", 2, 10);
        
        PrintedTypeView printedType2 = new PrintedTypeView(2L, "Tipo de Impressão C");
        PrintingTypeView printingType2 = new PrintingTypeView(2L, "Tipo de Impressão D");
        PrintedView printedView2 = new PrintedView(1L, printedType1, printingType1, "Papel A", 2, 10); // mesmo ID
        PrintedView printedView3 = new PrintedView(2L, printedType2, printingType2, "Papel B", 3, 20); // diferentes

        // Act & Assert
        assertEquals(printedView1, printedView2); // devem ser iguais
        assertNotEquals(printedView1, printedView3); // não devem ser iguais
        assertEquals(printedView1.hashCode(), printedView2.hashCode()); // mesmos hash codes para objetos iguais
        assertNotEquals(printedView1.hashCode(), printedView3.hashCode()); // hash codes diferentes para objetos diferentes
    }
}
