package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.profile.view.ProfileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;

public class ProfileViewMapper implements Mapper<Profile, ProfileView> {
    @Override
    public ProfileView map(Profile i) {
        return new ProfileView(
            i.getId(),
            i.getDescription()
        );
    }
}
