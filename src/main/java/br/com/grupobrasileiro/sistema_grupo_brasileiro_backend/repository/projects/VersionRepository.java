package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionRepository extends JpaRepository<Version,Long> {
    @Query("SELECT COUNT(v) FROM Version v WHERE v.briefing.id = :idBriefing")
    Long countVersionsByBriefingId(@Param("idBriefing") Long idBriefing);

}
