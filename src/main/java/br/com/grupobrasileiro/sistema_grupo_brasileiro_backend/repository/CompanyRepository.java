package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{
	Company findByName(String name);
}
