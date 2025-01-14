package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.user.form;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.user.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EntityNotFoundException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.Mapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Profile;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserFormMapper implements Mapper<UserForm, User> {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
	 * Converte os objetos UserForm num conjunto de User
	 * @param i - UserForm
	 * @return User (id, name, lastname, phonenumber, sector, occupation, nop, email, password, role)
	 * */
    @Override
    public User map(UserForm i) {
        Profile profile = profileRepository.findProfileById(i.profile());

        if(profile==null){
            throw new EntityNotFoundException("Profile with number " + i.profile() + " not found");
        }

        String password = passwordEncoder.encode(i.password());

        return new User(
            null,
            profile,
            i.email(),
            password,
            false,
            null
        );
    }
}