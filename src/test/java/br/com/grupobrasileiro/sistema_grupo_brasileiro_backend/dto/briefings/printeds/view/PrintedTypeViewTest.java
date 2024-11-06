package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.PrintedTypeView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PrintedTypeViewTest {

    @Test
    public void testCreatePrintedTypeView() {
        // Arrange
        Long id = 1L;
        String description = "Descrição do tipo impresso";

        // Act
        PrintedTypeView printedTypeView = new PrintedTypeView(id, description);

        // Assert
        assertEquals(id, printedTypeView.id());
        assertEquals(description, printedTypeView.description());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        PrintedTypeView printedTypeView1 = new PrintedTypeView(1L, "Descrição do tipo impresso");
        PrintedTypeView printedTypeView2 = new PrintedTypeView(1L, "Descrição do tipo impresso");
        PrintedTypeView printedTypeView3 = new PrintedTypeView(2L, "Outra descrição");

        // Act & Assert
        assertEquals(printedTypeView1, printedTypeView2); // devem ser iguais
        assertNotEquals(printedTypeView1, printedTypeView3); // não devem ser iguais
        assertEquals(printedTypeView1.hashCode(), printedTypeView2.hashCode()); // mesmos hash codes para objetos iguais
        assertNotEquals(printedTypeView1.hashCode(), printedTypeView3.hashCode()); // hash codes diferentes para objetos diferentes
    }
}
