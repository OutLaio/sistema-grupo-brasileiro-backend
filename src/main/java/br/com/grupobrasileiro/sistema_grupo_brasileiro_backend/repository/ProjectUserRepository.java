package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.Project;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.ProjectUser;
import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.User;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long>{
	
	Optional<ProjectUser> findByProjectAndClientIsNotNull(Project project);

    boolean existsByProjectAndCollaborator(Project project, User collaborator);
    
}
