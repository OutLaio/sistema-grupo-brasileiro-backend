package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
        /**
     * Busca um usuário pelo email.
     *
     * @param email o email do usuário
     * @return um Optional contendo o usuário, caso ele exista
     */
    Optional<User> findByEmail(String email);
}

