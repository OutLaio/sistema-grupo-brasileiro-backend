package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form;

import org.springframework.stereotype.Component;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;

@Component
public class UserFormMapper implements Mapper<UserForm, User> {
    
	/**
	 * Converte os objetos UserForm num conjunto de User
	 * @param i - UserForm
	 * @return User (id, name, lastname, phonenumber, sector, occupation, nop, email, password, role)
	 * */
    @Override
    public User map(UserForm i) {
        return new User(
            null,
            i.name(),
            i.lastname(),
            i.phonenumber(),
            i.sector(),
            i.occupation(),
            i.nop(),
            i.email(),
			i.password(),
			i.role()
        );
    }
}