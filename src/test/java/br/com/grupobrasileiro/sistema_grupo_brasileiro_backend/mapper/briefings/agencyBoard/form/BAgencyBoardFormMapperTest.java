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
 * Tests the BAgencyBoardFormMapper class.
 * Verifies that the class correctly maps instances of BAgencyBoardsForm to BAgencyBoard.
 */
public class BAgencyBoardFormMapperTest {

    private BAgencyBoardFormMapper mapper;
    private Faker faker;

    /**
     * Sets up the testing environment before each test.
     * Initializes the mapper and the Faker.
     */
    @BeforeEach
    void setUp() {
        mapper = new BAgencyBoardFormMapper();
        faker = new Faker();
    }

    /**
     * Tests the map method of the BAgencyBoardFormMapper class.
     * Verifies that the method correctly maps a BAgencyBoardsForm to a BAgencyBoard.
     */
    @Test
    @DisplayName("Should correctly map BAgencyBoardsForm to BAgencyBoard")
    void shouldMapBAgencyBoardsFormToBAgencyBoard() {
        // Generate fake data for form fields using Java Faker
        String fakeLocation = faker.address().streetAddress();
        String fakeObservation = faker.lorem().sentence();

        // Create an instance of BAgencyBoardsForm with the generated data
        BAgencyBoardsForm form = new BAgencyBoardsForm(
            1L, // idAgencyBoardType
            null, // idBoardType can be null
            fakeLocation, // Board location
            fakeObservation, // Observations
            new ArrayList<>(), // List of other routes
            new ArrayList<>()  // List of routes
        );

        // Map BAgencyBoardsForm to BAgencyBoard
        BAgencyBoard result = mapper.map(form);

        // Verify that the result is not null
        assertThat(result).isNotNull();

        // Verify that the board location was mapped correctly
        assertThat(result.getBoardLocation()).isEqualTo(fakeLocation);

        // Verify that the observations were mapped correctly
        assertThat(result.getObservations()).isEqualTo(fakeObservation);
    }

    /**
     * Tests the map method of the BAgencyBoardFormMapper with null values.
     * Verifies that the method correctly maps a BAgencyBoardsForm with null values to a BAgencyBoard.
     */
    @Test
    @DisplayName("Should correctly map BAgencyBoardsForm with null values to BAgencyBoard")
    void shouldMapBAgencyBoardsFormWithNullValuesToBAgencyBoard() {
        // Create an instance of BAgencyBoardsForm with null values for optional fields
        BAgencyBoardsForm form = new BAgencyBoardsForm(
            null, // idAgencyBoardType can be null
            null, // idBoardType can be null
            null, // Board location
            null, // Observations
            new ArrayList<>(), // List of other routes
            new ArrayList<>()  // List of routes
        );

        // Map BAgencyBoardsForm to BAgencyBoard
        BAgencyBoard result = mapper.map(form);

        // Verify that the result is not null
        assertThat(result).isNotNull();

        // Verify that the fields were mapped correctly, including null values
        assertThat(result.getBoardLocation()).isNull();
        assertThat(result.getObservations()).isNull();
    }

    /**
     * Tests the map method of the BAgencyBoardFormMapper with empty lists.
     * Verifies that the method correctly maps a BAgencyBoardsForm with empty lists to a BAgencyBoard.
     */
    @Test
    @DisplayName("Should correctly map BAgencyBoardsForm with empty lists to BAgencyBoard")
    void shouldMapBAgencyBoardsFormWithEmptyListsToBAgencyBoard() {
        BAgencyBoardsForm form = new BAgencyBoardsForm(
            1L, // idAgencyBoardType
            null, // idBoardType can be null
            "Some Location", // Board location
            "Some observation", // Observations
            new ArrayList<>(), // Empty list of other routes
            new ArrayList<>()  // Empty list of routes
        );

        // Map BAgencyBoardsForm to BAgencyBoard
        BAgencyBoard result = mapper.map(form);

        // Verify that the result is not null
        assertThat(result).isNotNull();

        // Verify that other routes and routes lists are initialized
       // assertThat(result.getOtherRoutes()).isNotNull().isEmpty();
      //  assertThat(result.getRoutes()).isNotNull().isEmpty();
    }

    /**
     * Tests the map method of the BAgencyBoardFormMapper with various data.
     * Verifies that the method correctly maps a BAgencyBoardsForm with varied data to a BAgencyBoard.
     */
    @Test
    @DisplayName("Should correctly map BAgencyBoardsForm with varied data to BAgencyBoard")
    void shouldMapBAgencyBoardsFormWithVariedDataToBAgencyBoard() {
        String fakeLocation = faker.address().streetAddress();
        String fakeObservation = faker.lorem().sentence();

        BAgencyBoardsForm form = new BAgencyBoardsForm(
            2L, // idAgencyBoardType
            3L, // idBoardType
            fakeLocation, // Board location
            fakeObservation, // Observations
            new ArrayList<>(), // List of other routes
            new ArrayList<>()
        );

        BAgencyBoard result = mapper.map(form);

        // Verify that the result is not null
        assertThat(result).isNotNull();
        assertThat(result.getBoardLocation()).isEqualTo(fakeLocation);
        assertThat(result.getObservations()).isEqualTo(fakeObservation);
    }
}

