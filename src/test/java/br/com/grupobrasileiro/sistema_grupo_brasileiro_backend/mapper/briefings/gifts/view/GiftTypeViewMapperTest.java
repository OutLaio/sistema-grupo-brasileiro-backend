package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.GiftTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.GiftType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GiftTypeViewMapperTest {

    private final GiftTypeViewMapper giftTypeViewMapper = new GiftTypeViewMapper();

    @Test
    public void testMap() {
        GiftType giftType = new GiftType();
        giftType.setId(1L);
        giftType.setDescription("Descrição do Tipo de Presente");

        GiftTypeView result = giftTypeViewMapper.map(giftType);

        assertEquals(1L, result.id(), "O ID deve ser 1");
        assertEquals("Descrição do Tipo de Presente", result.description(), "A descrição deve ser 'Descrição do Tipo de Presente'");
    }
}