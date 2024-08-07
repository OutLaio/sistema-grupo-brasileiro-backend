package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UpdateUserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserProfileView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EmailUniqueViolationException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.UserFormMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.UserProfileViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.view.UserViewMapper;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserFormMapper userFormMapper;
	
	@Autowired
	private UserViewMapper userViewMapper;
	
	@Autowired
    private UserProfileViewMapper userProfileViewMapper;
	
	@Transactional
	public UserView save(UserForm dto) throws EmailUniqueViolationException {

	    if (userRepository.findByEmail(dto.email()) != null) {
	        throw new EmailUniqueViolationException("Email já em uso: " + dto.email());
	    }
	  
	    String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
	    User entity = userFormMapper.map(dto);
	    entity.setPassword(encryptedPassword);

	    try {
	        userRepository.save(entity);
	    } catch (org.springframework.dao.DataIntegrityViolationException ex) {
	        throw new EmailUniqueViolationException("Email ou nome de usuário já registrado");
	    }

	    return userViewMapper.map(entity);
	}
	
	public UserProfileView getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return userProfileViewMapper.map(user);
    }
	
	@Transactional
    public User updateUser(Long userId, UpdateUserForm form) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setName(form.name());
        user.setLastname(form.lastname());
        user.setPhonenumber(form.phonenumber());
        user.setOccupation(form.occupation());
        user.setNop(form.nop());
        user.setSector(form.sector());
        return userRepository.save(user);
    }
	
	@Transactional
    public void deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(false);
        userRepository.save(user);
    }

}
