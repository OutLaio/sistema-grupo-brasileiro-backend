package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.gifts.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.gifts.view.StampView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.gifts.Stamp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StampViewMapperTest {

    private final StampViewMapper stampViewMapper = new StampViewMapper();

    @Test
    public void testMap() {
        Stamp stamp = new Stamp();
        stamp.setId(1L);
        stamp.setDescription("Descrição do Selo");

        StampView result = stampViewMapper.map(stamp);

       
        assertEquals(1L, result.id(), "O ID deve ser 1");
        assertEquals("Descrição do Selo", result.description(), "A descrição deve ser 'Descrição do Selo'");
    }
}