package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StickerTypeTest {

    private final Faker faker = new Faker();

    @Test
    void testGettersAndSetters() {
        // Dados fictícios
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();

        // Criação da entidade com dados fictícios
        StickerType type = new StickerType();
        type.setId(id);
        type.setDescription(description);

        // Validação dos métodos getters
        assertNotNull(type.getId());
        assertEquals(id, type.getId());
        assertEquals(description, type.getDescription());
    }

    @Test
    void testEqualsAndHashCode() {
        StickerType type1 = new StickerType();
        type1.setId(1L);
        type1.setDescription("Type A");

        StickerType type2 = new StickerType();
        type2.setId(1L);
        type2.setDescription("Type A");

        StickerType type3 = new StickerType();
        type3.setId(2L);
        type3.setDescription("Type B");

        // Verifica se dois objetos com os mesmos valores são considerados iguais
        assertEquals(type1, type2);
        assertEquals(type1.hashCode(), type2.hashCode());

        // Verifica se objetos com valores diferentes não são iguais
        assertNotEquals(type1, type3);
        assertNotEquals(type1.hashCode(), type3.hashCode());
    }

    @Test
    void testToString() {
        StickerType type = new StickerType();
        type.setId(1L);

        // Verifica a saída do método toString
        assertEquals("StickerType(id=1)", type.toString());
    }
}