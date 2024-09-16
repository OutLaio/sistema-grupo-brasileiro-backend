package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class UserViewMapper implements Mapper<User, UserView> {

    @Autowired
    private ProfileViewMapper profileViewMapper;

    @Override
    public UserView map(User i) {
        return new UserView(
            i.getId(),
            i.getEmail(),
            profileViewMapper.map(i.getProfile())
        );
    }
}
