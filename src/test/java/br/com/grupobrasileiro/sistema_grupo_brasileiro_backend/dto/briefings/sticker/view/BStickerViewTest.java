package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view.BStickerView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view.StickerInformationTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.sticker.view.StickerTypeView;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BStickerViewTest {

    private Faker faker;

    @BeforeEach
    void setUp() {
        // Inicializa o Faker para gerar dados aleatórios
        faker = new Faker();
    }

    @Test
    void testCreateBStickerView() {
        // Gerando dados falsos com o Faker
        Long id = faker.number().randomNumber();
        StickerTypeView stickerType = new StickerTypeView(id, faker.commerce().productName());
        StickerInformationTypeView stickerInformationType = new StickerInformationTypeView(faker.number().randomNumber(), faker.commerce().department());
        String sector = faker.commerce().department();
        String observations = faker.lorem().sentence();

        // Criação do BStickerView com os dados gerados
        BStickerView bStickerView = new BStickerView(id, stickerType, stickerInformationType, sector, observations);

        // Verifica se os dados do BStickerView estão corretos
        assertNotNull(bStickerView);
        assertEquals(id, bStickerView.id());
        assertEquals(stickerType, bStickerView.stickerType());
        assertEquals(stickerInformationType, bStickerView.stickerInformationType());
        assertEquals(sector, bStickerView.sector());
        assertEquals(observations, bStickerView.observations());
    }

    @Test
    void testCreateBStickerViewWithNullValues() {
        // Testa o comportamento com valores nulos
        BStickerView bStickerView = new BStickerView(null, null, null, null, null);

        // Verifica se o objeto BStickerView é criado com valores nulos
        assertNotNull(bStickerView);
        assertNull(bStickerView.id());
        assertNull(bStickerView.stickerType());
        assertNull(bStickerView.stickerInformationType());
        assertNull(bStickerView.sector());
        assertNull(bStickerView.observations());
    }

    @Test
    void testCreateBStickerViewWithFakeData() {
        // Gerando dados para testar a criação do objeto BStickerView com o Faker
        Long id = faker.number().randomNumber();
        StickerTypeView stickerType = new StickerTypeView(id, faker.commerce().productName());
        StickerInformationTypeView stickerInformationType = new StickerInformationTypeView(faker.number().randomNumber(), faker.commerce().department());
        String sector = faker.commerce().department();
        String observations = faker.lorem().sentence();

        BStickerView bStickerView = new BStickerView(id, stickerType, stickerInformationType, sector, observations);

        // Teste de igualdade de valores
        assertEquals(id, bStickerView.id());
        assertEquals(stickerType, bStickerView.stickerType());
        assertEquals(stickerInformationType, bStickerView.stickerInformationType());
        assertEquals(sector, bStickerView.sector());
        assertEquals(observations, bStickerView.observations());
    }

    @Test
    void testCreateBStickerViewWithRandomSector() {
        // Testa o comportamento de geração de setores aleatórios
        String randomSector = faker.commerce().department();
        BStickerView bStickerView = new BStickerView(faker.number().randomNumber(), 
                                                     new StickerTypeView(faker.number().randomNumber(), faker.commerce().productName()), 
                                                     new StickerInformationTypeView(faker.number().randomNumber(), faker.commerce().department()), 
                                                     randomSector, 
                                                     faker.lorem().sentence());

        assertNotNull(bStickerView);
        assertEquals(randomSector, bStickerView.sector());
    }
}
