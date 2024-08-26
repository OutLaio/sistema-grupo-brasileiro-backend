package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Measurement;

public interface MeasurementsRepository extends JpaRepository<Measurement, Long> {

}
