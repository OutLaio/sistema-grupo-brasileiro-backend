package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BoardTypeRepositoryTest {

    @Autowired
    private BoardTypeRepository boardTypeRepository;

    @BeforeEach
    void setUp() {
        // Este método é executado antes de cada teste
    }

    @Test
    @Rollback(false) // Ajuste para true se não quiser persistir no banco de dados
    @DisplayName("Should create and retrieve a BoardType")
    void testCreateAndRetrieveBoardType() {
        // Arrange
        BoardType boardType = new BoardType();
        boardType.setDescription("Tipo de Quadro");

        // Act
        BoardType savedBoardType = boardTypeRepository.save(boardType);
        BoardType retrievedBoardType = boardTypeRepository.findById(savedBoardType.getId()).orElse(null);

        // Assert
        assertThat(retrievedBoardType).isNotNull();
        assertThat(retrievedBoardType.getId()).isEqualTo(savedBoardType.getId());
        assertThat(retrievedBoardType.getDescription()).isEqualTo("Tipo de Quadro");
    }
}
