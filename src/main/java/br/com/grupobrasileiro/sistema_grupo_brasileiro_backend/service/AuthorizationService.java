//package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users.UserRepository;
//
//@Service
//public class AuthorizationService implements UserDetailsService{
//	@Autowired
//	UserRepository repository;
//
//	@Override
//	public UserDetails loadUserByUsername(String emailLogin) throws UsernameNotFoundException {
//		// TODO Auto-generated method stub
//		 return repository.findByEmail(emailLogin);
//	}
//}
