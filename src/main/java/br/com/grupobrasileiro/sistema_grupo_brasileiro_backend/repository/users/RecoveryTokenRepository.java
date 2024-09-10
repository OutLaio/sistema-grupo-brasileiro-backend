package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.RecoveryToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecoveryTokenRepository extends JpaRepository<RecoveryToken,Long> {
}
