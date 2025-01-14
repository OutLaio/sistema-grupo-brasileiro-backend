package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.printeds;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintedType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrintedTypeRepository extends JpaRepository<PrintedType,Long> {
}
