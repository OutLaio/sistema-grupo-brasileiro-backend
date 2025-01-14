package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.printed.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.PrintedTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintedType;

public class PrintedTypeViewMapperTest {

    private final PrintedTypeViewMapper mapper = new PrintedTypeViewMapper();

    @Test
    public void testMap() {
        // Arrange
        PrintedType printedType = new PrintedType();
        printedType.setId(1L);
        printedType.setDescription("Tipo de Impressão A");

        // Act
        PrintedTypeView result = mapper.map(printedType);

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
