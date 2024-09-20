package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BAgencyBoardRepositoryTest {

    @Autowired
    private BAgencyBoardRepository bAgencyBoardRepository;

    // Mock objects
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
}
