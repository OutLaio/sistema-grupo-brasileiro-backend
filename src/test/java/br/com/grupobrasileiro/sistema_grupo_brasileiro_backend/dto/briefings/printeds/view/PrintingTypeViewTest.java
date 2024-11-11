package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class PrintingTypeViewTest {

    @Test
    public void testCreatePrintingTypeView() {
        // Arrange
        Long id = 1L;
        String description = "Descrição do tipo de impressão";

        // Act
        PrintingTypeView printingTypeView = new PrintingTypeView(id, description);

        // Assert
        assertEquals(id, printingTypeView.id());
        assertEquals(description, printingTypeView.description());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        PrintingTypeView printingTypeView1 = new PrintingTypeView(1L, "Descrição do tipo de impressão");
        PrintingTypeView printingTypeView2 = new PrintingTypeView(1L, "Descrição do tipo de impressão");
        PrintingTypeView printingTypeView3 = new PrintingTypeView(2L, "Outra descrição");

        // Act & Assert
        assertEquals(printingTypeView1, printingTypeView2); // devem ser iguais
        assertNotEquals(printingTypeView1, printingTypeView3); // não devem ser iguais
        assertEquals(printingTypeView1.hashCode(), printingTypeView2.hashCode()); // mesmos hash codes para objetos iguais
        assertNotEquals(printingTypeView1.hashCode(), printingTypeView3.hashCode()); // hash codes diferentes para objetos diferentes
    }
}
