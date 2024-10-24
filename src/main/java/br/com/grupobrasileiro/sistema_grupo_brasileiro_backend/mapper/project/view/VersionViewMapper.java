package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.view.VersionView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Version;
import org.springframework.stereotype.Component;

@Component
public class VersionViewMapper implements Mapper<Version, VersionView> {
    @Override
    public VersionView map(Version i) {
        return new VersionView(
                i.getId(),
                i.getNumVersion(),
                i.getProductLink(),
                i.getClientApprove(),
                i.getSupervisorApprove(),
                i.getFeedback()
        );
    }
}
