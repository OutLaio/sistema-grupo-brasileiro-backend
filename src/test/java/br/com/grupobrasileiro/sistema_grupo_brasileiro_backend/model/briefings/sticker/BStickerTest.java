package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BStickerTest {

    private final Faker faker = new Faker();

    @Test
    void testGettersAndSetters() {
        // Dados fictícios
        Long id = faker.number().randomNumber();
        String sector = faker.company().industry();
        String observations = faker.lorem().sentence();

        // Criação da entidade com dados fictícios
        BSticker sticker = new BSticker();
        sticker.setId(id);
        sticker.setSector(sector);
        sticker.setObservations(observations);

        // Validação dos métodos getters
        assertNotNull(sticker.getId());
        assertEquals(id, sticker.getId());
        assertEquals(sector, sticker.getSector());
        assertEquals(observations, sticker.getObservations());
    }

    @Test
    void testEqualsAndHashCode() {
        BSticker sticker1 = new BSticker();
        sticker1.setId(1L);
        sticker1.setSector("Marketing");

        BSticker sticker2 = new BSticker();
        sticker2.setId(1L);
        sticker2.setSector("Marketing");

        // Verifica se dois objetos com os mesmos valores são considerados iguais
        assertEquals(sticker1, sticker2);
        assertEquals(sticker1.hashCode(), sticker2.hashCode());
    }

    @Test
    void testToString() {
        BSticker sticker = new BSticker();
        sticker.setId(1L);

        // Verifica a saída do método toString
        assertEquals("BSticker(id=1)", sticker.toString());
    }
}
