package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefins.handout.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.handout.view.HandoutTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.handout.view.HandoutTypeViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.HandoutType;

public class HandoutTypeViewMapperTest {

    @InjectMocks
    private HandoutTypeViewMapper mapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMap() {
        // Criando um mock do HandoutType
        HandoutType handoutType = mock(HandoutType.class);
        
        // Configurando os mocks
        when(handoutType.getId()).thenReturn(1L);
        when(handoutType.getDescription()).thenReturn("Descrição do Tipo de Comunicado");

        // Executando o método a ser testado
        HandoutTypeView result = mapper.map(handoutType);

        // Verificando os resultados
        assertNotNull(result);
        assertEquals(Long.valueOf(1), result.id());
        assertEquals("Descrição do Tipo de Comunicado", result.description());
    }

    @Test
    public void testMap_NullHandoutType() {

        assertThrows(NullPointerException.class, () -> {
            mapper.map(null);
        });
    }
}