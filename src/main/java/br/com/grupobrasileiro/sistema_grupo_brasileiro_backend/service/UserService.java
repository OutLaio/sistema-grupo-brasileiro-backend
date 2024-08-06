package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.form.UserForm;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.dto.view.UserView;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.infra.exception.EmailUniqueViolationException;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.mapper.form.UserFormMapper;
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
	
//	@Autowired
//    private PasswordEncoder passwordEncoder;
	
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

}
