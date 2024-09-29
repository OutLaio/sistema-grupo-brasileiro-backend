package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.view;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.view.UserDetailsView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsViewMapper implements Mapper<User, UserDetailsView> {

    @Autowired
    private EmployeeViewMapper employeeViewMapper;

    @Override
    public UserDetailsView map(User i) {
        return new UserDetailsView(
            i.getId(),
            employeeViewMapper.map(i.getEmployee())
        );
    }
}
