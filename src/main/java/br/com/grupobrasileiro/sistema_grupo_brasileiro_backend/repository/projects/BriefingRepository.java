package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Briefing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BriefingRepository extends JpaRepository<Briefing,Long> {
}
