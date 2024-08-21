package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String emailLogin);
    Page<User> findByRole(Integer role, PageRequest pageRequest);
    User findByName(String name);
}

