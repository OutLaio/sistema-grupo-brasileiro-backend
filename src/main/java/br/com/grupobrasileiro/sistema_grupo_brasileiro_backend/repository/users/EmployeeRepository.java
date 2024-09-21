package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.users;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.Project;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("""
        SELECT e FROM Employee e
        JOIN e.user u
        JOIN u.profile p
        WHERE p.id = 2
        """)
    Page<Employee> findAllCollaborators(Pageable pageable);
}
