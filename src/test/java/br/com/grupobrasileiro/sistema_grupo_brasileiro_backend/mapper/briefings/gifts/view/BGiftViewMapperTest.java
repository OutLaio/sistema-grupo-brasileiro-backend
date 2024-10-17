package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.BGiftView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.BGift;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BGiftViewMapperTest {

    @Mock
    private GiftTypeViewMapper giftTypeViewMapper;

    @Mock
    private PrintingTypeViewMapper printingTypeViewMapper;

    @Mock
    private PrintingShirtTypeViewMapper printingShirtTypeViewMapper;

    @Mock
    private StampViewMapper stampViewMapper;

    @Mock
    private CalendarTypeViewMapper calendarTypeViewMapper;

    @InjectMocks
    private BGiftViewMapper bGiftViewMapper;

    public BGiftViewMapperTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMap() {
        BGift bGift = new BGift();
        bGift.setId(1L);
        bGift.setGiftModel("Modelo A");
        bGift.setLinkModel("http://link.com");

        // Simulando os mapeamentos
        when(giftTypeViewMapper.map(bGift.getGiftType())).thenReturn(null); 
        when(printingTypeViewMapper.map(bGift.getPrintingType())).thenReturn(null); 
        when(printingShirtTypeViewMapper.map(bGift.getPrintingShirtType())).thenReturn(null); 
        when(stampViewMapper.map(bGift.getStamp())).thenReturn(null); 
        when(calendarTypeViewMapper.map(bGift.getCalendarType())).thenReturn(null); 

        BGiftView result = bGiftViewMapper.map(bGift);

       
        assertEquals(1L, result.id(), "O ID deve ser 1");
        assertEquals("Modelo A", result.giftModel(), "O modelo do presente deve ser 'Modelo A'");
        assertEquals("http://link.com", result.linkModel(), "O link do modelo deve ser 'http://link.com'");
    }
}