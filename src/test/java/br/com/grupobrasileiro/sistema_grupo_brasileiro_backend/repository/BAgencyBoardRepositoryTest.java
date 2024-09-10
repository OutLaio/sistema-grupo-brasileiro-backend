package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.BAgencyBoardRepository;

@DataJpaTest
@ActiveProfiles("test")
public class BAgencyBoardRepositoryTest {

    @Autowired
    private BAgencyBoardRepository bAgencyBoardRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void testSaveAndFindById() {
       
        Project project = new Project();
        project.setTitle("Test Project Title");
        project.setDescription("Test Project Description");
        project.setProgress(50);
        project.setStatus("In Progress");
        projectRepository.save(project);

       
        BAgencyBoard board = new BAgencyBoard();
        board.setBoardLocation("Test Location");
        board.setCompanySharing(true);
        board.setBoardType("Test Type");
        board.setMaterial("Test Material");
        board.setObservations("Test Observations");
        board.setProject(project);

        bAgencyBoardRepository.save(board);

        
        Optional<BAgencyBoard> foundBoard = bAgencyBoardRepository.findById(board.getId());

        assertThat(foundBoard).isPresent();
        assertThat(foundBoard.get().getBoardLocation()).isEqualTo("Test Location");
        assertThat(foundBoard.get().getProject()).isEqualTo(project);
    }

    @Test
    public void testSaveAndFindByNonExistentId() {
          Optional<BAgencyBoard> foundBoard = bAgencyBoardRepository.findById(Long.MAX_VALUE);

        assertThat(foundBoard).isNotPresent();
    }
}
