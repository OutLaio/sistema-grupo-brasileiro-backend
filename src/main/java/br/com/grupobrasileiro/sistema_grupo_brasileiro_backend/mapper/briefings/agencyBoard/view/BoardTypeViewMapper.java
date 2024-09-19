package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;


import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.BoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.BoardType;

@Component
public class BoardTypeViewMapper implements Mapper<BoardType, BoardTypeView> {

    @Override
    public BoardTypeView map(BoardType boardType) {

        return new BoardTypeView(
                boardType.getId(),
                boardType.getDescription()
        );
    }
}
