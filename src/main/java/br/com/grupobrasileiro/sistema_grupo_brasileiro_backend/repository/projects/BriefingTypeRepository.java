package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.BriefingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BriefingTypeRepository extends JpaRepository<BriefingType,Long> {
}
