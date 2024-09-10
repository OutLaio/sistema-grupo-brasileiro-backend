package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.measurements;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.meansurements.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Long> {
}
