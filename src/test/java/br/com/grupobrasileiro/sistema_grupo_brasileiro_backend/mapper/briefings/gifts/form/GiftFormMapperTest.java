package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.form;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.form.GiftForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.BGift;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.CalendarType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.GiftType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.PrintingShirtType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.Stamp;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.CalendarTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.GiftTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.PrintingShirtTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.StampRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds.PrintingTypeRepository;

@SpringBootTest
public class GiftFormMapperTest {

    @Autowired
    private GiftFormMapper giftFormMapper;

    @MockBean
    private GiftTypeRepository giftTypeRepository;

    @MockBean
    private PrintingTypeRepository printingTypeRepository;

    @MockBean
    private PrintingShirtTypeRepository printingShirtTypeRepository;

    @MockBean
    private StampRepository stampRepository;

    @MockBean
    private CalendarTypeRepository calendarTypeRepository;

    @Test
    public void testMap() {
        // Configuração do GiftForm de entrada
        GiftForm giftForm = new GiftForm(1L, 2L, 3L, 4L, 5L, "Modelo A", "http://link.com");

        // Mock para GiftTypeRepository
        GiftType mockGiftType = new GiftType(1L, "Gift Type A");
        when(giftTypeRepository.findById(1L)).thenReturn(Optional.of(mockGiftType));

        // Mock para PrintingTypeRepository
        PrintingType mockPrintingType = new PrintingType(2L, "Printing Type A");
        when(printingTypeRepository.findById(2L)).thenReturn(Optional.of(mockPrintingType));

        // Mock para PrintingShirtTypeRepository
        PrintingShirtType mockPrintingShirtType = new PrintingShirtType(3L, "Printing Shirt Type A");
        when(printingShirtTypeRepository.findById(3L)).thenReturn(Optional.of(mockPrintingShirtType));

        // Mock para StampRepository
        Stamp mockStamp = new Stamp(4L, "Stamp A");
        when(stampRepository.findById(4L)).thenReturn(Optional.of(mockStamp));

        // Mock para CalendarTypeRepository
        CalendarType mockCalendarType = new CalendarType(5L, "Calendar Type A");
        when(calendarTypeRepository.findById(5L)).thenReturn(Optional.of(mockCalendarType));

        // Chamada do método de mapeamento
        BGift bGift = giftFormMapper.map(giftForm);

        // Verificações
        assertEquals("Modelo A", bGift.getGiftModel(), "O modelo do presente deve ser 'Modelo A'");
        assertEquals("http://link.com", bGift.getLinkModel(), "O link do modelo deve ser 'http://link.com'");
        assertEquals(mockGiftType, bGift.getGiftType(), "O tipo de presente deve ser 'Gift Type A'");
        assertEquals(mockPrintingType, bGift.getPrintingType(), "O tipo de impressão deve ser 'Printing Type A'");
        assertEquals(mockPrintingShirtType, bGift.getPrintingShirtType(), "O tipo de camisa de impressão deve ser 'Printing Shirt Type A'");
        assertEquals(mockStamp, bGift.getStamp(), "O carimbo deve ser 'Stamp A'");
        assertEquals(mockCalendarType, bGift.getCalendarType(), "O tipo de calendário deve ser 'Calendar Type A'");
    }

    @Test
    public void testMap_GiftTypeNotFound() {
        GiftForm giftForm = new GiftForm(1L, 2L, 3L, 4L, 5L, "Modelo A", "http://link.com");

        // Mock para GiftTypeRepository retornar vazio, simulando "Gift Type not found"
        when(giftTypeRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificação da exceção
        assertThrows(EntityNotFoundException.class, () -> giftFormMapper.map(giftForm));
    }

    @Test
    public void testMap_PrintingTypeNotFound() {
        GiftForm giftForm = new GiftForm(1L, 2L, 3L, 4L, 5L, "Modelo A", "http://link.com");

        // Mocks para GiftTypeRepository e outros repositórios
        when(giftTypeRepository.findById(1L)).thenReturn(Optional.of(new GiftType(1L, "Gift Type A")));
        when(printingTypeRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> giftFormMapper.map(giftForm));
    }

    @Test
    public void testMap_PrintingShirtTypeNotFound() {
        GiftForm giftForm = new GiftForm(1L, 2L, 3L, 4L, 5L, "Modelo A", "http://link.com");

        // Mocks para outros repositórios e para PrintingShirtTypeRepository retornar vazio
        when(giftTypeRepository.findById(1L)).thenReturn(Optional.of(new GiftType(1L, "Gift Type A")));
        when(printingTypeRepository.findById(2L)).thenReturn(Optional.of(new PrintingType(2L, "Printing Type A")));
        when(printingShirtTypeRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> giftFormMapper.map(giftForm));
    }

    @Test
    public void testMap_NullValues() {
        // Testa com alguns IDs nulos
        GiftForm giftForm = new GiftForm(1L, null, null, null, null, "Modelo A", "http://link.com");

        // Mock para GiftTypeRepository
        GiftType mockGiftType = new GiftType(1L, "Gift Type A");
        when(giftTypeRepository.findById(1L)).thenReturn(Optional.of(mockGiftType));

        BGift bGift = giftFormMapper.map(giftForm);

        assertEquals(mockGiftType, bGift.getGiftType());
        assertEquals("Modelo A", bGift.getGiftModel());
        assertEquals("http://link.com", bGift.getLinkModel());
        assertEquals(null, bGift.getPrintingType());
        assertEquals(null, bGift.getPrintingShirtType());
        assertEquals(null, bGift.getStamp());
        assertEquals(null, bGift.getCalendarType());
    }
}
