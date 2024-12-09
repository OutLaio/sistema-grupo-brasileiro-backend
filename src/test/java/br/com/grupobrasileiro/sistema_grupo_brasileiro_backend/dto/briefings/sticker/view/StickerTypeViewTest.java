package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view.StickerTypeView;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StickerTypeViewTest {

    private Faker faker;

    @BeforeEach
    void setUp() {
        // Inicializa o Faker para gerar dados aleatórios
        faker = new Faker();
    }

    @Test
    void testCreateStickerTypeView() {
        // Gerando dados falsos para os campos do DTO
        Long id = faker.number().randomNumber();
        String description = faker.lorem().sentence();

        // Criando a instância do DTO StickerTypeView
        StickerTypeView stickerTypeView = new StickerTypeView(id, description);

        // Verificando se o objeto foi criado corretamente e os valores estão corretos
        assertNotNull(stickerTypeView);
        assertEquals(id, stickerTypeView.id());
        assertEquals(description, stickerTypeView.description());
    }

    @Test
    void testCreateStickerTypeViewWithNullValues() {
        // Criando a instância do DTO com valores nulos
        StickerTypeView stickerTypeView = new StickerTypeView(null, null);

        // Verificando se o objeto foi criado corretamente com valores nulos
        assertNotNull(stickerTypeView);
        assertNull(stickerTypeView.id());
        assertNull(stickerTypeView.description());
    }

    @Test
    void testStickerTypeViewImmutability() {
        // Criando a instância do DTO
        Long id = faker.number().randomNumber();
        String description = faker.lorem().sentence();
        StickerTypeView stickerTypeView = new StickerTypeView(id, description);

        // Verificando que o objeto é imutável, ou seja, não pode alterar os valores após a criação
        assertThrows(UnsupportedOperationException.class, () -> {
            // Tentando alterar um campo do record (o que deve ser impossível)
            stickerTypeView.id();
            stickerTypeView.description();
        });
    }
}
