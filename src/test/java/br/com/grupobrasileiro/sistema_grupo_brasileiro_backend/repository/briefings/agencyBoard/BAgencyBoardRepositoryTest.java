package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;

@DataJpaTest
public class BAgencyBoardRepositoryTest {

    @Autowired
    private BAgencyBoardRepository bAgencyBoardRepository;

    // Objetos mock
    private AgencyBoardType agencyBoardType;
    private BoardType boardType;
    private Briefing briefing;

    @BeforeEach
    void setUp() {
        // Configura os objetos mock para os testes
        agencyBoardType = new AgencyBoardType();
        agencyBoardType.setDescription("Tipo de Placa de Agência");

        boardType = new BoardType();
        boardType.setDescription("Tipo de Quadro");

        briefing = new Briefing();
        // Configure outros atributos necessários para o Briefing, se necessário
    }

    /**
     * Testa a criação e recuperação de um BAgencyBoard.
     */
    @Test
    @Rollback(false) // Ajuste para true se não quiser persistir no banco de dados
    @DisplayName("Should create and retrieve a BAgencyBoard")
    void testCreateAndRetrieveBAgencyBoard() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setAgencyBoardType(agencyBoardType);
        bAgencyBoard.setBoardType(boardType);
        bAgencyBoard.setBriefing(briefing);
        bAgencyBoard.setBoardLocation("Localização do Quadro");
        bAgencyBoard.setObservations("Observações do Quadro");

        // Act
        BAgencyBoard savedBAgencyBoard = bAgencyBoardRepository.save(bAgencyBoard);
        BAgencyBoard retrievedBAgencyBoard = bAgencyBoardRepository.findById(savedBAgencyBoard.getId()).orElse(null);

        // Assert
        assertThat(retrievedBAgencyBoard).isNotNull();
        assertThat(retrievedBAgencyBoard.getId()).isEqualTo(savedBAgencyBoard.getId());
        assertThat(retrievedBAgencyBoard.getBoardLocation()).isEqualTo("Localização do Quadro");
        assertThat(retrievedBAgencyBoard.getObservations()).isEqualTo("Observações do Quadro");
    }

    /**
     * Testa a deleção de um BAgencyBoard.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should delete a BAgencyBoard")
    void testDeleteBAgencyBoard() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setAgencyBoardType(agencyBoardType);
        bAgencyBoard.setBoardType(boardType);
        bAgencyBoard.setBriefing(briefing);
        bAgencyBoard.setBoardLocation("Localização do Quadro");
        bAgencyBoard.setObservations("Observações do Quadro");
        
        // Salvando o BAgencyBoard
        BAgencyBoard savedBAgencyBoard = bAgencyBoardRepository.save(bAgencyBoard);

        // Act
        bAgencyBoardRepository.deleteById(savedBAgencyBoard.getId());
        BAgencyBoard retrievedBAgencyBoard = bAgencyBoardRepository.findById(savedBAgencyBoard.getId()).orElse(null);

        // Assert
        assertThat(retrievedBAgencyBoard).isNull(); // Deve ser nulo após a deleção
    }

    /**
     * Testa a atualização de um BAgencyBoard.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should update a BAgencyBoard")
    void testUpdateBAgencyBoard() {
        // Arrange
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setAgencyBoardType(agencyBoardType);
        bAgencyBoard.setBoardType(boardType);
        bAgencyBoard.setBriefing(briefing);
        bAgencyBoard.setBoardLocation("Localização do Quadro");
        bAgencyBoard.setObservations("Observações do Quadro");

        // Salvando o BAgencyBoard
        BAgencyBoard savedBAgencyBoard = bAgencyBoardRepository.save(bAgencyBoard);

        // Act: Atualizando o BAgencyBoard
        savedBAgencyBoard.setBoardLocation("Localização Atualizada");
        savedBAgencyBoard.setObservations("Observações Atualizadas");
        bAgencyBoardRepository.save(savedBAgencyBoard);

        // Assert: Recuperando o BAgencyBoard atualizado
        BAgencyBoard updatedBAgencyBoard = bAgencyBoardRepository.findById(savedBAgencyBoard.getId()).orElse(null);
        assertThat(updatedBAgencyBoard).isNotNull();
        assertThat(updatedBAgencyBoard.getBoardLocation()).isEqualTo("Localização Atualizada");
        assertThat(updatedBAgencyBoard.getObservations()).isEqualTo("Observações Atualizadas");
    }

    /**
     * Testa a recuperação de todos os BAgencyBoards.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should retrieve all BAgencyBoards")
    void testFindAllBAgencyBoards() {
        // Arrange
        BAgencyBoard bAgencyBoard1 = new BAgencyBoard();
        bAgencyBoard1.setAgencyBoardType(agencyBoardType);
        bAgencyBoard1.setBoardType(boardType);
        bAgencyBoard1.setBriefing(briefing);
        bAgencyBoard1.setBoardLocation("Localização 1");
        bAgencyBoard1.setObservations("Observações 1");
        bAgencyBoardRepository.save(bAgencyBoard1);

        BAgencyBoard bAgencyBoard2 = new BAgencyBoard();
        bAgencyBoard2.setAgencyBoardType(agencyBoardType);
        bAgencyBoard2.setBoardType(boardType);
        bAgencyBoard2.setBriefing(briefing);
        bAgencyBoard2.setBoardLocation("Localização 2");
        bAgencyBoard2.setObservations("Observações 2");
        bAgencyBoardRepository.save(bAgencyBoard2);

        // Act
        List<BAgencyBoard> allBAgencyBoards = bAgencyBoardRepository.findAll();

        // Assert
        assertThat(allBAgencyBoards).hasSize(2); // Deve encontrar 2 BAgencyBoards
    }

    /**
     * Testa a recuperação de um BAgencyBoard inexistente.
     */
    @Test
    @Rollback(false)
    @DisplayName("Should return null for a non-existent BAgencyBoard")
    void testFindNonExistentBAgencyBoard() {
        // Act
        BAgencyBoard retrievedBAgencyBoard = bAgencyBoardRepository.findById(999L).orElse(null); // ID que não existe

        // Assert
        assertThat(retrievedBAgencyBoard).isNull(); 
    }
}
