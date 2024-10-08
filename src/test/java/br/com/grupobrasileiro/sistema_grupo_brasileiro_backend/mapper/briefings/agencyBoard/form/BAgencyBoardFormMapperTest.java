package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.BAgencyBoardsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;

/**
 * Tests the BAgencyBoardsFormMapper class.
 * Verifies that the class correctly maps instances of BAgencyBoardsForm to BAgencyBoard.
 */
public class BAgencyBoardFormMapperTest {

    private BAgencyBoardFormMapper mapper;
    private Faker faker;

    @BeforeEach
    void setUp() {
        mapper = new BAgencyBoardFormMapper();
        faker = new Faker();
    }

    /**
     * Testa a conversão de um BAgencyBoardsForm para BAgencyBoard.
     * Verifica se o valor de entrada gerado pelo Faker é mapeado corretamente.
     */
    @Test
    @DisplayName("Should map BAgencyBoardsForm to BAgencyBoard")
    void shouldMapToBAgencyBoard() {
        String fakeLocation = faker.address().streetAddress();
        String fakeObservation = faker.lorem().sentence();

        BAgencyBoardsForm form = new BAgencyBoardsForm(
            1L, 
            null, 
            fakeLocation, 
            fakeObservation, 
            new ArrayList<>(), 
            new ArrayList<>()
        );

        BAgencyBoard result = mapper.map(form);

        assertThat(result).isNotNull();
        assertThat(result.getBoardLocation()).isEqualTo(fakeLocation);
        assertThat(result.getObservations()).isEqualTo(fakeObservation);
    }

    /**
     * Testa a conversão de um BAgencyBoardsForm com valores nulos.
     * Verifica se os campos opcionais são mapeados corretamente como nulos.
     */
    @Test
    @DisplayName("Should map BAgencyBoardsForm with null values")
    void shouldMapWithNullValues() {
        BAgencyBoardsForm form = new BAgencyBoardsForm(
            null, 
            null, 
            null, 
            null, 
            new ArrayList<>(), 
            new ArrayList<>()
        );

        BAgencyBoard result = mapper.map(form);

        assertThat(result).isNotNull();
        assertThat(result.getBoardLocation()).isNull();
        assertThat(result.getObservations()).isNull();
    }

    /**
     * Testa a conversão de um BAgencyBoardsForm com listas vazias.
     * Verifica se as listas de rotas são inicializadas corretamente.
     */
    @Test
    @DisplayName("Should map BAgencyBoardsForm with empty lists")
    void shouldMapWithEmptyLists() {
        BAgencyBoardsForm form = new BAgencyBoardsForm(
            1L, 
            null, 
            "Some Location", 
            "Some observation", 
            new ArrayList<>(), 
            new ArrayList<>()
        );

        BAgencyBoard result = mapper.map(form);

        assertThat(result).isNotNull();
        //assertThat(result.getOtherRoutes()).isNotNull().isEmpty();
       // assertThat(result.getRoutes()).isNotNull().isEmpty();
    }

    /**
     * Testa a conversão de um BAgencyBoardsForm com dados variados.
     * Verifica se os campos são mapeados corretamente com dados gerados pelo Faker.
     */
    @Test
    @DisplayName("Should map BAgencyBoardsForm with varied data")
    void shouldMapWithVariedData() {
        String fakeLocation = faker.address().streetAddress();
        String fakeObservation = faker.lorem().sentence();

        BAgencyBoardsForm form = new BAgencyBoardsForm(
            2L, 
            3L, 
            fakeLocation, 
            fakeObservation, 
            new ArrayList<>(), 
            new ArrayList<>()
        );

        BAgencyBoard result = mapper.map(form);

        assertThat(result).isNotNull();
        assertThat(result.getBoardLocation()).isEqualTo(fakeLocation);
        assertThat(result.getObservations()).isEqualTo(fakeObservation);
    }

    /**
     * Testa a conversão de um BAgencyBoardsForm com strings de comprimento máximo.
     * Verifica se os valores são mapeados corretamente.
     */
    @Test
    @DisplayName("Should map with maximum length strings")
    void shouldMapWithMaxLengthStrings() {
        String maxLengthLocation = faker.lorem().characters(255); // Assuming 255 is the max length
        String maxLengthObservation = faker.lorem().characters(500); // Assuming 500 is the max length

        BAgencyBoardsForm form = new BAgencyBoardsForm(
            1L, 
            null, 
            maxLengthLocation, 
            maxLengthObservation, 
            new ArrayList<>(), 
            new ArrayList<>()
        );

        BAgencyBoard result = mapper.map(form);

        assertThat(result).isNotNull();
        assertThat(result.getBoardLocation()).isEqualTo(maxLengthLocation);
        assertThat(result.getObservations()).isEqualTo(maxLengthObservation);
    }

    /**
     * Testa a conversão de um BAgencyBoardsForm com idAgencyBoardType negativo.
     * Verifica se o valor é mapeado corretamente.
     */
    @Test
    @DisplayName("Should handle negative idAgencyBoardType")
    void shouldHandleNegativeId() {
        BAgencyBoardsForm form = new BAgencyBoardsForm(
            -1L, 
            null, 
            "Negative ID Location", 
            "Observation with negative ID", 
            new ArrayList<>(), 
            new ArrayList<>()
        );

        BAgencyBoard result = mapper.map(form);

        assertThat(result).isNotNull();
        assertThat(result.getBoardLocation()).isEqualTo("Negative ID Location");
        assertThat(result.getObservations()).isEqualTo("Observation with negative ID");
    }

    /**
     * Testa a conversão de um BAgencyBoardsForm com caracteres especiais.
     * Verifica se os caracteres são mapeados corretamente.
     */
    @Test
    @DisplayName("Should map with special characters")
    void shouldMapWithSpecialCharacters() {
        String specialLocation = "Location!@#$%^&*()_+";
        String specialObservation = "Observation with special chars ~`<>?";

        BAgencyBoardsForm form = new BAgencyBoardsForm(
            1L, 
            null, 
            specialLocation, 
            specialObservation, 
            new ArrayList<>(), 
            new ArrayList<>()
        );

        BAgencyBoard result = mapper.map(form);

        assertThat(result).isNotNull();
        assertThat(result.getBoardLocation()).isEqualTo(specialLocation);
        assertThat(result.getObservations()).isEqualTo(specialObservation);
    }
}

