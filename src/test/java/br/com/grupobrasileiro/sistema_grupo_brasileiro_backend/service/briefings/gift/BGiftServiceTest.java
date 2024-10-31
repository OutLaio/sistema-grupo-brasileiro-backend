package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.gift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.form.GiftForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.BGiftDetailedView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.BGiftView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.CalendarTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.GiftTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.PrintingShirtTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.StampView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.printeds.view.PrintingTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.BriefingView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.ProjectView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.form.GiftFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view.BGiftDetailedViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.BGift;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.CalendarType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.GiftType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.PrintingShirtType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.Stamp;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.BGiftRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.CalendarTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.GiftTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.PrintingShirtTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.PrintingTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.StampRepository;

public class BGiftServiceTest {

    @InjectMocks
    private BGiftService bGiftService;

    @Mock
    private BGiftRepository giftRepository;

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

    @Mock
    private GiftFormMapper giftFormMapper;

    @Mock
    private BGiftDetailedViewMapper bGiftDetailedViewMapper;

    @Mock
    private Briefing briefing;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        // Criando um objeto GiftForm para testar
        GiftForm giftForm = new GiftForm(1L, null, null, null, null, "Modelo A", "http://link.com");
        
        // Criando objetos simulados
        GiftType giftType = new GiftType(1L, "Tipo de Presente");
        PrintingType printingType = new PrintingType(1L, "Tipo de Impressão");
        PrintingShirtType printingShirtType = new PrintingShirtType(1L, "Tipo de Camiseta");
        Stamp stamp = new Stamp(1L, "Selo");
        CalendarType calendarType = new CalendarType(1L, "Calendário");

        // Criando um objeto BGift simulado
        BGift bGift = new BGift();

        // Criando um objeto BGiftView com os dados necessários
        BGiftView bGiftView = new BGiftView(
            1L, 
            new GiftTypeView(1L, "Tipo de Presente"), 
            new PrintingTypeView(1L, "Tipo de Impressão"), 
            new PrintingShirtTypeView(1L, "Tipo de Camiseta"), 
            new StampView(1L, "Selo"), 
            new CalendarTypeView(1L, "Calendário"), 
            "Modelo A", 
            "http://link.com"
        );

        // Criando um objeto BGiftDetailedView com os objetos simulados
        ProjectView projectView = new ProjectView(1L, "Projeto A", "Em andamento", null, null);
        BriefingView briefingView = new BriefingView(1L, null, null, null, null, null, null, null, null);
        BGiftDetailedView bGiftDetailedView = new BGiftDetailedView(bGiftView, projectView, briefingView);

        // Configurando o comportamento dos mocks
        when(giftTypeRepository.getReferenceById(1L)).thenReturn(giftType);
        when(printingTypeRepository.getReferenceById(null)).thenReturn(printingType);
        when(printingShirtTypeRepository.getReferenceById(null)).thenReturn(printingShirtType);
        when(stampRepository.getReferenceById(null)).thenReturn(stamp);
        when(calendarTypeRepository.getReferenceById(null)).thenReturn(calendarType);
        when(giftFormMapper.map(giftForm)).thenReturn(bGift);
        when(giftRepository.save(bGift)).thenReturn(bGift);
        when(bGiftDetailedViewMapper.map(bGift)).thenReturn(bGiftDetailedView);

        // Chamando o método a ser testado
        BGiftDetailedView result = bGiftService.register(giftForm, briefing);

        // Verificando se o resultado não é nulo
        assertNotNull(result, "O resultado não deve ser nulo");
        
        // Verificando se o método save foi chamado
        verify(giftRepository).save(bGift);
        
        // Verificando se os campos foram configurados corretamente
        assertEquals(giftType, bGift.getGiftType(), "O tipo de presente deve ser o esperado");
        assertEquals(printingType, bGift.getPrintingType(), "O tipo de impressão deve ser o esperado");
        assertEquals(printingShirtType, bGift.getPrintingShirtType(), "O tipo de camiseta deve ser o esperado");
        assertEquals(stamp, bGift.getStamp(), "O selo deve ser o esperado");
        assertEquals(calendarType, bGift.getCalendarType(), "O tipo de calendário deve ser o esperado");
        assertEquals(briefing, bGift.getBriefing(), "O briefing deve ser o esperado");
    }
}