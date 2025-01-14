package br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.projects;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import br.com.grupobrasileiro.sistema_grupo_brasileiro_backend.model.users.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa um projeto no sistema.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"Tb_Projects\"")
public class Project {

    /**
     * O identificador único do projeto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * O colaborador associado ao projeto.
     */
    @ManyToOne
    @JoinColumn(name = "id_collaborator")
    private Employee collaborator;

    /**
     * O cliente associado ao projeto.
     * Este campo não pode ser nulo.
     */
    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private Employee client;

    /**
     * O título do projeto.
     * Este campo não pode ser nulo.
     */
    @Column(nullable = false)
    private String title;

    /**
     * O status do projeto.
     * Este campo pode ser nulo.
     */
    @Column
    private String status;

    /**
     * Indica se o projeto está desativado.
     * Este campo não pode ser nulo.
     */
    @Column(nullable = false)
    private Boolean disabled;

    /**
     * Os briefings associados ao projeto.
     * A relação é mapeada pelo campo "project" na entidade Briefing.
     */
    @OneToOne(mappedBy = "project")
    private Briefing briefing;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id) &&
                Objects.equals(title, project.title) &&
                Objects.equals(status, project.status) &&
                Objects.equals(disabled, project.disabled) &&
                Objects.equals(collaborator != null ? collaborator.getId() : null, project.collaborator != null ? project.collaborator.getId() : null) &&
                Objects.equals(client != null ? client.getId() : null, project.client != null ? project.client.getId() : null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, status, disabled,
                collaborator != null ? collaborator.getId() : null,
                client != null ? client.getId() : null);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", collaborator=" + (collaborator != null ? collaborator.getId() : "null") +
                ", client=" + (client != null ? client.getId() : "null") +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", disabled=" + disabled +
                '}';
    }
}
