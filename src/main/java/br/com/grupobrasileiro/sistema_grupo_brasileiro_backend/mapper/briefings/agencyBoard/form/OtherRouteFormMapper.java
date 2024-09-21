package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.agencyBoard.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.agencyBoards.form.OtherRoutesForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.OtherRoute;
import org.springframework.stereotype.Component;

@Component
public class OtherRouteFormMapper implements Mapper<OtherRoutesForm, OtherRoute> {

    @Override
    public OtherRoute map(OtherRoutesForm otherRoutesForm) {
        return new OtherRoute(
                null,
                null,
                otherRoutesForm.company(),
                otherRoutesForm.city(),
                otherRoutesForm.type()
        );
    }
}
