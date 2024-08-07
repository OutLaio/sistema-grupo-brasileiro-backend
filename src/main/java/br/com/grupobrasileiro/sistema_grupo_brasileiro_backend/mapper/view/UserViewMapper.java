package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;

@Component
public class UserViewMapper implements Mapper<User, UserView> {
    @Override
    public UserView map(User i) {
        return new UserView(
            i.getId(),
            i.getName(),
            i.getLastname(),
            i.getPhonenumber(),
            i.getSector(),
            i.getOccupation(),
            i.getNop(),
            i.getEmail(),
            i.getRole(),
            i.getStatus()
        );
    }
}
