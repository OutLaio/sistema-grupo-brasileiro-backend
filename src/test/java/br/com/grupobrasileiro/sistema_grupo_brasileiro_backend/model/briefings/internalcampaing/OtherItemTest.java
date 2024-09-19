package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaing;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.OtherItem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OtherItemTest {

    private OtherItem otherItem;
    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
        otherItem = new OtherItem();
        otherItem.setId(faker.number().randomNumber());
        otherItem.setDescription(faker.lorem().sentence());
    }

    @Test
    void testOtherItem() {
        // Verifica se os dados foram definidos corretamente
        assertThat(otherItem.getId()).isNotNull();
        assertThat(otherItem.getDescription()).isNotNull().isNotEmpty();
    }
}
