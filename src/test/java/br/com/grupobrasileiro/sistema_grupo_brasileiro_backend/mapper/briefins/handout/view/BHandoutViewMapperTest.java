package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefins.handout.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.BHandoutView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.HandoutTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.view.BHandoutViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.view.HandoutTypeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.HandoutType;

public class BHandoutViewMapperTest {

    @InjectMocks
    private BHandoutViewMapper mapper;

    @Mock
    private HandoutTypeViewMapper handoutTypeViewMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMap() {
        // Configurando os mocks
        BHandout bHandout = mock(BHandout.class);
        HandoutType handoutType = mock(HandoutType.class);
        HandoutTypeView handoutTypeView = mock(HandoutTypeView.class);

        when(bHandout.getId()).thenReturn(1L);
        when(bHandout.getHandoutType()).thenReturn(handoutType);
        when(handoutTypeViewMapper.map(handoutType)).thenReturn(handoutTypeView);

        // Executando o método a ser testado
        BHandoutView result = mapper.map(bHandout);

        // Verificando os resultados
        assertNotNull(result);
        assertEquals(Long.valueOf(1), result.id());
        assertEquals(handoutTypeView, result.handoutType());
    }

    @Test
    public void testMap_NullBHandout() {
        // Executando o método a ser testado com bHandout nulo
        assertThrows(NullPointerException.class, () -> {
            mapper.map(null);
        });
    }
}