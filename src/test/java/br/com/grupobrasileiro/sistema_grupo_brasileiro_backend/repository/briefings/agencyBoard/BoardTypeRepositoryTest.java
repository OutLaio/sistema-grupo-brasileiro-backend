package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BoardTypeRepositoryTest {

    @Autowired
    private BoardTypeRepository boardTypeRepository;

   
    @BeforeEach
    void setUp() {
        // Este método é executado antes de cada teste
    }

    /**
     * Testa a criação e recuperação de um BoardType.
     */
    @Test
    @Rollback(false) 
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

    /**
     * Testa a deleção de um BoardType.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should delete a BoardType")
    void testDeleteBoardType() {
        // Arrange
        BoardType boardType = new BoardType();
        boardType.setDescription("Tipo de Quadro Para Deletar");

        // Salvando o BoardType
        BoardType savedBoardType = boardTypeRepository.save(boardType);

        // Act
        boardTypeRepository.deleteById(savedBoardType.getId());
        BoardType retrievedBoardType = boardTypeRepository.findById(savedBoardType.getId()).orElse(null);

        // Assert
        assertThat(retrievedBoardType).isNull(); // Deve ser nulo após a deleção
    }

    /**
     * Testa a atualização de um BoardType.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should update a BoardType")
    void testUpdateBoardType() {
        // Arrange
        BoardType boardType = new BoardType();
        boardType.setDescription("Tipo de Quadro Original");

        // Salvando o BoardType
        BoardType savedBoardType = boardTypeRepository.save(boardType);

        // Act: Atualizando o BoardType
        savedBoardType.setDescription("Tipo de Quadro Atualizado");
        boardTypeRepository.save(savedBoardType);

        // Assert: Recuperando o BoardType atualizado
        BoardType updatedBoardType = boardTypeRepository.findById(savedBoardType.getId()).orElse(null);
        assertThat(updatedBoardType).isNotNull();
        assertThat(updatedBoardType.getDescription()).isEqualTo("Tipo de Quadro Atualizado");
    }

    /**
     * Testa a recuperação de todos os BoardTypes.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should retrieve all BoardTypes")
    void testFindAllBoardTypes() {
        // Arrange
        BoardType boardType1 = new BoardType();
        boardType1.setDescription("Tipo de Quadro 1");
        boardTypeRepository.save(boardType1);

        BoardType boardType2 = new BoardType();
        boardType2.setDescription("Tipo de Quadro 2");
        boardTypeRepository.save(boardType2);

        // Act
        List<BoardType> allBoardTypes = boardTypeRepository.findAll();

        // Assert
        assertThat(allBoardTypes).hasSize(2); // Deve encontrar 2 BoardTypes
    }

    /**
     * Testa a recuperação de um BoardType inexistente.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should return null for a non-existent BoardType")
    void testFindNonExistentBoardType() {
        // Act
        BoardType retrievedBoardType = boardTypeRepository.findById(999L).orElse(null); // ID que não existe

        // Assert
        assertThat(retrievedBoardType).isNull(); // Deve ser nulo
    }
}