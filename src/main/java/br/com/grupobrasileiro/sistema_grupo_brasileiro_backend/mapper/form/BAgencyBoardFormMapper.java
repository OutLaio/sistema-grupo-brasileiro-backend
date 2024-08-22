package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.BAgencyBoardForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.BAgencyBoard;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.ProjectRepository;

@Component
public class BAgencyBoardFormMapper implements Mapper<BAgencyBoardForm, BAgencyBoard> {

    private final ProjectRepository projectRepository;

    public BAgencyBoardFormMapper(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
	 * Converte o BAgencyBoardForm em um objeto BAgencyBoard.
	 * @param bAgencyBoardForm - BAgencyBoardForm
	 * @return BAgencyBoard (id, boardLocation, companySharing, boardType, material, observations, project)
	 * */
    @Override
    public BAgencyBoard map(BAgencyBoardForm bAgencyBoardForm) {
        // Busca o projeto pelo ID fornecido no formulário
        Project project = projectRepository.findById(bAgencyBoardForm.projectId())
            .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado com o ID: " + bAgencyBoardForm.projectId()));

        BAgencyBoard bAgencyBoard = new BAgencyBoard(
            null,
			bAgencyBoardForm.boardLocation(),
			bAgencyBoardForm.companySharing(),
			bAgencyBoardForm.boardType(),
			bAgencyBoardForm.material(),
			bAgencyBoardForm.observations(),
			project
        );

        return bAgencyBoard;
    }
}
