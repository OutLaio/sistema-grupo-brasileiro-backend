package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.briefings.gift;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.briefings.printeds.PrintingType;

@Repository
public interface PrintingTypeRepository extends JpaRepository<PrintingType,Long> {
}
