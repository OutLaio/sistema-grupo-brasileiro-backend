package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.PrintingShirtTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.PrintingShirtType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrintingShirtTypeViewMapperTest {

    private final PrintingShirtTypeViewMapper printingShirtTypeViewMapper = new PrintingShirtTypeViewMapper();

    @Test
    public void testMap() {
        PrintingShirtType printingShirtType = new PrintingShirtType();
        printingShirtType.setId(1L);
        printingShirtType.setDescription("Descrição do Tipo de Camiseta");

        PrintingShirtTypeView result = printingShirtTypeViewMapper.map(printingShirtType);

       
        assertEquals(1L, result.id(), "O ID deve ser 1");
        assertEquals("Descrição do Tipo de Camiseta", result.description(), "A descrição deve ser 'Descrição do Tipo de Camiseta'");
    }
}