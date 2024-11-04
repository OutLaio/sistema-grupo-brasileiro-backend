package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service.briefings.gift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.form.GiftForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.form.GiftFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.BGift;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift.BGiftRepository;

public class BGiftServiceTest {

    @InjectMocks
    private BGiftService bGiftService;

    @Mock
    private BGiftRepository giftRepository;

    @Mock
    private GiftFormMapper giftFormMapper;

    @Mock
    private Briefing briefing;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        // Criando um objeto GiftForm para testar
        GiftForm giftForm = new GiftForm(1L, null, null, null, null, "Modelo A", "link.com"); // Ajuste conforme necessário
        
        // Criando um objeto BGift simulado
        BGift bGift = new BGift(); // Ajuste conforme a estrutura da classe BGift
        
        // Configurando o comportamento dos mocks
        when(giftFormMapper.map(giftForm)).thenReturn(bGift);
        
        // Chamando o método a ser testado
        bGiftService.register(giftForm, briefing);

        // Verificando se o método save foi chamado
        verify(giftRepository).save(bGift);
        
        // Verificando se o briefing foi configurado corretamente
        assertEquals(briefing, bGift.getBriefing(), "O briefing deve ser o esperado");
    }
}