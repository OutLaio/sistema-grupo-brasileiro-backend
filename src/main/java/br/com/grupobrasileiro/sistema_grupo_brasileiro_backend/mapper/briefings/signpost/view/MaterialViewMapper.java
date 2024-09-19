package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.briefings.signpost.view;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.briefings.signpost.view.MaterialView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.signposts.Material;

@Component
public class MaterialViewMapper implements Mapper<Material, MaterialView> {

    @Override
    public MaterialView map(Material material) {
        return new MaterialView(
                material.getId(),
                material.getDescription()
        );
    }
}
