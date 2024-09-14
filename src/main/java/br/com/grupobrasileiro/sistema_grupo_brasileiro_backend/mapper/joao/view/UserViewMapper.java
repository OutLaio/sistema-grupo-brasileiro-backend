package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.joao.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.laio.user.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserViewMapper implements Mapper<User, UserView> {

    @Autowired
    private ProfileViewMapper profileMapperView;

    @Override
    public UserView map(User user) {
        return new UserView(
                user.getId(),
                user.getEmail(),
                profileMapperView.map(user.getProfile())
        );
    }
}
