package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.BAgencyBoardsForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BAgencyBoard;

import org.springframework.stereotype.Component;

@Component
public class BAgencyBoardFormMapper implements Mapper<BAgencyBoardsForm, BAgencyBoard> {

    @Override
    public BAgencyBoard map(BAgencyBoardsForm form) {

        return new BAgencyBoard(
                null,
                null,
                null,
                null,
                form.boardLocation(),
                form.observation(),
                null,
                null
        );
    }
}
