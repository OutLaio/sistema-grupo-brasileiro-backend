package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.github.javafaker.Faker;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;

/**
 * Tests the BoardTypeViewMapper class.
 * Verifies if the mapping handles null BoardType correctly.
 */
public class BoardTypeViewMapperTest {

    @InjectMocks
    private BoardTypeViewMapper boardTypeViewMapper;

    private Faker faker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker(); // Initializes the Faker
    }

    /**
     * Tests the mapping of BoardType to BoardTypeView.
     * Verifies if a BoardType is correctly mapped to a BoardTypeView.
     */
    @Test
    @DisplayName("Maps BoardType to BoardTypeView correctly")
    void mapBoardTypeToBoardTypeView() {
        // Arrange
        BoardType boardType = new BoardType();
        boardType.setId(faker.number().randomNumber());
        boardType.setDescription(faker.lorem().word());

        // Act
        BoardTypeView result = boardTypeViewMapper.map(boardType);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(boardType.getId());
        assertThat(result.description()).isEqualTo(boardType.getDescription());
    }

    /**
     * Tests that the map method returns null when receiving a null BoardType.
     */
    @Test
    @DisplayName("Returns null for null BoardType")
    void returnNullForNullBoardType() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            boardTypeViewMapper.map(null);
        }, "Mapping should throw a NullPointerException for null BoardType");
    }

    /**
     * Tests the mapping of BoardType with null fields to BoardTypeView.
     * Verifies if the map method handles null fields in BoardType correctly.
     */
    @Test
    @DisplayName("Maps BoardType with null fields to BoardTypeView correctly")
    void mapBoardTypeWithNullFieldsToBoardTypeView() {
        // Arrange
        BoardType boardType = new BoardType();
        boardType.setId(null);
        boardType.setDescription(null);

        // Act
        BoardTypeView result = boardTypeViewMapper.map(boardType);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.id()).isNull();
        assertThat(result.description()).isNull();
    }

    /**
     * Tests the mapping of BoardType with only an ID.
     * Verifies if the map method handles a BoardType with only an ID correctly.
     */
    @Test
    @DisplayName("Maps BoardType with only ID to BoardTypeView correctly")
    void mapBoardTypeWithOnlyId() {
        // Arrange
        BoardType boardType = new BoardType();
        boardType.setId(faker.number().randomNumber());
        boardType.setDescription(null); // No description

        // Act
        BoardTypeView result = boardTypeViewMapper.map(boardType);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(boardType.getId());
        assertThat(result.description()).isNull();
    }

    /**
     * Tests the mapping of BoardType with an empty description.
     * Verifies if the map method handles an empty description correctly.
     */
    @Test
    @DisplayName("Maps BoardType with empty description to BoardTypeView correctly")
    void mapBoardTypeWithEmptyDescription() {
        // Arrange
        BoardType boardType = new BoardType();
        boardType.setId(faker.number().randomNumber());
        boardType.setDescription(""); // Empty description

        // Act
        BoardTypeView result = boardTypeViewMapper.map(boardType);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(boardType.getId());
        assertThat(result.description()).isEqualTo(""); // Expecting empty string
    }
}

