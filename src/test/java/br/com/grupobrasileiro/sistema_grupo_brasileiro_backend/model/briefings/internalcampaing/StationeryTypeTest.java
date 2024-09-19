package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaing;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.internalcampaign.StationeryType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StationeryTypeTest {

    private StationeryType stationeryType;
    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
        stationeryType = new StationeryType();
        stationeryType.setId(faker.number().randomNumber());
        stationeryType.setDescription(faker.commerce().productName());
    }

    @Test
    void testStationeryType() {
        // Verifica se os dados foram definidos corretamente
        assertThat(stationeryType.getId()).isNotNull();
        assertThat(stationeryType.getDescription()).isNotNull().isNotEmpty();
    }
}
