package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.PrintingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintingTypeViewMapperTest {

    private final PrintingTypeViewMapper printingTypeViewMapper = new PrintingTypeViewMapper();

    @Test
    public void testMap() {
        PrintingType printingType = new PrintingType();
        printingType.setId(1L);
        printingType.setDescription("Descrição do Tipo de Impressão");

        PrintingTypeView result = printingTypeViewMapper.map(printingType);

    
        assertEquals(1L, result.id(), "O ID deve ser 1");
        assertEquals("Descrição do Tipo de Impressão", result.description(), "A descrição deve ser 'Descrição do Tipo de Impressão'");
    }
}