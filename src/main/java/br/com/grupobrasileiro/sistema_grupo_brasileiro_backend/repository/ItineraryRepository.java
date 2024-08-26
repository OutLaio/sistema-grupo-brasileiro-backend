package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Itinerary;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {
	List<Itinerary> findByBAgencyBoardId(Long bAgencyBoardId);
}
