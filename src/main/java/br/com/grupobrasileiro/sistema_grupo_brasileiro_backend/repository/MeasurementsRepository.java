package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Measurement;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Long> {

}
