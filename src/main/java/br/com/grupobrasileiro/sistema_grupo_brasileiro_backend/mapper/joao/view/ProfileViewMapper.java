package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.joao.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.profile.view.ProfileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileViewMapper implements Mapper<Profile, ProfileView> {

    @Override
    public ProfileView map(Profile profile) {
        return new ProfileView(
                profile.getId(),
                profile.getDescription()
        );
    }
}
