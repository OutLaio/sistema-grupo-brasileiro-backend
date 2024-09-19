package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.sticker;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StickerInformationTypeTest {

    private final Faker faker = new Faker();

    @Test
    void testGettersAndSetters() {
        // Dados fictícios
        Long id = faker.number().randomNumber();
        String description = faker.lorem().word();

        // Criação da entidade com dados fictícios
        StickerInformationType type = new StickerInformationType();
        type.setId(id);
        type.setDescription(description);

        // Validação dos métodos getters
        assertNotNull(type.getId());
        assertEquals(id, type.getId());
        assertEquals(description, type.getDescription());
    }

    @Test
    void testEqualsAndHashCode() {
        StickerInformationType type1 = new StickerInformationType();
        type1.setId(1L);
        type1.setDescription("Description A");

        StickerInformationType type2 = new StickerInformationType();
        type2.setId(1L);
        type2.setDescription("Description A");

        StickerInformationType type3 = new StickerInformationType();
        type3.setId(2L);
        type3.setDescription("Description B");

        // Verifica se dois objetos com os mesmos valores são considerados iguais
        assertEquals(type1, type2);
        assertEquals(type1.hashCode(), type2.hashCode());

        // Verifica se objetos com valores diferentes não são iguais
        assertNotEquals(type1, type3);
        assertNotEquals(type1.hashCode(), type3.hashCode());
    }

    @Test
    void testToString() {
        StickerInformationType type = new StickerInformationType();
        type.setId(1L);

        // Verifica a saída do método toString
        assertEquals("StickerInformationType(id=1)", type.toString());
    }
}
