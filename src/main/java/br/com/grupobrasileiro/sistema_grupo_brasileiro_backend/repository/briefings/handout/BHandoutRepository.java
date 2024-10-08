package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.handout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.handouts.BHandout;

@Repository
public interface BHandoutRepository extends JpaRepository<BHandout, Long> {

}
