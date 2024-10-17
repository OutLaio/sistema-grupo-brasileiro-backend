package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.form.GiftForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.BGift;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GiftFormMapperTest {

    private final GiftFormMapper giftFormMapper = new GiftFormMapper();

    @Test
    public void testMap() {
        GiftForm giftForm = new GiftForm(1L, 2L, 3L, 4L, 5L, "Modelo A", "http://link.com");
        
        BGift bGift = giftFormMapper.map(giftForm);

        assertEquals("Modelo A", bGift.getGiftModel(), "O modelo do presente deve ser 'Modelo A'");
        assertEquals("http://link.com", bGift.getLinkModel(), "O link do modelo deve ser 'http://link.com'");
        
    }
}