package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.BAgencyBoardRepository;

@DataJpaTest
@ActiveProfiles("test") // Use a profile for testing if needed
public class BAgencyBoardRepositoryTest {

    @Autowired
    private BAgencyBoardRepository bAgencyBoardRepository;

    @Test
    public void testCreateAndFindBAgencyBoard() {
        // Create a new BAgencyBoard entity
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setBoardLocation("Location A");
        bAgencyBoard.setCompanySharing(true);
        bAgencyBoard.setBoardType("Type A");
        bAgencyBoard.setMaterial("Material A");
        bAgencyBoard.setObservations("Observations A");

        // Save the entity
        BAgencyBoard savedBAgencyBoard = bAgencyBoardRepository.save(bAgencyBoard);

        // Fetch the entity by ID
        BAgencyBoard foundBAgencyBoard = bAgencyBoardRepository.findById(savedBAgencyBoard.getId()).orElse(null);

        // Assertions
        assertThat(foundBAgencyBoard).isNotNull();
        assertThat(foundBAgencyBoard.getBoardLocation()).isEqualTo("Location A");
        assertThat(foundBAgencyBoard.getCompanySharing()).isTrue();
        assertThat(foundBAgencyBoard.getBoardType()).isEqualTo("Type A");
        assertThat(foundBAgencyBoard.getMaterial()).isEqualTo("Material A");
        assertThat(foundBAgencyBoard.getObservations()).isEqualTo("Observations A");
    }

    @Test
    public void testDeleteBAgencyBoard() {
        // Create and save a new BAgencyBoard entity
        BAgencyBoard bAgencyBoard = new BAgencyBoard();
        bAgencyBoard.setBoardLocation("Location B");
        bAgencyBoard.setCompanySharing(false);
        bAgencyBoard.setBoardType("Type B");
        bAgencyBoard.setMaterial("Material B");
        bAgencyBoard.setObservations("Observations B");

        BAgencyBoard savedBAgencyBoard = bAgencyBoardRepository.save(bAgencyBoard);

        // Delete the entity
        bAgencyBoardRepository.deleteById(savedBAgencyBoard.getId());

        // Attempt to fetch the entity
        BAgencyBoard foundBAgencyBoard = bAgencyBoardRepository.findById(savedBAgencyBoard.getId()).orElse(null);

        // Assertions
        assertThat(foundBAgencyBoard).isNull();
    }
}
