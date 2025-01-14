package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.form;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

public class GiftFormMapperTest {

    @InjectMocks
    private GiftFormMapper giftFormMapper;

    @Mock
    private GiftTypeRepository giftTypeRepository;

    @Mock
    private PrintingTypeRepository printingTypeRepository;

    @Mock
    private PrintingShirtTypeRepository printingShirtTypeRepository;

    @Mock
    private StampRepository stampRepository;

    @Mock
    private CalendarTypeRepository calendarTypeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMap() {
        GiftForm giftForm = new GiftForm(1L, 2L, 3L, 4L, 5L, "Modelo A", "http://link.com");

        GiftType mockGiftType = new GiftType(1L, "Gift Type A");
        when(giftTypeRepository.findById(1L)).thenReturn(Optional.of(mockGiftType));

        PrintingType mockPrintingType = new PrintingType(2L, "Printing Type A");
        when(printingTypeRepository.findById(2L)).thenReturn(Optional.of(mockPrintingType));

        PrintingShirtType mockPrintingShirtType = new PrintingShirtType(3L, "Printing Shirt Type A");
        when(printingShirtTypeRepository.findById(3L)).thenReturn(Optional.of(mockPrintingShirtType));

        Stamp mockStamp = new Stamp(4L, "Stamp A");
        when(stampRepository.findById(4L)).thenReturn(Optional.of(mockStamp));

        CalendarType mockCalendarType = new CalendarType(5L, "Calendar Type A");
        when(calendarTypeRepository.findById(5L)).thenReturn(Optional.of(mockCalendarType));

        BGift bGift = giftFormMapper.map(giftForm);

        assertEquals("Modelo A", bGift.getGiftModel());
        assertEquals("http://link.com", bGift.getLinkModel());
        assertEquals(mockGiftType, bGift.getGiftType());
        assertEquals(mockPrintingType, bGift.getPrintingType());
        assertEquals(mockPrintingShirtType, bGift.getPrintingShirtType());
        assertEquals(mockStamp, bGift.getStamp());
        assertEquals(mockCalendarType, bGift.getCalendarType());
    }

    @Test
    public void testMap_GiftTypeNotFound() {
        GiftForm giftForm = new GiftForm(1L, 2L, 3L, 4L, 5L, "Modelo A", "http://link.com");

        when(giftTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> giftFormMapper.map(giftForm));
    }

    @Test
    public void testMap_PrintingTypeNotFound() {
        GiftForm giftForm = new GiftForm(1L, 2L, 3L, 4L, 5L, "Modelo A", "http://link.com");

        when(giftTypeRepository.findById(1L)).thenReturn(Optional.of(new GiftType(1L, "Gift Type A")));
        when(printingTypeRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> giftFormMapper.map(giftForm));
    }

    @Test
    public void testMap_PrintingShirtTypeNotFound() {
        GiftForm giftForm = new GiftForm(1L, 2L, 3L, 4L, 5L, "Modelo A", "http://link.com");

        when(giftTypeRepository.findById(1L)).thenReturn(Optional.of(new GiftType(1L, "Gift Type A")));
        when(printingTypeRepository.findById(2L)).thenReturn(Optional.of(new PrintingType(2L, "Printing Type A")));
        when(printingShirtTypeRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> giftFormMapper.map(giftForm));
    }

    @Test
    public void testMap_NullValues() {
        GiftForm giftForm = new GiftForm(1L, null, null, null, null, "Modelo A", "http://link.com");

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
