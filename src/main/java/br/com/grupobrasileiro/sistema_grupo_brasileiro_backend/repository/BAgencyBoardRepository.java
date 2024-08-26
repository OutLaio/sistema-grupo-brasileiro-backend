package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.BAgencyBoard;

public interface BAgencyBoardRepository extends JpaRepository<BAgencyBoard, Long> {
	Optional<BAgencyBoard> findByProjectId(Long projectId);
}
