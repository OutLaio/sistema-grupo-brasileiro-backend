package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.BAgencyBoardsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.AgencyBoardTypeRepository;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard.BoardTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BAgencyBoardFormMapper implements Mapper<BAgencyBoardsForm, BAgencyBoard> {

    @Autowired
    private BoardTypeRepository boardTypeRepository;

    @Autowired
    private AgencyBoardTypeRepository agencyBoardTypeRepository;

    @Override
    public BAgencyBoard map(BAgencyBoardsForm form) {
        return new BAgencyBoard(
            null,
            agencyBoardTypeRepository.findById(form.idAgencyBoardType()).orElseThrow(
                () -> new EntityNotFoundException("Agency Board Type not found with id: " + form.idAgencyBoardType())
            ),
            form.idBoardType() == null ? null : boardTypeRepository.findById(form.idBoardType()).orElseThrow(
                () -> new EntityNotFoundException("Board Type not found with id: " + form.idBoardType())
            ),
            null,
            form.boardLocation(),
            form.observation(),
            null,
            null
        );
    }
}
