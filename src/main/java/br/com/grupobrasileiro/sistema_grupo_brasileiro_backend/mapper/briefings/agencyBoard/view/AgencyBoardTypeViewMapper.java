package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.view;


import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.view.AgencyBoardTypeView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import org.springframework.stereotype.Component;

@Component
public class AgencyBoardTypeViewMapper implements Mapper<AgencyBoardType, AgencyBoardTypeView> {

    @Override
    public AgencyBoardTypeView map(AgencyBoardType agencyBoardType) {

        return new AgencyBoardTypeView(
                agencyBoardType.getId(),
                agencyBoardType.getDescription()
        );
    }
}
