package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.printed.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.PrintingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;

public class PrintingTypeViewMapperTest {

    private final PrintingTypeViewMapper mapper = new PrintingTypeViewMapper();

    @Test
    public void testMap() {
        // Arrange
        PrintingType printingType = new PrintingType();
        printingType.setId(1L);
        printingType.setDescription("Tipo de Impressão A");

        // Act
        PrintingTypeView result = mapper.map(printingType);

        // Assert
        assertEquals(1L, result.id());
        assertEquals("Tipo de Impressão A", result.description());
    }

    @Test
    public void testMapWithNull() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            mapper.map(null); 
        });
    }
}
