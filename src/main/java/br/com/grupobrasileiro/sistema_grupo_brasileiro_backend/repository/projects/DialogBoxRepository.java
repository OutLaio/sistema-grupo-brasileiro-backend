package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.repository.projects;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects.DialogBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DialogBoxRepository extends JpaRepository<DialogBox,Long> {
    Set<DialogBox> findByBriefingId(Long briefingId);
}
