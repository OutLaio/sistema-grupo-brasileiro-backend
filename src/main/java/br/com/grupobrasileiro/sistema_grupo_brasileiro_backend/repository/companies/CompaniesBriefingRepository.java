package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.companies;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.companies.CompaniesBriefing;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompaniesBriefingRepository extends JpaRepository<CompaniesBriefing,Long> {

    Set<CompaniesBriefing> findByBriefingId(Long briefingId);
}
