package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Measurement;

public interface MeasurementsRepository extends JpaRepository<Measurement, Long> {
	List<Measurement> findByBAgencyBoardId(Long bAgencyBoardId);
}
