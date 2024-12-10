package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view.StickerInformationTypeView;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StickerInformationTypeViewTest {

    private Faker faker;

    @BeforeEach
    void setUp() {
        // Inicializa o Faker para gerar dados aleatórios
        faker = new Faker();
    }

    @Test
    void testCreateStickerInformationTypeView() {
        // Gerando dados falsos para os campos do DTO
        Long id = faker.number().randomNumber();
        String description = faker.lorem().sentence();

        // Criando a instância do DTO StickerInformationTypeView
        StickerInformationTypeView stickerInformationTypeView = new StickerInformationTypeView(id, description);

        // Verificando se o objeto foi criado corretamente e os valores estão corretos
        assertNotNull(stickerInformationTypeView);
        assertEquals(id, stickerInformationTypeView.id());
        assertEquals(description, stickerInformationTypeView.description());
    }

    @Test
    void testCreateStickerInformationTypeViewWithNullValues() {
        // Criando a instância do DTO com valores nulos
        StickerInformationTypeView stickerInformationTypeView = new StickerInformationTypeView(null, null);

        // Verificando se o objeto foi criado corretamente com valores nulos
        assertNotNull(stickerInformationTypeView);
        assertNull(stickerInformationTypeView.id());
        assertNull(stickerInformationTypeView.description());
    }
}
