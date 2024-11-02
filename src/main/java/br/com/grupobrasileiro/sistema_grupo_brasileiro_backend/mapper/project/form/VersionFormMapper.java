package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.project.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.projects.form.NewVersionForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Version;
import org.springframework.stereotype.Component;

@Component
public class VersionFormMapper implements Mapper<NewVersionForm, Version> {

    @Override
    public Version map(NewVersionForm i) {
        return new Version(
                null,
                null,
                null,
                i.productLink(),
                null,
                null,
                null
        );
    }
}
