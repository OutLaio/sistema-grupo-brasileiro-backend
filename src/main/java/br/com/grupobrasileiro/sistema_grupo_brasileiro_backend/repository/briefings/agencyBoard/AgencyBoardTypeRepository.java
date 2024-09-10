package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.agencyBoard;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.agencyBoard.AgencyBoardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyBoardTypeRepository extends JpaRepository<AgencyBoardType,Long> {
}
