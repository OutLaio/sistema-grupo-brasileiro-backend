package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.dialogbox.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.dialogbox.view.DialogBoxView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.DialogBox;
import org.springframework.stereotype.Component;

@Component
public class DialogBoxViewMapper implements Mapper<DialogBox, DialogBoxView> {
    @Override
    public DialogBoxView map(DialogBox i) {
        String fullName = i.getEmployee().getName() + " " + i.getEmployee().getLastName();
        return new DialogBoxView(
                i.getId(),
                fullName,
                i.getBriefing().getProject().getTitle(),
                i.getTime(),
                i.getDialog()
        );
    }
}
